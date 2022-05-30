package com.cpilosenlaces.disheap_backend.controller;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.exception.BadRequestException;
import com.cpilosenlaces.disheap_backend.exception.ErrorResponse;
import com.cpilosenlaces.disheap_backend.exception.NotFoundException;
import com.cpilosenlaces.disheap_backend.model.UserModel;
import com.cpilosenlaces.disheap_backend.model.dto.PasswordChangeDTO;
import com.cpilosenlaces.disheap_backend.model.dto.UserDTO;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Users", description = "User API")
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@SecurityScheme(name = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public interface UserApi {

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @Operation(summary = "Get user by ID", operationId = "getUserById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Icon not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    @SecurityRequirements
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<UserModel> getById(
            @Parameter(description = "User ID", required = true) @PathVariable("id") UUID id)
            throws NotFoundException;

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @Operation(summary = "Get user by email", operationId = "getUserByEmail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserModel.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/emails/{email}")
    @SecurityRequirements
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<List<UserModel>> getByEmail(
            @Parameter(description = "User email", required = true) @PathVariable("email") String email);

    @Operation(summary = "Save user", operationId = "saveUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = UserModel.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<UserModel> save(
            @Parameter(description = "User object", required = false) @RequestBody UserDTO userDTO)
            throws BadRequestException;

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @Operation(summary = "Update user", operationId = "updateUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Icon not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    @SecurityRequirements
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<String> update(
            @Parameter(description = "User id", required = true) @PathVariable UUID id,
            @Parameter(description = "User object") @RequestBody UserDTO userDTO)
            throws NotFoundException, BadRequestException;

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @Operation(summary = "Update user's password", operationId = "updateUserPassword")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Icon not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}/password")
    @SecurityRequirements
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<String> updatePassword(
            @Parameter(description = "User id", required = true) @PathVariable UUID id,
            @Parameter(description = "User object", required = true) @RequestBody PasswordChangeDTO password)
            throws NotFoundException;

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @Operation(summary = "Delete user", operationId = "deleteUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Icon not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    @SecurityRequirements
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<UserModel> delete(
            @Parameter(description = "User id", required = true) @PathVariable UUID id)
            throws NotFoundException;

    @Secured({ "ROLE_ADMIN" })
    @Operation(summary = "Delete all users", operationId = "deleteAllUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping
    @SecurityRequirements
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<List<UserModel>> deleteAll();
}
