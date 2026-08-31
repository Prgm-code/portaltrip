package cl.prgm.portaltrip.infrastructure.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.prgm.portaltrip.application.port.in.CharacterService;
import cl.prgm.portaltrip.infrastructure.web.dto.ApiResponseDto;
import cl.prgm.portaltrip.infrastructure.web.dto.CharacterResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("/api/v1/characters")
@Tag(name = "Characters")
public class CharacterController {

	private final CharacterService characterService;

	public CharacterController(CharacterService characterService) {
		this.characterService = characterService;
	}

	@GetMapping
	@Operation(
			summary = "Listar characters",
			description = "Devuelve todos los personajes ordenados por id, con su origen y última ubicación conocida. La lista no incluye los episodios (episodeIds vacío); para obtenerlos usa el detalle.")
	public ApiResponseDto<List<CharacterResponseDto>> findAll() {
		List<CharacterResponseDto> characters = characterService.findAll().stream()
				.map(CharacterResponseDto::from)
				.toList();
		return ApiResponseDto.success(HttpStatus.OK, "Characters retrieved successfully", characters);
	}

	@GetMapping("/{id}")
	@Operation(
			summary = "Obtener character por id",
			description = "Devuelve el personaje con sus episodeIds (episodios en los que aparece). Responde 404 si no existe.")
	public ApiResponseDto<CharacterResponseDto> findById(@PathVariable @Min(1) Integer id) {
		CharacterResponseDto character = CharacterResponseDto.from(characterService.findById(id));
		return ApiResponseDto.success(HttpStatus.OK, "Character retrieved successfully", character);
	}

}
