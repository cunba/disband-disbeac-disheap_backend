package com.cpilosenlaces.microservice.controller.disheap.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cpilosenlaces.microservice.controller.disheap.SubjectApi;
import com.cpilosenlaces.microservice.exception.BadRequestException;
import com.cpilosenlaces.microservice.exception.ErrorResponse;
import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disheap.SchoolYear;
import com.cpilosenlaces.microservice.model.disheap.Subject;
import com.cpilosenlaces.microservice.model.disheap.dto.SubjectDTO;
import com.cpilosenlaces.microservice.model.util.HandledResponse;
import com.cpilosenlaces.microservice.service.disheap.SchoolYearService;
import com.cpilosenlaces.microservice.service.disheap.SubjectService;

import io.jsonwebtoken.security.SignatureException;

@Controller
public class SubjectApiController implements SubjectApi {

    @Autowired
    private SubjectService ss;
    @Autowired
    private SchoolYearService sys;

    @Override
    public ResponseEntity<Subject> getById(UUID id) throws NotFoundException {
        try {
            return new ResponseEntity<>(ss.findById(id), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Subject with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<List<Subject>> getBySchoolYearId(UUID schoolYearId) {
        return new ResponseEntity<>(ss.findBySchoolYearId(schoolYearId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Subject> save(SubjectDTO subjectDTO) throws NotFoundException {
        SchoolYear schoolYear = null;
        try {
            schoolYear = sys.findById(subjectDTO.getSchoolYearId());
        } catch (NotFoundException nfe) {
            throw new NotFoundException("School year with ID " + subjectDTO.getSchoolYearId() + " does not exists.");
        }

        Subject subject = new Subject();
        subject.setId(UUID.randomUUID());
        subject.setName(subjectDTO.getName());
        subject.setSchoolYear(schoolYear);

        return new ResponseEntity<>(ss.save(subject), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<HandledResponse> update(UUID id, SubjectDTO subjectDTO) throws NotFoundException {
        SchoolYear schoolYear = null;
        try {
            schoolYear = sys.findById(subjectDTO.getSchoolYearId());
        } catch (NotFoundException nfe) {
            throw new NotFoundException("School year with ID " + subjectDTO.getSchoolYearId() + " does not exists.");
        }

        Subject subject = null;
        try {
            subject = ss.findById(id);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Subject with ID " + id + " does not exists.");
        }

        subject.setName(subjectDTO.getName());
        subject.setSchoolYear(schoolYear);

        ss.save(subject);

        return new ResponseEntity<>(new HandledResponse("Subject updated.", 1), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Subject> delete(UUID id) throws NotFoundException {
        try {
            Subject subject = ss.findById(id);
            ss.delete(subject);
            return new ResponseEntity<>(subject, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Subject with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<List<Subject>> deleteBySchoolYearId(UUID schoolYearId) {
        List<Subject> subjects = ss.findBySchoolYearId(schoolYearId);
        ss.deleteBySchoolYearId(subjects);
        return new ResponseEntity<>(subjects, HttpStatus.OK);
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
