package cl.prgm.portaltrip.infrastructure.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.prgm.portaltrip.application.port.in.QuoteQuery;
import cl.prgm.portaltrip.application.port.in.QuoteService;
import cl.prgm.portaltrip.domain.model.TripType;
import cl.prgm.portaltrip.infrastructure.web.dto.ApiResponseDto;
import cl.prgm.portaltrip.infrastructure.web.dto.QuoteDto;
import cl.prgm.portaltrip.infrastructure.web.dto.QuoteRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/v1/quotes")
@Tag(name = "Quotes")
public class QuoteController {

	private final QuoteService quoteService;

	public QuoteController(QuoteService quoteService) {
		this.quoteService = quoteService;
	}

	@PostMapping
	@Operation(
			summary = "Cotizar un viaje interdimensional",
			description = "Calcula el desglose del precio (base 1200, recargo por estación espacial, por pasajeros extra y por tipo de viaje, más seguro) y el nivel de riesgo del destino, sin persistir nada. Si la dimensión del destino es 'unknown', el seguro se fuerza a true. Responde 404 si el destino no existe.")
	public ApiResponseDto<QuoteDto> quote(@Valid @RequestBody QuoteRequestDto request) {
		QuoteQuery query = new QuoteQuery(
				request.destinationId(),
				request.passengers(),
				TripType.fromCode(request.tripType()),
				request.insurance());
		QuoteDto quote = QuoteDto.from(quoteService.quote(query));
		return ApiResponseDto.success(HttpStatus.OK, "Quote calculated successfully", quote);
	}

}
