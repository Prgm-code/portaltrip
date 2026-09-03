package cl.prgm.portaltrip.infrastructure.web.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.prgm.portaltrip.application.service.CharacterService;
import cl.prgm.portaltrip.application.service.LocationService;
import cl.prgm.portaltrip.application.service.ReservationService;
import cl.prgm.portaltrip.domain.model.Character;
import cl.prgm.portaltrip.domain.model.Location;
import cl.prgm.portaltrip.domain.model.Reservation;
import cl.prgm.portaltrip.domain.model.ReservationBalanceResult;
import cl.prgm.portaltrip.infrastructure.web.dto.ApiResponseDto;
import cl.prgm.portaltrip.infrastructure.web.dto.ReservationRequestDto;
import cl.prgm.portaltrip.infrastructure.web.dto.ReservationBalanceResponseDto;
import cl.prgm.portaltrip.infrastructure.web.dto.ReservationResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping(path = "/api/v1/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Reservations")
@SecurityRequirement(name = "bearerAuth")
public class ReservationController {

	private final ReservationService reservationService;
	private final LocationService locationService;
	private final CharacterService characterService;

	public ReservationController(
			ReservationService reservationService,
			LocationService locationService,
			CharacterService characterService) {
		this.reservationService = reservationService;
		this.locationService = locationService;
		this.characterService = characterService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(
			summary = "Create a reservation",
			description = "Validates the request, calculates the quote, and persists a confirmed reservation with its destination, companions, and price breakdown.")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Reservation created", useReturnTypeSchema = true),
		@ApiResponse(
				responseCode = "400",
				description = "Invalid reservation request",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class))),
		@ApiResponse(
				responseCode = "404",
				description = "Destination or companion not found",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class))),
		@ApiResponse(
				responseCode = "422",
				description = "Reservation violates a business rule",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class))),
		@ApiResponse(
				responseCode = "500",
				description = "Internal server error",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class)))
	})
	public ApiResponseDto<ReservationBalanceResponseDto> create(
			JwtAuthenticationToken authentication,
			@RequestHeader("Idempotency-Key") UUID idempotencyKey,
			@Valid @RequestBody ReservationRequestDto request) {
		ReservationBalanceResult result = reservationService.create(userId(authentication), idempotencyKey, request.toDraft());
		ReservationBalanceResponseDto response = new ReservationBalanceResponseDto(
				toResponse(result.reservation()),
				result.remainingBalance());
		return ApiResponseDto.success(HttpStatus.CREATED, "Reservation created successfully", response);
	}

	@GetMapping
	@Operation(
			summary = "List reservations",
			description = "Returns every reservation from newest to oldest with destination, companions, quote, and current status.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Reservations retrieved", useReturnTypeSchema = true),
		@ApiResponse(
				responseCode = "500",
				description = "Internal server error",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class)))
	})
	public ApiResponseDto<List<ReservationResponseDto>> findAll(JwtAuthenticationToken authentication) {
		List<ReservationResponseDto> reservations = reservationService.findAll(userId(authentication)).stream()
				.map(this::toResponse)
				.toList();
		return ApiResponseDto.success(HttpStatus.OK, "Reservations retrieved successfully", reservations);
	}

	@GetMapping("/{id}")
	@Operation(
			summary = "Get reservation by ID",
			description = "Returns a reservation with its destination, companions, quote, status, and timestamps.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Reservation retrieved", useReturnTypeSchema = true),
		@ApiResponse(
				responseCode = "400",
				description = "Invalid reservation ID",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class))),
		@ApiResponse(
				responseCode = "404",
				description = "Reservation not found",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class))),
		@ApiResponse(
				responseCode = "500",
				description = "Internal server error",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class)))
	})
	public ApiResponseDto<ReservationResponseDto> findById(
			JwtAuthenticationToken authentication,
			@PathVariable UUID id) {
		ReservationResponseDto reservation = toResponse(reservationService.findById(userId(authentication), id));
		return ApiResponseDto.success(HttpStatus.OK, "Reservation retrieved successfully", reservation);
	}

	@PatchMapping("/{id}/cancel")
	@Operation(
			summary = "Cancel a reservation",
			description = "Cancels a confirmed reservation and refunds its credits. Reservations in any other status cannot be cancelled.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Reservation cancelled", useReturnTypeSchema = true),
		@ApiResponse(
				responseCode = "400",
				description = "Invalid reservation ID",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class))),
		@ApiResponse(
				responseCode = "404",
				description = "Reservation not found",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class))),
		@ApiResponse(
				responseCode = "409",
				description = "Invalid reservation state transition",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class))),
		@ApiResponse(
				responseCode = "500",
				description = "Internal server error",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class)))
	})
	public ApiResponseDto<ReservationBalanceResponseDto> cancel(
			JwtAuthenticationToken authentication,
			@PathVariable UUID id) {
		ReservationBalanceResult result = reservationService.cancel(userId(authentication), id);
		ReservationBalanceResponseDto response = new ReservationBalanceResponseDto(
				toResponse(result.reservation()),
				result.remainingBalance());
		return ApiResponseDto.success(HttpStatus.OK, "Reservation cancelled successfully", response);
	}

	@PatchMapping("/{id}/start")
	@Operation(
			summary = "Start a reservation",
			description = "Moves a confirmed reservation to in progress and records its start time.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Reservation started", useReturnTypeSchema = true),
		@ApiResponse(
				responseCode = "400",
				description = "Invalid reservation ID",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class))),
		@ApiResponse(
				responseCode = "404",
				description = "Reservation not found",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class))),
		@ApiResponse(
				responseCode = "409",
				description = "Invalid reservation state transition",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class))),
		@ApiResponse(
				responseCode = "500",
				description = "Internal server error",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class)))
	})
	public ApiResponseDto<ReservationResponseDto> start(
			JwtAuthenticationToken authentication,
			@PathVariable UUID id) {
		ReservationResponseDto reservation = toResponse(reservationService.start(userId(authentication), id));
		return ApiResponseDto.success(HttpStatus.OK, "Reservation started successfully", reservation);
	}

	@PatchMapping("/{id}/complete")
	@Operation(
			summary = "Complete a reservation",
			description = "Moves an in-progress reservation to completed and records its completion time.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Reservation completed", useReturnTypeSchema = true),
		@ApiResponse(
				responseCode = "400",
				description = "Invalid reservation ID",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class))),
		@ApiResponse(
				responseCode = "404",
				description = "Reservation not found",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class))),
		@ApiResponse(
				responseCode = "409",
				description = "Invalid reservation state transition",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class))),
		@ApiResponse(
				responseCode = "500",
				description = "Internal server error",
				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = @Schema(implementation = ApiResponseDto.class)))
	})
	public ApiResponseDto<ReservationResponseDto> complete(
			JwtAuthenticationToken authentication,
			@PathVariable UUID id) {
		ReservationResponseDto reservation = toResponse(reservationService.complete(userId(authentication), id));
		return ApiResponseDto.success(HttpStatus.OK, "Reservation completed successfully", reservation);
	}

	private ReservationResponseDto toResponse(Reservation reservation) {
		Location destination = locationService.findById(reservation.destinationId());
		List<Character> companions = reservation.companionIds().stream()
				.map(characterService::findById)
				.toList();
		return ReservationResponseDto.from(reservation, destination, companions);
	}

	private UUID userId(JwtAuthenticationToken authentication) {
		return UUID.fromString(authentication.getToken().getClaimAsString("user_id"));
	}

}
