package cl.prgm.portaltrip.infrastructure.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.prgm.portaltrip.application.service.CharacterService;
import cl.prgm.portaltrip.infrastructure.web.dto.ApiResponseDto;
import cl.prgm.portaltrip.infrastructure.web.dto.CharacterResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;

@Validated
@RestController
@RequestMapping(path = "/api/v1/characters", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Characters")
public class CharacterController {

	private final CharacterService characterService;

	public CharacterController(CharacterService characterService) {
		this.characterService = characterService;
	}

	@GetMapping
	@Operation(
			summary = "List characters",
			description = "Returns every character ordered by ID, including origin and last known location. The list omits episode IDs; use the detail endpoint to retrieve them.")
	@ApiResponses({
		@ApiResponse(
				responseCode = "200",
				description = "Characters retrieved",
				useReturnTypeSchema = true),
		@ApiResponse(
				responseCode = "500",
				description = "Internal server error",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class)))
	})
	public ApiResponseDto<List<CharacterResponseDto>> findAll() {
		List<CharacterResponseDto> characters = characterService.findAll().stream()
				.map(CharacterResponseDto::from)
				.toList();
		return ApiResponseDto.success(HttpStatus.OK, "Characters retrieved successfully", characters);
	}

	@GetMapping("/{id}")
	@Operation(
			summary = "Get character by ID",
			description = "Returns a character with the IDs of the episodes in which it appears.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Character retrieved", useReturnTypeSchema = true),
		@ApiResponse(
				responseCode = "400",
				description = "Invalid character ID",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class))),
		@ApiResponse(
				responseCode = "404",
				description = "Character not found",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class))),
		@ApiResponse(
				responseCode = "500",
				description = "Internal server error",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class)))
	})
	public ApiResponseDto<CharacterResponseDto> findById(@PathVariable @Min(1) Integer id) {
		CharacterResponseDto character = CharacterResponseDto.from(characterService.findById(id));
		return ApiResponseDto.success(HttpStatus.OK, "Character retrieved successfully", character);
	}

}
