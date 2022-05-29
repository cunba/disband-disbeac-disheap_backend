package com.cpilosenlaces.disheap_backend.controller;

import java.util.List;
import java.util.UUID;

import com.cpilosenlaces.disheap_backend.exception.BadRequestException;
import com.cpilosenlaces.disheap_backend.exception.ErrorResponse;
import com.cpilosenlaces.disheap_backend.exception.NotFoundException;
import com.cpilosenlaces.disheap_backend.model.Alarm;
import com.cpilosenlaces.disheap_backend.model.dto.AlarmDTO;

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
import org.springframework.web.bind.annotation.RequestParam;
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

@Secured({ "ROLE_ADIMN", "ROLE_USER" })
@Tag(name = "Alarms", description = "Alarm API")
@Validated
@RestController
@RequestMapping(value = "/alarms", produces = { MediaType.APPLICATION_JSON_VALUE })
@SecurityScheme(name = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
@SecurityRequirements
@SecurityRequirement(name = "bearer")
public interface AlarmApi {
    @Operation(summary = "Get alarm by ID", operationId = "getById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Alarm not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Alarm> getById(
            @Parameter(description = "Alarm ID", required = true) @PathVariable UUID id)
            throws NotFoundException;

    @Operation(summary = "Get alarms by disband ID", operationId = "getAlarmsByDisbandId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/disbands/{disbandId}")
    public ResponseEntity<List<Alarm>> getByDisbandId(
            @Parameter(description = "Disband ID", required = true) @PathVariable UUID disbandId)
            throws BadRequestException;

    @Operation(summary = "Get alarms by date between and disband ID", operationId = "getAlarmsByDateBetweenAndDisbandId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/date/between/disbands/{disbandId}")
    public ResponseEntity<List<Alarm>> getByDateBetweenAndDisbandId(
            @Parameter(description = "Min date", required = true) @RequestParam(value = "min date") long minDate,
            @Parameter(description = "Max date", required = true) @RequestParam(value = "max date") long maxDate,
            @Parameter(description = "Disband ID", required = true) @PathVariable UUID disbandId)
            throws BadRequestException;

    @Operation(summary = "Save alarm", operationId = "saveAlarm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Disband not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<Alarm> save(
            @Parameter(description = "Alarm object", required = true) @RequestBody AlarmDTO alarmDTO)
            throws NotFoundException;

    @Operation(summary = "Update alarm", operationId = "updateAlarm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Alarm not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> update(
            @Parameter(description = "Alarm id", required = true) @PathVariable UUID id,
            @Parameter(description = "Alarm object", required = true) @RequestBody AlarmDTO alarmDTO)
            throws NotFoundException;

    @Operation(summary = "Delete alarm", operationId = "deleteAlarm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Alarm not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Alarm> delete(
            @Parameter(description = "Alarm id", required = true) @PathVariable UUID id)
            throws NotFoundException;

    @Operation(summary = "Delete alarms by disband ID", operationId = "deleteAlarmsByDisbandId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/disbands/{id}")
    public ResponseEntity<List<Alarm>> deleteByDisbandId(
            @Parameter(description = "Disband id", required = true) @PathVariable UUID disbandId);

    @Secured({ "ROLE_ADIMN" })
    @Operation(summary = "Delete all alarms", operationId = "deleteAllAlarms")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping
    public ResponseEntity<List<Alarm>> deleteAll();
}
