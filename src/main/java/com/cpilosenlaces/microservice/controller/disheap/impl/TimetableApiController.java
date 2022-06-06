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

import com.cpilosenlaces.microservice.controller.disheap.TimetableApi;
import com.cpilosenlaces.microservice.exception.BadRequestException;
import com.cpilosenlaces.microservice.exception.ErrorResponse;
import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disheap.Subject;
import com.cpilosenlaces.microservice.model.disheap.Timetable;
import com.cpilosenlaces.microservice.model.disheap.UserModel;
import com.cpilosenlaces.microservice.model.disheap.dto.TimetableDTO;
import com.cpilosenlaces.microservice.model.util.HandledResponse;
import com.cpilosenlaces.microservice.service.disheap.SubjectService;
import com.cpilosenlaces.microservice.service.disheap.TimetableService;
import com.cpilosenlaces.microservice.service.disheap.UserService;

import io.jsonwebtoken.security.SignatureException;

@Controller
public class TimetableApiController implements TimetableApi {

    @Autowired
    private TimetableService ts;
    @Autowired
    private UserService us;
    @Autowired
    private SubjectService ss;

    @Override
    public ResponseEntity<Timetable> getById(UUID id) throws NotFoundException {
        try {
            return new ResponseEntity<>(ts.findById(id), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Timetable with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<List<Timetable>> getByUserId(UUID userId) {
        return new ResponseEntity<>(ts.findByUserId(userId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Timetable> save(TimetableDTO timetableDTO) throws NotFoundException {
        UserModel user = null;
        try {
            user = us.findById(timetableDTO.getUserId());
        } catch (NotFoundException nfe) {
            throw new NotFoundException("User with ID " + timetableDTO.getUserId() + " does not exists.");
        }

        Subject subject = null;
        try {
            subject = ss.findById(timetableDTO.getSubjectId());
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Subject with ID " + timetableDTO.getSubjectId() + " does not exists.");
        }

        ModelMapper mapper = new ModelMapper();
        Timetable timetable = mapper.map(timetableDTO, Timetable.class);
        timetable.setId(UUID.randomUUID());
        timetable.setUser(user);
        timetable.setSubject(subject);

        return new ResponseEntity<>(ts.save(timetable), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<HandledResponse> update(UUID id, TimetableDTO timetableDTO) throws NotFoundException {
        Timetable timetable = null;
        try {
            timetable = ts.findById(id);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Timetable with ID " + id + " does not exists.");
        }

        UserModel user = null;
        try {
            user = us.findById(timetableDTO.getUserId());
        } catch (NotFoundException nfe) {
            throw new NotFoundException("User with ID " + timetableDTO.getUserId() + " does not exists.");
        }

        Subject subject = null;
        try {
            subject = ss.findById(timetableDTO.getSubjectId());
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Subject with ID " + timetableDTO.getSubjectId() + " does not exists.");
        }

        timetable.setEndTime(timetableDTO.getEndTime());
        timetable.setStartTime(timetableDTO.getStartTime());
        timetable.setSubject(subject);
        timetable.setUser(user);
        timetable.setWeekDay(timetableDTO.getWeekDay());

        ts.save(timetable);

        return new ResponseEntity<>(new HandledResponse("Timetable updated.", 1), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Timetable> delete(UUID id) throws NotFoundException {
        try {
            Timetable timetable = ts.findById(id);
            ts.delete(timetable);
            return new ResponseEntity<>(timetable, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Timetable with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<List<Timetable>> deleteByUserId(UUID userId) {
        List<Timetable> timetables = ts.findByUserId(userId);
        ts.deleteByUserId(timetables);
        return new ResponseEntity<>(timetables, HttpStatus.OK);
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
