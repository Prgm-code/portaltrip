package cl.prgm.portaltrip.infrastructure.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.prgm.portaltrip.application.service.EpisodeService;
import cl.prgm.portaltrip.infrastructure.web.dto.ApiResponseDto;
import cl.prgm.portaltrip.infrastructure.web.dto.EpisodeResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;

@Validated
@RestController
@RequestMapping(path = "/api/v1/episodes", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Episodes")
public class EpisodeController {

	private final EpisodeService episodeService;

	public EpisodeController(EpisodeService episodeService) {
		this.episodeService = episodeService;
	}

	@GetMapping
	@Operation(
			summary = "List episodes",
			description = "Returns every episode in broadcast order, including the IDs of its characters.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Episodes retrieved", useReturnTypeSchema = true),
		@ApiResponse(
				responseCode = "500",
				description = "Internal server error",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class)))
	})
	public ApiResponseDto<List<EpisodeResponseDto>> findAll() {
		List<EpisodeResponseDto> episodes = episodeService.findAll().stream()
				.map(EpisodeResponseDto::from)
				.toList();
		return ApiResponseDto.success(HttpStatus.OK, "Episodes retrieved successfully", episodes);
	}

	@GetMapping("/{id}")
	@Operation(
			summary = "Get episode by ID",
			description = "Returns an episode with the IDs of its characters.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Episode retrieved", useReturnTypeSchema = true),
		@ApiResponse(
				responseCode = "400",
				description = "Invalid episode ID",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class))),
		@ApiResponse(
				responseCode = "404",
				description = "Episode not found",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class))),
		@ApiResponse(
				responseCode = "500",
				description = "Internal server error",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class)))
	})
	public ApiResponseDto<EpisodeResponseDto> findById(@PathVariable @Min(1) Integer id) {
		EpisodeResponseDto episode = EpisodeResponseDto.from(episodeService.findById(id));
		return ApiResponseDto.success(HttpStatus.OK, "Episode retrieved successfully", episode);
	}

}
