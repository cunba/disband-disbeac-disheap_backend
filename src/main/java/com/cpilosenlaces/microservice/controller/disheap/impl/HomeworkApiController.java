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

import com.cpilosenlaces.microservice.controller.disheap.HomeworkApi;
import com.cpilosenlaces.microservice.exception.BadRequestException;
import com.cpilosenlaces.microservice.exception.ErrorResponse;
import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disheap.Homework;
import com.cpilosenlaces.microservice.model.disheap.Subject;
import com.cpilosenlaces.microservice.model.disheap.UserModel;
import com.cpilosenlaces.microservice.model.disheap.dto.HomeworkDTO;
import com.cpilosenlaces.microservice.model.util.HandledResponse;
import com.cpilosenlaces.microservice.service.disheap.HomeworkService;
import com.cpilosenlaces.microservice.service.disheap.SubjectService;
import com.cpilosenlaces.microservice.service.disheap.UserService;

import io.jsonwebtoken.security.SignatureException;

@Controller
public class HomeworkApiController implements HomeworkApi {

    @Autowired
    private HomeworkService hs;
    @Autowired
    private UserService us;
    @Autowired
    private SubjectService ss;

    @Override
    public ResponseEntity<Homework> getById(UUID id) throws NotFoundException {
        try {
            return new ResponseEntity<>(hs.findById(id), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Homework with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<List<Homework>> findByDeadlineBetweenAndUserId(long minDate, long maxDate, UUID userId) {
        return new ResponseEntity<>(hs.findByDeadlineBetweenAndUserId(minDate, maxDate, userId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Homework>> findByDeadlineBetweenAndSubjectIdAndUserId(long minDate, long maxDate,
            UUID subjectId, UUID userId) {

        return new ResponseEntity<>(hs.findByDeadlineBetweenAndSubjectIdAndUserId(minDate, maxDate, subjectId, userId),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Homework>> getByUserId(UUID userId) {
        return new ResponseEntity<>(hs.findByUserId(userId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Homework> save(HomeworkDTO homeworkDTO) throws NotFoundException {
        UserModel user = null;
        try {
            user = us.findById(homeworkDTO.getUserId());
        } catch (NotFoundException nfe) {
            throw new NotFoundException("User with ID " + homeworkDTO.getUserId() + " does not exists.");
        }

        Subject subject = null;
        try {
            subject = ss.findById(homeworkDTO.getSubjectId());
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Subject with ID " + homeworkDTO.getSubjectId() + " does not exists.");
        }

        ModelMapper mapper = new ModelMapper();
        Homework homework = mapper.map(homeworkDTO, Homework.class);
        homework.setId(UUID.randomUUID());
        homework.setSubject(subject);
        homework.setUser(user);

        return new ResponseEntity<>(hs.save(homework), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<HandledResponse> update(UUID id, HomeworkDTO homeworkDTO) throws NotFoundException {
        UserModel user = null;
        try {
            user = us.findById(homeworkDTO.getUserId());
        } catch (NotFoundException nfe) {
            throw new NotFoundException("User with ID " + homeworkDTO.getUserId() + " does not exists.");
        }

        Subject subject = null;
        try {
            subject = ss.findById(homeworkDTO.getSubjectId());
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Subject with ID " + homeworkDTO.getSubjectId() + " does not exists.");
        }

        Homework homework = null;
        try {
            homework = hs.findById(id);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Homework with ID " + id + " does not exists.");
        }

        homework.setDeadline(homeworkDTO.getDeadline());
        homework.setDescription(homeworkDTO.getDescription());
        homework.setName(homeworkDTO.getName());
        homework.setSubject(subject);
        homework.setUser(user);

        hs.save(homework);

        return new ResponseEntity<>(new HandledResponse("Homework updated.", 1), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Homework> delete(UUID id) throws NotFoundException {
        Homework homework = hs.findById(id);
        hs.delete(homework);
        return new ResponseEntity<>(homework, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Homework>> deleteByUserId(UUID userId) {
        List<Homework> homeworks = hs.findByUserId(userId);
        hs.deleteByUserId(homeworks);
        return new ResponseEntity<>(homeworks, HttpStatus.OK);
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
