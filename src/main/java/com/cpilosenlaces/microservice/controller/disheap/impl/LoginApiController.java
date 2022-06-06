package com.cpilosenlaces.microservice.controller.disheap.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cpilosenlaces.microservice.config.WebSecurityConfig;
import com.cpilosenlaces.microservice.controller.disheap.LoginApi;
import com.cpilosenlaces.microservice.exception.BadRequestException;
import com.cpilosenlaces.microservice.exception.ErrorResponse;
import com.cpilosenlaces.microservice.model.disheap.UserModel;
import com.cpilosenlaces.microservice.security.JwtRequest;
import com.cpilosenlaces.microservice.security.JwtResponse;
import com.cpilosenlaces.microservice.security.JwtTokenProvider;
import com.cpilosenlaces.microservice.service.disheap.UserService;

@Controller
public class LoginApiController implements LoginApi {

    @Autowired
    private UserService us;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public ResponseEntity<JwtResponse> login(JwtRequest jwtRequest) throws BadRequestException {
        List<UserModel> user = us.findByEmail(jwtRequest.getEmail());

        if (user.isEmpty()) {
            throw new BadRequestException("User not found with email: " + jwtRequest.getEmail());
        }

        if (!(WebSecurityConfig.encoder().matches(jwtRequest.getPassword(), user.get(0).getPassword()))) {
            throw new BadRequestException("Credentials error, incorrect password for user " + jwtRequest.getEmail());
        }

        String token = jwtTokenProvider.createToken(user.get(0).getId(), jwtRequest.getEmail(), user.get(0).getRole());
        JwtResponse jwtResponse = new JwtResponse(token);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException br) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Bad request expcetion");
        ErrorResponse errorResponse = new ErrorResponse("400", error, br.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
