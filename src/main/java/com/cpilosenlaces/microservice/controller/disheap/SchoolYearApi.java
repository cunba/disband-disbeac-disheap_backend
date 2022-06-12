package com.cpilosenlaces.microservice.controller.disheap;

import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpilosenlaces.microservice.exception.ErrorResponse;
import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disheap.SchoolYear;
import com.cpilosenlaces.microservice.model.disheap.dto.SchoolYearDTO;
import com.cpilosenlaces.microservice.model.util.HandledResponse;

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

@Tag(name = "School years", description = "School year API")
@Validated
@RestController
@RequestMapping(value = "/school-years", produces = { MediaType.APPLICATION_JSON_VALUE })
@SecurityScheme(name = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public interface SchoolYearApi {

    @Operation(summary = "Get school year by ID", operationId = "getSchoolYearById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Timetable not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    @SecurityRequirements
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<SchoolYear> getById(
            @Parameter(description = "School year ID", required = true) @PathVariable("id") UUID id)
            throws NotFoundException;

    @Operation(summary = "Get all school years", operationId = "getAllSchoolYears")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    @SecurityRequirements
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<List<SchoolYear>> getAll();

    @Secured({ "ROLE_ADMIN" })
    @Operation(summary = "Save school year", operationId = "saveSchoolYear")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<SchoolYear> save(
            @Parameter(description = "School year object", required = true) @RequestBody SchoolYearDTO schoolYearDTO)
            throws NotFoundException;

    @Secured({ "ROLE_ADMIN" })
    @Operation(summary = "Update school year", operationId = "updateSchoolYear")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "School year not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<HandledResponse> update(
            @Parameter(description = "School year id", required = true) @PathVariable UUID id,
            @Parameter(description = "School year object", required = true) @RequestBody SchoolYearDTO schoolYearDTO)
            throws NotFoundException;

    @Secured({ "ROLE_ADMIN" })
    @Operation(summary = "Delete school year", operationId = "deleteSchoolYear")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "School year not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<SchoolYear> delete(
            @Parameter(description = "School year ID", required = true) @PathVariable UUID id)
            throws NotFoundException;

    @Secured({ "ROLE_ADMIN" })
    @Operation(summary = "Delete all school years", operationId = "deleteAllSchoolYears")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping
    public ResponseEntity<List<SchoolYear>> deleteAll();

}
