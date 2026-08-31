package cl.prgm.portaltrip.infrastructure.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.prgm.portaltrip.application.port.in.LocationService;
import cl.prgm.portaltrip.infrastructure.web.dto.ApiResponseDto;
import cl.prgm.portaltrip.infrastructure.web.dto.LocationResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("/api/v1/locations")
@Tag(name = "Locations")
public class LocationController {

	private final LocationService locationService;

	public LocationController(LocationService locationService) {
		this.locationService = locationService;
	}

	@GetMapping
	@Operation(
			summary = "Listar locations",
			description = "Devuelve todas las ubicaciones ordenadas por id, cada una con sus residentIds (personajes vistos por última vez en ella).")
	public ApiResponseDto<List<LocationResponseDto>> findAll() {
		List<LocationResponseDto> locations = locationService.findAll().stream()
				.map(LocationResponseDto::from)
				.toList();
		return ApiResponseDto.success(HttpStatus.OK, "Locations retrieved successfully", locations);
	}

	@GetMapping("/{id}")
	@Operation(
			summary = "Obtener location por id",
			description = "Devuelve la ubicación con sus residentIds. Responde 404 si no existe.")
	public ApiResponseDto<LocationResponseDto> findById(@PathVariable @Min(1) Integer id) {
		LocationResponseDto location = LocationResponseDto.from(locationService.findById(id));
		return ApiResponseDto.success(HttpStatus.OK, "Location retrieved successfully", location);
	}

}
