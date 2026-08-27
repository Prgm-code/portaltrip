package cl.prgm.portaltrip.infrastructure.web.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.prgm.portaltrip.application.service.LocationService;
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
	@Operation(summary = "Listar locations")
	public List<LocationResponseDto> findAll() {
		return locationService.findAll().stream()
				.map(LocationResponseDto::from)
				.toList();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtener location por id")
	public LocationResponseDto findById(@PathVariable @Min(1) Integer id) {
		return LocationResponseDto.from(locationService.findById(id));
	}

}
