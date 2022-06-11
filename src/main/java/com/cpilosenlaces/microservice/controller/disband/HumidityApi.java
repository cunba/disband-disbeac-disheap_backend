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
import com.cpilosenlaces.microservice.model.disband.Humidity;
import com.cpilosenlaces.microservice.model.disband.MeasureResponse;
import com.cpilosenlaces.microservice.model.disband.dto.MeasureDTO;

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

@Tag(name = "Humidity", description = "Humidity API")
@Validated
@RestController
@RequestMapping(value = "/humidities", produces = { MediaType.APPLICATION_JSON_VALUE })
@SecurityScheme(name = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
@SecurityRequirements
@SecurityRequirement(name = "bearer")
public interface HumidityApi {

	@Operation(summary = "Get last humidity by disband ID", operationId = "getLast1HumidityByDateBetweenAndDisbandId")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/last/disbandId/{disbandId}")
	ResponseEntity<Humidity> getLast1ByDateBetweenAndDisbandId(
			@Parameter(description = "Min date", required = true) @RequestParam(value = "min date") long minDate,
			@Parameter(description = "Max date", required = true) @RequestParam(value = "max date") long maxDate,
			@Parameter(description = "Disband ID", required = true) @PathVariable UUID disbandId);

	@Operation(summary = "Get all humidities", operationId = "getAllHumidities")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping
	public ResponseEntity<List<Humidity>> getAll();

	@Operation(summary = "Get humidity by ID", operationId = "getHumidityById")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/{id}")
	public ResponseEntity<Humidity> getById(
			@Parameter(description = "Humidity ID", required = true) @PathVariable UUID id)
			throws NotFoundException;

	@Operation(summary = "Get humidities by disband ID", operationId = "getHumiditiesByDisbandId")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/disbands/{disbandId}")
	public ResponseEntity<List<Humidity>> getByDisbandId(
			@Parameter(description = "Disband ID", required = true) @PathVariable UUID userId);

	@Operation(summary = "Get humidities by date between and disband ID", operationId = "getHumiditiesByDateBetweenAndDisbandId")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/date/between/disband/{disbandId}")
	public ResponseEntity<MeasureResponse<Humidity>> getByDateBetweenAndDisbandId(
			@Parameter(description = "Min date", required = true) @RequestParam(value = "min date") long minDate,
			@Parameter(description = "Max date", required = true) @RequestParam(value = "max date") long maxDate,
			@Parameter(description = "Disband ID", required = true) @PathVariable UUID disbandId);

	@Operation(summary = "Get humidities by date between", operationId = "getHumiditiesByDateBetween")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/date/between")
	public ResponseEntity<List<Humidity>> getByDateBetween(
			@Parameter(description = "Min date", required = true) @RequestParam(value = "min date") long minDate,
			@Parameter(description = "Max date", required = true) @RequestParam(value = "max date") long maxDate);

	@Operation(summary = "Save humidity", operationId = "saveHumidity")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "CREATED"),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping
	public ResponseEntity<Humidity> save(
			@Parameter(description = "Measure object", required = true) @RequestBody MeasureDTO measureDTO)
			throws NotFoundException, BadRequestException;

	@Operation(summary = "Delete humidities by disband ID", operationId = "deleteHumiditiesByDisbandId")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@DeleteMapping("/disbands/{disbandId}")
	public ResponseEntity<List<Humidity>> deleteByDisbandId(
			@Parameter(description = "Disband id", required = true) @PathVariable UUID disbandId)
			throws NotFoundException;
}
