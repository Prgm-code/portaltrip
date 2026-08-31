package cl.prgm.portaltrip.infrastructure.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.prgm.portaltrip.application.service.LocationService;
import cl.prgm.portaltrip.infrastructure.web.dto.ApiResponseDto;
import cl.prgm.portaltrip.infrastructure.web.dto.LocationResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;

@Validated
@RestController
@RequestMapping(path = "/api/v1/locations", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Locations")
public class LocationController {

	private final LocationService locationService;

	public LocationController(LocationService locationService) {
		this.locationService = locationService;
	}

	@GetMapping
	@Operation(
			summary = "List locations",
			description = "Returns every location ordered by ID, including the IDs of its last known residents.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Locations retrieved", useReturnTypeSchema = true),
		@ApiResponse(
				responseCode = "500",
				description = "Internal server error",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class)))
	})
	public ApiResponseDto<List<LocationResponseDto>> findAll() {
		List<LocationResponseDto> locations = locationService.findAll().stream()
				.map(LocationResponseDto::from)
				.toList();
		return ApiResponseDto.success(HttpStatus.OK, "Locations retrieved successfully", locations);
	}

	@GetMapping("/{id}")
	@Operation(
			summary = "Get location by ID",
			description = "Returns a location with its resident IDs.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Location retrieved", useReturnTypeSchema = true),
		@ApiResponse(
				responseCode = "400",
				description = "Invalid location ID",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class))),
		@ApiResponse(
				responseCode = "404",
				description = "Location not found",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class))),
		@ApiResponse(
				responseCode = "500",
				description = "Internal server error",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class)))
	})
	public ApiResponseDto<LocationResponseDto> findById(@PathVariable @Min(1) Integer id) {
		LocationResponseDto location = LocationResponseDto.from(locationService.findById(id));
		return ApiResponseDto.success(HttpStatus.OK, "Location retrieved successfully", location);
	}

}
