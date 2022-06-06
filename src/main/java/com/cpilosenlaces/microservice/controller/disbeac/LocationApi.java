package com.cpilosenlaces.microservice.controller.disbeac;

import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cpilosenlaces.microservice.exception.BadRequestException;
import com.cpilosenlaces.microservice.exception.ErrorResponse;
import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disbeac.Location;
import com.cpilosenlaces.microservice.model.disbeac.dto.LocationDTO;

import io.swagger.oas.annotations.parameters.RequestBody;
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

@Tag(name = "Locations", description = "Location API")
@Validated
@RestController
@RequestMapping(value = "/locations", produces = { MediaType.APPLICATION_JSON_VALUE })
@SecurityScheme(name = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
@SecurityRequirements
@SecurityRequirement(name = "bearer")
public interface LocationApi {

    @Operation(summary = "Get last location by disbeac ID", operationId = "getLast1ByDisbeacId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/last/disbeacId/{disbeacId}")
    ResponseEntity<List<Location>> getLast1ByDisbeacId(
            @Parameter(description = "Disbeac ID", required = true) @PathVariable UUID disbeacId);

    @Operation(summary = "Get location by date between and disbeac ID", operationId = "getLocationByDateBetweenAndDisbeacId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/date/between/disbeacId/{disbeacId}")
    ResponseEntity<List<Location>> getByDateBetweenAndDisbeacId(
            @Parameter(description = "Min date", required = true) @RequestParam(value = "min date") long minDate,
            @Parameter(description = "Max date", required = true) @RequestParam(value = "max date") long maxDate,
            @Parameter(description = "Disbeac ID", required = true) @PathVariable UUID disbeacId);

    @Operation(summary = "Get location by disbeac ID", operationId = "getLocationByDisbeacId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/disbeacId/{disbeacId}")
    ResponseEntity<List<Location>> getByDisbeacId(
            @Parameter(description = "Disbeac ID", required = true) @PathVariable UUID disbeacId);

    @Operation(summary = "Get location by ID", operationId = "getLocationById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Disbeac not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    ResponseEntity<Location> getById(
            @Parameter(description = "Disbeac ID", required = true) @PathVariable UUID id)
            throws NotFoundException;

    @Operation(summary = "Get all locations", operationId = "getAllLocation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    ResponseEntity<List<Location>> getAll();

    @Operation(summary = "Save location", operationId = "saveLocation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    ResponseEntity<Location> save(
            @Parameter(description = "Disbeac ID", required = true) @RequestBody LocationDTO locationDTO)
            throws NotFoundException, BadRequestException;

    @Operation(summary = "Delete location by id", operationId = "deleteLocation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Location not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Location> delete(
            @Parameter(description = "Location ID", required = true) @PathVariable UUID id)
            throws NotFoundException;

    @Operation(summary = "Delete all locations by disbeac ID", operationId = "deleteByDisbeacId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Disbeac not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/disbeac/{disbeacId}")
    ResponseEntity<List<Location>> deleteByDisbeacId(
            @Parameter(description = "Disbeac ID", required = true) @PathVariable UUID disbeacId)
            throws NotFoundException;
}
