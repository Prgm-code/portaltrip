package cl.prgm.portaltrip.infrastructure.web.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.prgm.portaltrip.application.service.CharacterService;
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
	@Operation(summary = "Listar characters")
	public List<CharacterResponseDto> findAll() {
		return characterService.findAll().stream()
				.map(CharacterResponseDto::from)
				.toList();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtener character por id")
	public CharacterResponseDto findById(@PathVariable @Min(1) Integer id) {
		return CharacterResponseDto.from(characterService.findById(id));
	}

}
