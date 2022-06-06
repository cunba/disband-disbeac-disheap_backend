package com.cpilosenlaces.microservice.controller.disheap.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.ConstraintViolationException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cpilosenlaces.microservice.controller.disheap.SchoolYearApi;
import com.cpilosenlaces.microservice.exception.BadRequestException;
import com.cpilosenlaces.microservice.exception.ErrorResponse;
import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disheap.SchoolYear;
import com.cpilosenlaces.microservice.model.disheap.dto.SchoolYearDTO;
import com.cpilosenlaces.microservice.model.util.HandledResponse;
import com.cpilosenlaces.microservice.service.disheap.SchoolYearService;

import io.jsonwebtoken.security.SignatureException;

@Controller
public class SchoolYearApiController implements SchoolYearApi {

    @Autowired
    private SchoolYearService sys;

    @Override
    public ResponseEntity<SchoolYear> getById(UUID id) throws NotFoundException {
        try {
            return new ResponseEntity<>(sys.findById(id), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("School year with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<List<SchoolYear>> getAll() {
        return new ResponseEntity<>(sys.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SchoolYear> save(SchoolYearDTO schoolYearDTO) throws NotFoundException {
        ModelMapper mapper = new ModelMapper();
        SchoolYear schoolYear = mapper.map(schoolYearDTO, SchoolYear.class);
        schoolYear.setId(UUID.randomUUID());
        return new ResponseEntity<>(sys.save(schoolYear), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<HandledResponse> update(UUID id, SchoolYearDTO schoolYearDTO) throws NotFoundException {
        SchoolYear schoolYear = null;
        try {
            schoolYear = sys.findById(id);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("School year with ID " + id + " does not exists.");
        }

        schoolYear.setName(schoolYearDTO.getName());
        schoolYear.setStudy(schoolYearDTO.getStudy());

        sys.save(schoolYear);

        return new ResponseEntity<>(new HandledResponse("School year updated", 1), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SchoolYear> delete(UUID id) throws NotFoundException {
        try {
            SchoolYear schoolYear = sys.findById(id);
            sys.delete(schoolYear);
            return new ResponseEntity<>(schoolYear, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("School year with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<List<SchoolYear>> deleteAll() {
        List<SchoolYear> schoolYears = sys.findAll();
        sys.deleteAll();
        return new ResponseEntity<>(schoolYears, HttpStatus.OK);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(Exception e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Acceso denegado");
        ErrorResponse errorResponse = new ErrorResponse("401", error,
                "Este usuario no tiene permisos suficientes para realizar esta operación.");

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponse> handleSignatureException(Exception e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Acceso denegado");
        ErrorResponse errorResponse = new ErrorResponse("401", error, "Token caducado o en mal estado.");

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException br) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Bad request exception");
        ErrorResponse errorResponse = new ErrorResponse("400", error, br.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException br) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Bad request expcetion");
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
