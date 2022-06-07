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

import com.cpilosenlaces.microservice.config.WebSecurityConfig;
import com.cpilosenlaces.microservice.controller.disheap.UserApi;
import com.cpilosenlaces.microservice.exception.BadRequestException;
import com.cpilosenlaces.microservice.exception.ErrorResponse;
import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disheap.UserModel;
import com.cpilosenlaces.microservice.model.disheap.dto.PasswordChangeDTO;
import com.cpilosenlaces.microservice.model.disheap.dto.UpdateUserDTO;
import com.cpilosenlaces.microservice.model.disheap.dto.UserDTO;
import com.cpilosenlaces.microservice.model.util.HandledResponse;
import com.cpilosenlaces.microservice.service.disheap.UserService;

import io.jsonwebtoken.security.SignatureException;

@Controller
public class UserApiController implements UserApi {

    @Autowired
    private UserService us;

    @Override
    public ResponseEntity<UserModel> getById(UUID id) throws NotFoundException {
        try {
            return new ResponseEntity<>(us.findById(id), HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("User with ID " + id + " does not exists.");
        }
    }

    @Override
    public ResponseEntity<UserModel> getByEmail(String email) {
        List<UserModel> user = us.findByEmail(email);
        System.out.println(user);
        System.out.println(user.get(0));
        return new ResponseEntity<>(user.get(0), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserModel> save(UserDTO userDTO) throws BadRequestException {
        List<UserModel> users = us.findByEmail(userDTO.getEmail());
        if (!users.isEmpty()) {
            throw new BadRequestException("The user " + userDTO.getEmail() + " already exists.");
        }

        ModelMapper mapper = new ModelMapper();
        UserModel user = mapper.map(userDTO, UserModel.class);
        user.setId(UUID.randomUUID());
        user.setRole(userDTO.getRole().toUpperCase());
        user.setPassword(WebSecurityConfig.encoder().encode(userDTO.getPassword()));
        user.setCreated(System.currentTimeMillis());
        user.setUpdated(System.currentTimeMillis());

        return new ResponseEntity<>(us.save(user), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<HandledResponse> update(UUID id, UpdateUserDTO userDTO)
            throws NotFoundException, BadRequestException {
        UserModel user = null;
        try {
            user = us.findById(id);
            System.out.println(user);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("User with ID " + id + " does not exists.");
        }

        if (!user.getEmail().equals("string") && !user.getEmail().equals(userDTO.getEmail())) {
            List<UserModel> users = us.findByEmail(userDTO.getEmail());
            if (!users.isEmpty()) {
                throw new BadRequestException("The user " + userDTO.getEmail() + " already exists.");
            }
        }

        if (!userDTO.getBirthday().equals("string")) {
            System.out.println("birthday es null");
            user.setBirthday(userDTO.getBirthday());
        }
        if (!userDTO.getEmail().equals("string")) {
            System.out.println("email es null");
            user.setEmail(userDTO.getEmail());
        }
        if (!userDTO.getName().equals("string")) {
            System.out.println("name no es nulo");
            user.setName(userDTO.getName());
        }
        if (!userDTO.getSurname().equals("string")) {
            System.out.println("surname es null");
            user.setSurname(userDTO.getSurname());
        }

        user.setUpdated(System.currentTimeMillis());
        us.save(user);

        return new ResponseEntity<>(new HandledResponse("User updated", 1), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HandledResponse> updatePassword(UUID id, PasswordChangeDTO password)
            throws NotFoundException, BadRequestException {

        UserModel user = us.findById(id);
        if (!(WebSecurityConfig.encoder().matches(password.getPassword(), user.getPassword()))) {
            String passwordEncrypted = WebSecurityConfig.encoder().encode(password.getPassword());
            us.updatePassword(id, passwordEncrypted);
            return new ResponseEntity<>(new HandledResponse("Password updated", 1), HttpStatus.OK);
        } else {
            throw new BadRequestException("La contraseña debe ser distinta.");
        }
    }

    @Override
    public ResponseEntity<HandledResponse> updateEmail(UUID id, String email)
            throws NotFoundException, BadRequestException {

        UserModel user = us.findById(id);
        if (!user.getEmail().equals(email)) {
            List<UserModel> users = us.findByEmail(email);
            if (users.size() > 0) {
                throw new BadRequestException("Ya existe un usuario con ese email.");
            }
            us.updateEmail(id, email);
            return new ResponseEntity<>(new HandledResponse("Email updated", 1), HttpStatus.OK);
        } else {
            throw new BadRequestException("El email debe ser distinto.");
        }
    }

    @Override
    public ResponseEntity<UserModel> delete(UUID id) throws NotFoundException {
        try {
            UserModel user = us.findById(id);
            us.delete(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw new NotFoundException("User with ID " + id + " does not exists.");
        }
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
