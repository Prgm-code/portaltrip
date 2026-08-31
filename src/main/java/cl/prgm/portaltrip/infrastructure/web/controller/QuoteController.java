package cl.prgm.portaltrip.infrastructure.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.prgm.portaltrip.application.service.QuoteQuery;
import cl.prgm.portaltrip.application.service.QuoteService;
import cl.prgm.portaltrip.domain.model.TripType;
import cl.prgm.portaltrip.infrastructure.web.dto.ApiResponseDto;
import cl.prgm.portaltrip.infrastructure.web.dto.QuoteDto;
import cl.prgm.portaltrip.infrastructure.web.dto.QuoteRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping(path = "/api/v1/quotes", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Quotes")
public class QuoteController {

	private final QuoteService quoteService;

	public QuoteController(QuoteService quoteService) {
		this.quoteService = quoteService;
	}

	@PostMapping
	@Operation(
			summary = "Quote an interdimensional trip",
			description = "Calculates the price breakdown and destination risk without persisting data. Insurance is mandatory when the destination dimension is unknown.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Quote calculated", useReturnTypeSchema = true),
		@ApiResponse(
				responseCode = "400",
				description = "Invalid quote request",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class))),
		@ApiResponse(
				responseCode = "404",
				description = "Destination not found",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class))),
		@ApiResponse(
				responseCode = "500",
				description = "Internal server error",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class)))
	})
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
