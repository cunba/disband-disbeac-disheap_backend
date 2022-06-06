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

import com.cpilosenlaces.microservice.controller.disheap.EventApi;
import com.cpilosenlaces.microservice.exception.BadRequestException;
import com.cpilosenlaces.microservice.exception.ErrorResponse;
import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disheap.Event;
import com.cpilosenlaces.microservice.model.disheap.UserModel;
import com.cpilosenlaces.microservice.model.disheap.dto.EventDTO;
import com.cpilosenlaces.microservice.model.util.HandledResponse;
import com.cpilosenlaces.microservice.service.disheap.EventService;
import com.cpilosenlaces.microservice.service.disheap.UserService;

import io.jsonwebtoken.security.SignatureException;

@Controller
public class EventApiController implements EventApi {

    @Autowired
    private EventService es;
    @Autowired
    private UserService us;

    @Override
    public ResponseEntity<Event> getById(UUID id) throws NotFoundException {
        try {
            return new ResponseEntity<>(es.findById(id), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Event with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<List<Event>> getByDateBetweenAndUserId(long minDate, long maxDate, UUID userId) {

        long changerDate = System.currentTimeMillis();
        if (minDate > maxDate) {
            changerDate = minDate;
            minDate = maxDate;
            maxDate = changerDate;
        }

        return new ResponseEntity<>(es.findByStartDateBetweenAndUserId(minDate, maxDate, userId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Event>> getByTypeAndUserId(String type, UUID userId) {
        return new ResponseEntity<>(es.findByTypeAndUserId(type, userId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Event>> getByUserId(UUID userId) {
        return new ResponseEntity<>(es.findByUserId(userId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Event> save(EventDTO eventDTO) throws NotFoundException {
        UserModel user = null;
        try {
            user = us.findById(eventDTO.getUserId());
        } catch (NotFoundException nfe) {
            throw new NotFoundException("User with ID " + eventDTO.getUserId() + " does not exists.");
        }

        ModelMapper mapper = new ModelMapper();
        Event event = mapper.map(eventDTO, Event.class);
        event.setUser(user);

        return new ResponseEntity<>(es.save(event), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<HandledResponse> update(UUID id, EventDTO eventDTO) throws NotFoundException {
        Event event = null;
        try {
            event = es.findById(id);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("Event with ID " + id + " does not exists.");
        }

        event.setEndDate(eventDTO.getEndDate());
        event.setName(eventDTO.getName());
        event.setNote(eventDTO.getNotes());
        event.setStartDate(eventDTO.getStartDate());
        event.setType(eventDTO.getType());

        es.save(event);

        return new ResponseEntity<>(new HandledResponse("Event updated", 1), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Event> delete(UUID id) throws NotFoundException {
        Event event = es.findById(id);
        es.delete(event);

        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Event>> deleteByUserId(UUID userId) {
        List<Event> events = es.findByUserId(userId);
        es.deleteByUserId(events);

        return new ResponseEntity<>(events, HttpStatus.OK);
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
