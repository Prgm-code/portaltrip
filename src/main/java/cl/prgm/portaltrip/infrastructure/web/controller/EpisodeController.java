package cl.prgm.portaltrip.infrastructure.web.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.prgm.portaltrip.application.service.EpisodeService;
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
	@Operation(summary = "Listar episodes")
	public List<EpisodeResponseDto> findAll() {
		return episodeService.findAll().stream()
				.map(EpisodeResponseDto::from)
				.toList();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtener episode por id")
	public EpisodeResponseDto findById(@PathVariable @Min(1) Integer id) {
		return EpisodeResponseDto.from(episodeService.findById(id));
	}

}
