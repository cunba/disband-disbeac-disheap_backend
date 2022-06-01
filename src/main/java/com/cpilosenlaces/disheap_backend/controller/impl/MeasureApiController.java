package com.cpilosenlaces.disheap_backend.controller.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.ConstraintViolationException;

import com.cpilosenlaces.disheap_backend.controller.MeasureApi;
import com.cpilosenlaces.disheap_backend.exception.BadRequestException;
import com.cpilosenlaces.disheap_backend.exception.ErrorResponse;
import com.cpilosenlaces.disheap_backend.exception.NotFoundException;
import com.cpilosenlaces.disheap_backend.model.Disband;
import com.cpilosenlaces.disheap_backend.model.Measure;
import com.cpilosenlaces.disheap_backend.model.dto.MeasureDTO;
import com.cpilosenlaces.disheap_backend.service.DisbandService;
import com.cpilosenlaces.disheap_backend.service.MeasureService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class MeasureApiController implements MeasureApi {

    @Autowired
    private MeasureService as;
    @Autowired
    private DisbandService ds;

    @Override
    public ResponseEntity<Measure> getById(UUID id) throws NotFoundException {
        try {
            return new ResponseEntity<>(as.findById(id), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Measure with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<List<Measure>> getByDisbandId(UUID disbandId) {
        return new ResponseEntity<>(as.findByDisbandId(disbandId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Measure>> getByDateBetweenAndDisbandId(long minDate, long maxDate,
            UUID disbandId) {

        long changerDate = System.currentTimeMillis();
        if (minDate > maxDate) {
            changerDate = minDate;
            minDate = maxDate;
            maxDate = changerDate;
        }

        return new ResponseEntity<>(as.findByDateBetweenAndDisbandId(minDate, maxDate, disbandId),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Measure> save(MeasureDTO measureDTO) throws NotFoundException {

        Disband disband = null;
        try {
            disband = ds.findById(measureDTO.getDisbandId());
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Disband with ID " + measureDTO.getDisbandId() + " does not exists.");
        }

        Measure measure = new Measure();

        measure.setId(UUID.randomUUID());
        measure.setDate(System.currentTimeMillis());
        measure.setDisband(disband);

        if (measureDTO.getTemperature() != -1000) {
            measure.setTemperature(measureDTO.getTemperature());
        }
        if (measureDTO.getHumidity() != -1000) {
            measure.setHumidity(measureDTO.getHumidity());
        }
        if (measureDTO.getPressure() != -1000) {
            measure.setPressure(measureDTO.getPressure());
        }
        if (measureDTO.getAmbientNoise() != -1000) {
            measure.setAmbientNoise(measureDTO.getAmbientNoise());
        }
        if (measureDTO.getLightning() != -1000) {
            measure.setLightning(measureDTO.getLightning());
        }
        if (measureDTO.getRedLightning() != -1000) {
            measure.setRedLightning(measureDTO.getRedLightning());
        }
        if (measureDTO.getGreenLightning() != -1000) {
            measure.setGreenLightning(measureDTO.getGreenLightning());
        }
        if (measureDTO.getBlueLightning() != -1000) {
            measure.setBlueLightning(measureDTO.getBlueLightning());
        }
        if (measureDTO.getHeartRate() != -1000) {
            measure.setHeartRate(measureDTO.getHeartRate());
        }

        return new ResponseEntity<>(as.save(measure), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Measure> delete(UUID id) throws NotFoundException {
        try {
            Measure measure = as.findById(id);
            as.delete(measure);

            return new ResponseEntity<>(measure, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Alrm with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<List<Measure>> deleteByDisbandId(UUID disbandId) {
        List<Measure> measures = as.findByDisbandId(disbandId);

        for (Measure measure : measures) {
            as.delete(measure);
        }

        return new ResponseEntity<>(measures, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Measure>> deleteAll() {
        List<Measure> measures = as.findAll();
        as.deleteAll();
        return new ResponseEntity<>(measures, HttpStatus.OK);
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
