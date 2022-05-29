package com.cpilosenlaces.disheap_backend.controller.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.ConstraintViolationException;

import com.cpilosenlaces.disheap_backend.controller.DisbeacApi;
import com.cpilosenlaces.disheap_backend.exception.BadRequestException;
import com.cpilosenlaces.disheap_backend.exception.ErrorResponse;
import com.cpilosenlaces.disheap_backend.exception.NotFoundException;
import com.cpilosenlaces.disheap_backend.model.Disbeac;
import com.cpilosenlaces.disheap_backend.model.UserModel;
import com.cpilosenlaces.disheap_backend.model.dto.DisbeacDTO;
import com.cpilosenlaces.disheap_backend.service.DisbeacService;
import com.cpilosenlaces.disheap_backend.service.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class DisbeacApiController implements DisbeacApi {

    @Autowired
    private DisbeacService ds;
    @Autowired
    private UserService us;

    @Override
    public ResponseEntity<Disbeac> getById(UUID id) throws NotFoundException {
        try {
            return new ResponseEntity<>(ds.findById(id), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Disbeac with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<List<Disbeac>> getByUserId(UUID userId) {
        return new ResponseEntity<>(ds.findByUserId(userId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Disbeac> save(DisbeacDTO disbeacDTO) throws NotFoundException {
        UserModel user = null;
        try {
            user = us.findById(disbeacDTO.getUserId());
        } catch (NotFoundException nfe) {
            throw new NotFoundException("User with ID " + disbeacDTO.getUserId() + " does not exists.");
        }

        ModelMapper mapper = new ModelMapper();
        Disbeac disbeac = mapper.map(disbeacDTO, Disbeac.class);
        disbeac.setId(UUID.randomUUID());
        disbeac.setDate(LocalDateTime.now());
        disbeac.setUser(user);

        return new ResponseEntity<>(ds.save(disbeac), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> update(UUID id, DisbeacDTO disbeacDTO) throws NotFoundException {
        Disbeac disbeac = null;
        try {
            disbeac = ds.findById(id);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Disbeac with ID " + disbeacDTO.getUserId() + " does not exists.");
        }

        UserModel user = null;
        try {
            user = us.findById(disbeacDTO.getUserId());
        } catch (NotFoundException nfe) {
            throw new NotFoundException("User with ID " + disbeacDTO.getUserId() + " does not exists.");
        }

        ModelMapper mapper = new ModelMapper();
        disbeac = mapper.map(disbeacDTO, Disbeac.class);
        disbeac.setDate(LocalDateTime.now());
        disbeac.setUser(user);

        ds.save(disbeac);

        return new ResponseEntity<>("Disbeac updated.", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateLocation(UUID id, float latitude, float longitude) throws NotFoundException {
        try {
            Disbeac disbeac = ds.findById(id);

            disbeac.setLatitude(latitude);
            disbeac.setLongitude(longitude);
            disbeac.setDate(LocalDateTime.now());

            ds.save(disbeac);

            return new ResponseEntity<>("Location updated.", HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Disbeac with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<Disbeac> delete(UUID id) throws NotFoundException {
        try {
            Disbeac disbeac = ds.findById(id);
            ds.delete(disbeac);

            return new ResponseEntity<>(disbeac, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Disbeac with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<List<Disbeac>> deleteByUserId(UUID userId) {
        List<Disbeac> disbeacs = ds.findByUserId(userId);

        for (Disbeac disbeac : disbeacs) {
            ds.delete(disbeac);
        }

        return new ResponseEntity<>(disbeacs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Disbeac>> deleteAll() {
        List<Disbeac> disbeacs = ds.findAll();
        ds.deleteAll();
        return new ResponseEntity<>(disbeacs, HttpStatus.OK);
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
