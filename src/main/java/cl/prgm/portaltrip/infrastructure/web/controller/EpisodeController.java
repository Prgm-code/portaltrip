package cl.prgm.portaltrip.infrastructure.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.prgm.portaltrip.application.port.in.EpisodeService;
import cl.prgm.portaltrip.infrastructure.web.dto.ApiResponseDto;
import cl.prgm.portaltrip.infrastructure.web.dto.EpisodeResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("/api/v1/episodes")
@Tag(name = "Episodes")
public class EpisodeController {

	private final EpisodeService episodeService;

	public EpisodeController(EpisodeService episodeService) {
		this.episodeService = episodeService;
	}

	@GetMapping
	@Operation(
			summary = "Listar episodes",
			description = "Devuelve todos los episodios ordenados por id (orden de emisión), cada uno con sus characterIds (personajes que aparecen).")
	public ApiResponseDto<List<EpisodeResponseDto>> findAll() {
		List<EpisodeResponseDto> episodes = episodeService.findAll().stream()
				.map(EpisodeResponseDto::from)
				.toList();
		return ApiResponseDto.success(HttpStatus.OK, "Episodes retrieved successfully", episodes);
	}

	@GetMapping("/{id}")
	@Operation(
			summary = "Obtener episode por id",
			description = "Devuelve el episodio con sus characterIds (personajes que aparecen). Responde 404 si no existe.")
	public ApiResponseDto<EpisodeResponseDto> findById(@PathVariable @Min(1) Integer id) {
		EpisodeResponseDto episode = EpisodeResponseDto.from(episodeService.findById(id));
		return ApiResponseDto.success(HttpStatus.OK, "Episode retrieved successfully", episode);
	}

}
