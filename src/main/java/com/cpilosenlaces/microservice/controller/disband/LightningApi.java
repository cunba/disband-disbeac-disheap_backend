package com.cpilosenlaces.microservice.controller.disband;

import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cpilosenlaces.microservice.exception.BadRequestException;
import com.cpilosenlaces.microservice.exception.ErrorResponse;
import com.cpilosenlaces.microservice.exception.NotFoundException;
import com.cpilosenlaces.microservice.model.disband.Lightning;
import com.cpilosenlaces.microservice.model.disband.dto.LightningDTO;

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

@Tag(name = "Lightnings", description = "Lightning API")
@Validated
@RestController
@RequestMapping(value = "/lightnings", produces = { MediaType.APPLICATION_JSON_VALUE })
@SecurityScheme(name = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
@SecurityRequirements
@SecurityRequirement(name = "bearer")
public interface LightningApi {

	@Operation(summary = "Get last lightning by disband ID", operationId = "getLast1ByDateBetweenAndDisbandId")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/last/disbandId/{disbandId}")
	ResponseEntity<Lightning> getLast1ByDateBetweenAndDisbandId(
			@Parameter(description = "Min date", required = true) @RequestParam(value = "min date") long minDate,
			@Parameter(description = "Max date", required = true) @RequestParam(value = "max date") long maxDate,
			@Parameter(description = "Disband ID", required = true) @PathVariable UUID disbandId);

	@Operation(summary = "Get all lightnings", operationId = "getAllLightning")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping
	public ResponseEntity<List<Lightning>> getAll();

	@Operation(summary = "Get lightnint by ID", operationId = "getById")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/{id}")
	public ResponseEntity<Lightning> getById(
			@Parameter(description = "Lightnint ID", required = true) @PathVariable UUID id)
			throws NotFoundException;

	@Operation(summary = "Get lightnings by disband ID", operationId = "getLightningsByDisbandId")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/disbands/{disbandId}")
	public ResponseEntity<List<Lightning>> getByDisbandId(
			@Parameter(description = "Disband ID", required = true) @PathVariable UUID userId);

	@Operation(summary = "Get lightnings by date between and disband ID", operationId = "getLightningsByDateBetweenAndDisbandId")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/date/between/disband/{disbandId}")
	public ResponseEntity<List<Lightning>> getByDateBetweenAndDisbandId(
			@Parameter(description = "Min date", required = true) @RequestParam(value = "min date") long minDate,
			@Parameter(description = "Max date", required = true) @RequestParam(value = "max date") long maxDate,
			@Parameter(description = "Disband ID", required = true) @PathVariable UUID disbandId);

	@Operation(summary = "Get lightnings by date between", operationId = "getLightningsByDateBetween")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/date/between")
	public ResponseEntity<List<Lightning>> getByDateBetween(
			@Parameter(description = "Min date", required = true) @RequestParam(value = "min date") long minDate,
			@Parameter(description = "Max date", required = true) @RequestParam(value = "max date") long maxDate);

	@Operation(summary = "Save lightnint", operationId = "saveLightning")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "CREATED"),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping
	public ResponseEntity<Lightning> save(
			@Parameter(description = "Measure object", required = true) @RequestBody LightningDTO LightningDTO)
			throws NotFoundException, BadRequestException;

	@Operation(summary = "Delete lightnings by disband ID", operationId = "deleteLightningsByDisbandId")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@DeleteMapping("/disbands/{disbandId}")
	public ResponseEntity<List<Lightning>> deleteByDisbandId(
			@Parameter(description = "Disband id", required = true) @PathVariable UUID disbandId)
			throws NotFoundException;
}
