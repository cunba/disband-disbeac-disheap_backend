package com.cpilosenlaces.disheap_backend.controller.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.ConstraintViolationException;

import com.cpilosenlaces.disheap_backend.controller.AlarmApi;
import com.cpilosenlaces.disheap_backend.exception.BadRequestException;
import com.cpilosenlaces.disheap_backend.exception.ErrorResponse;
import com.cpilosenlaces.disheap_backend.exception.NotFoundException;
import com.cpilosenlaces.disheap_backend.model.Alarm;
import com.cpilosenlaces.disheap_backend.model.Disband;
import com.cpilosenlaces.disheap_backend.model.dto.AlarmDTO;
import com.cpilosenlaces.disheap_backend.model.util.HandledResponse;
import com.cpilosenlaces.disheap_backend.service.AlarmService;
import com.cpilosenlaces.disheap_backend.service.DisbandService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class AlarmApiController implements AlarmApi {

    @Autowired
    private AlarmService as;
    @Autowired
    private DisbandService ds;

    @Override
    public ResponseEntity<Alarm> getById(UUID id) throws NotFoundException {
        try {
            return new ResponseEntity<>(as.findById(id), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Alarm with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<List<Alarm>> getByDisbandId(UUID disbandId) {
        return new ResponseEntity<>(as.findByDisbandId(disbandId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Alarm>> getByDateBetweenAndDisbandId(long minDate, long maxDate,
            UUID disbandId) {

        long changerDate = 0;
        if (minDate > maxDate) {
            changerDate = minDate;
            minDate = maxDate;
            maxDate = changerDate;
        }

        return new ResponseEntity<>(as.findByDateBetweenAndDisbandId(minDate, maxDate, disbandId),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Alarm> save(AlarmDTO alarmDTO) throws NotFoundException {

        Disband disband = null;
        try {
            disband = ds.findById(alarmDTO.getDisbandId());
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Disband with ID " + alarmDTO.getDisbandId() + " does not exists.");
        }

        ModelMapper mapper = new ModelMapper();
        Alarm alarm = mapper.map(alarmDTO, Alarm.class);
        alarm.setId(UUID.randomUUID());
        alarm.setDate(System.currentTimeMillis());
        alarm.setDisband(disband);

        return new ResponseEntity<>(as.save(alarm), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<HandledResponse> update(UUID id, AlarmDTO alarmDTO) throws NotFoundException {
        Alarm alarm = null;
        try {
            alarm = as.findById(id);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Alarm with ID " + id + " does not exists.");
        }

        ModelMapper mapper = new ModelMapper();
        alarm = mapper.map(alarmDTO, Alarm.class);
        alarm.setDate(System.currentTimeMillis());

        as.save(alarm);

        return new ResponseEntity<>(new HandledResponse("Alarm updated", 1), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Alarm> delete(UUID id) throws NotFoundException {
        try {
            Alarm alarm = as.findById(id);
            as.delete(alarm);

            return new ResponseEntity<>(alarm, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Alrm with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<List<Alarm>> deleteByDisbandId(UUID disbandId) {
        List<Alarm> alarms = as.findByDisbandId(disbandId);
        as.deleteByDisband(alarms);

        return new ResponseEntity<>(alarms, HttpStatus.OK);
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
