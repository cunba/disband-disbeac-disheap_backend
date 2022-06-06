package com.cpilosenlaces.microservice.controller.disband.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cpilosenlaces.microservice.controller.disband.HumidityApi;
import com.cpilosenlaces.microservice.exception.BadRequestException;
import com.cpilosenlaces.microservice.exception.ErrorResponse;
import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disband.Disband;
import com.cpilosenlaces.microservice.model.disband.Humidity;
import com.cpilosenlaces.microservice.model.disband.dto.MeasureDTO;
import com.cpilosenlaces.microservice.service.disband.DisbandService;
import com.cpilosenlaces.microservice.service.disband.HumidityService;

@Controller
public class HumidityApiController implements HumidityApi {

    @Autowired
    private HumidityService hs;
    @Autowired
    private DisbandService ds;

    @Override
    public ResponseEntity<List<Humidity>> getAll() {
        return new ResponseEntity<>(hs.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Humidity> getById(UUID id) throws NotFoundException {
        try {
            return new ResponseEntity<>(hs.findById(id), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Humidity with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<List<Humidity>> getByDisbandId(UUID disbandId) {
        return new ResponseEntity<>(hs.findByDisbandId(disbandId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Humidity>> getByDateBetweenAndDisbandId(long minDate, long maxDate,
            UUID disbandId) {

        long changerDate = System.currentTimeMillis();
        if (minDate > maxDate) {
            changerDate = minDate;
            minDate = maxDate;
            maxDate = changerDate;
        }

        return new ResponseEntity<>(hs.findByDateBetweenAndDisbandId(minDate, maxDate, disbandId),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Humidity>> getByDateBetween(long minDate, long maxDate) {

        long changerDate = System.currentTimeMillis();
        if (minDate > maxDate) {
            changerDate = minDate;
            minDate = maxDate;
            maxDate = changerDate;
        }

        return new ResponseEntity<>(hs.findByDateBetween(minDate, maxDate), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Humidity> save(MeasureDTO measureDTO) throws NotFoundException, BadRequestException {

        Disband disband = null;
        List<Disband> disbands = ds.findByMac(measureDTO.getDisbandMac());
        if (disbands.size() > 0) {
            disband = disbands.get(0);
        } else {
            throw new NotFoundException("Disband with MAC " + measureDTO.getDisbandMac() + " does not exists.");
        }

        Humidity humidity = new Humidity();

        humidity.setId(UUID.randomUUID());
        humidity.setData(measureDTO.getData());
        humidity.setDate(measureDTO.getDate());
        humidity.setDisband(disband);

        return new ResponseEntity<>(hs.save(humidity), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<Humidity>> deleteByDisbandId(UUID disbandId) throws NotFoundException {
        try {
            ds.findById(disbandId);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Disband with ID " + disbandId + " does not exists.");
        }

        List<Humidity> listHumiditys = hs.findByDisbandId(disbandId);
        hs.deleteByDisband(listHumiditys);

        return new ResponseEntity<>(listHumiditys, HttpStatus.OK);
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
