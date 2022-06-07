package com.cpilosenlaces.microservice.controller.disbeac.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.ConstraintViolationException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cpilosenlaces.microservice.controller.disbeac.LocationApi;
import com.cpilosenlaces.microservice.exception.BadRequestException;
import com.cpilosenlaces.microservice.exception.ErrorResponse;
import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disbeac.Disbeac;
import com.cpilosenlaces.microservice.model.disbeac.Location;
import com.cpilosenlaces.microservice.model.disbeac.dto.LocationDTO;
import com.cpilosenlaces.microservice.service.disbeac.DisbeacService;
import com.cpilosenlaces.microservice.service.disbeac.LocationService;

@Controller
public class LocationApiController implements LocationApi {

    @Autowired
    private LocationService lds;
    @Autowired
    private DisbeacService ds;

    @Override
    public ResponseEntity<Location> getLast1ByDateBetweenAndDisbeacId(long minDate, long maxDate, UUID disbeacId) {
        return new ResponseEntity<>(lds.findLast1ByDateBetweenAndDisbeacId(minDate, maxDate, disbeacId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Location>> getByDateBetweenAndDisbeacId(long minDate, long maxDate,
            UUID disbeacId) {

        long changerDate = System.currentTimeMillis();
        if (minDate > maxDate) {
            changerDate = minDate;
            minDate = maxDate;
            maxDate = changerDate;
        }
        return new ResponseEntity<>(lds.findByDateBetweenAndDisbeacId(minDate, maxDate, disbeacId),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Location>> getByDisbeacId(UUID disbeacId) {
        return new ResponseEntity<>(lds.findByDisbeacId(disbeacId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Location> getById(UUID id) throws NotFoundException {
        try {
            return new ResponseEntity<>(lds.findById(id), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Location with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<List<Location>> getAll() {
        return new ResponseEntity<>(lds.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Location> save(LocationDTO locationDTO)
            throws NotFoundException, BadRequestException {

        Disbeac disbeac = null;
        List<Disbeac> disbeacs = ds.findByMac(locationDTO.getDisbeacMac());
        if (disbeacs.size() > 0) {
            disbeac = disbeacs.get(0);
        } else {
            throw new NotFoundException("Disbeac with MAC " + locationDTO.getDisbeacMac() + " does not exists.");
        }

        ModelMapper mapper = new ModelMapper();
        Location location = mapper.map(locationDTO, Location.class);
        location.setId(UUID.randomUUID());
        location.setDate(System.currentTimeMillis());
        location.setDisbeac(disbeac);

        return new ResponseEntity<>(lds.save(location), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Location> delete(UUID id) throws NotFoundException {
        try {
            Location location = lds.findById(id);
            lds.delete(location);
            return new ResponseEntity<>(location, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Location with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<List<Location>> deleteByDisbeacId(UUID disbeacId) throws NotFoundException {
        try {
            ds.findById(disbeacId);
            List<Location> listLocations = lds.findByDisbeacId(disbeacId);
            lds.deleteByDisbeac(listLocations);

            return new ResponseEntity<>(listLocations, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Disbeac with ID " + disbeacId + " does not exists.");
        }

    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException br) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Bad request exception");
        ErrorResponse errorResponse = new ErrorResponse("400", error, br.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException nfe) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Not Found Exception");
        ErrorResponse errorResponse = new ErrorResponse("404", error, nfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleArgumentNotValidException(MethodArgumentNotValidException manve) {
        Map<String, String> errors = new HashMap<>();
        manve.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException cve) {
        Map<String, String> errors = new HashMap<>();
        cve.getConstraintViolations().forEach(error -> {
            String fieldName = error.getPropertyPath().toString();
            String message = error.getMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Internal server error");
        ErrorResponse errorResponse = new ErrorResponse("500", error, exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
