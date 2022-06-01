package com.cpilosenlaces.disheap_backend.controller.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.ConstraintViolationException;

import com.cpilosenlaces.disheap_backend.controller.UserApi;
import com.cpilosenlaces.disheap_backend.exception.BadRequestException;
import com.cpilosenlaces.disheap_backend.exception.ErrorResponse;
import com.cpilosenlaces.disheap_backend.exception.NotFoundException;
import com.cpilosenlaces.disheap_backend.model.UserModel;
import com.cpilosenlaces.disheap_backend.model.dto.PasswordChangeDTO;
import com.cpilosenlaces.disheap_backend.model.dto.UpdateUserDTO;
import com.cpilosenlaces.disheap_backend.model.dto.UserDTO;
import com.cpilosenlaces.disheap_backend.model.util.HandledResponse;
import com.cpilosenlaces.disheap_backend.service.UserService;

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
    public ResponseEntity<List<UserModel>> getByEmail(String email) {
        System.out.println("este es el email " + email);
        return new ResponseEntity<>(us.findByEmail(email), HttpStatus.OK);
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
        user.setPassword(UserModel.encoder().encode(userDTO.getPassword()));
        user.setRegisterDate(System.currentTimeMillis());

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

        us.save(user);

        return new ResponseEntity<>(new HandledResponse("User updated", 1), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HandledResponse> updatePassword(UUID id, PasswordChangeDTO password)
            throws NotFoundException, BadRequestException {

        UserModel user = us.findById(id);
        if (!(UserModel.encoder().matches(password.getPassword(), user.getPassword()))) {
            System.out.println("son diferentes");
            String passwordEncrypted = UserModel.encoder().encode(password.getPassword());
            us.updatePassword(id, passwordEncrypted);
            return new ResponseEntity<>(new HandledResponse("Password updated", 1), HttpStatus.OK);
        } else {
            throw new BadRequestException("La contraseña debe ser distinta.");
        }
    }

    @Override
    public ResponseEntity<UserModel> delete(UUID id) throws NotFoundException {
        try {
            UserModel user = us.findById(id);
            us.deleteUser(user);
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
