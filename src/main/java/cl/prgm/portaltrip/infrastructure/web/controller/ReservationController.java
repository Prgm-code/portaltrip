package cl.prgm.portaltrip.infrastructure.web.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.prgm.portaltrip.application.port.in.CharacterService;
import cl.prgm.portaltrip.application.port.in.LocationService;
import cl.prgm.portaltrip.application.port.in.ReservationService;
import cl.prgm.portaltrip.domain.model.Character;
import cl.prgm.portaltrip.domain.model.Location;
import cl.prgm.portaltrip.domain.model.Reservation;
import cl.prgm.portaltrip.infrastructure.web.dto.ApiResponseDto;
import cl.prgm.portaltrip.infrastructure.web.dto.ReservationRequestDto;
import cl.prgm.portaltrip.infrastructure.web.dto.ReservationResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/v1/reservations")
@Tag(name = "Reservations")
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
			summary = "Crear una reserva",
			description = "Valida el borrador (pasajero, email, fecha futura, 1-8 pasajeros, máximo 3 acompañantes vivos, seguro obligatorio en dimensiones 'unknown'), calcula la cotización en el servidor y persiste la reserva con status CONFIRMED y número PT-<año>-<6 dígitos>. Devuelve la reserva con destino, acompañantes y desglose del quote. Responde 400 con la lista de errores si la validación falla.")
	public ApiResponseDto<ReservationResponseDto> create(@Valid @RequestBody ReservationRequestDto request) {
		ReservationResponseDto reservation = toResponse(reservationService.create(request.toDraft()));
		return ApiResponseDto.success(HttpStatus.CREATED, "Reservation created successfully", reservation);
	}

	@GetMapping
	@Operation(
			summary = "Listar reservas",
			description = "Devuelve todas las reservas, de la más reciente a la más antigua, con su destino, acompañantes, quote y estado actual.")
	public ApiResponseDto<List<ReservationResponseDto>> findAll() {
		List<ReservationResponseDto> reservations = reservationService.findAll().stream()
				.map(this::toResponse)
				.toList();
		return ApiResponseDto.success(HttpStatus.OK, "Reservations retrieved successfully", reservations);
	}

	@GetMapping("/{id}")
	@Operation(
			summary = "Obtener reserva por id",
			description = "Devuelve la reserva completa (destino, acompañantes, quote, estado y marcas de tiempo). Responde 404 si no existe.")
	public ApiResponseDto<ReservationResponseDto> findById(@PathVariable UUID id) {
		ReservationResponseDto reservation = toResponse(reservationService.findById(id));
		return ApiResponseDto.success(HttpStatus.OK, "Reservation retrieved successfully", reservation);
	}

	@PatchMapping("/{id}/cancel")
	@Operation(
			summary = "Cancelar una reserva",
			description = "Marca la reserva como CANCELLED. Responde 409 si ya está COMPLETED o CANCELLED.")
	public ApiResponseDto<ReservationResponseDto> cancel(@PathVariable UUID id) {
		ReservationResponseDto reservation = toResponse(reservationService.cancel(id));
		return ApiResponseDto.success(HttpStatus.OK, "Reservation cancelled successfully", reservation);
	}

	@PatchMapping("/{id}/start")
	@Operation(
			summary = "Iniciar una reserva",
			description = "Transición CONFIRMED → IN_PROGRESS; marca startedAt. Responde 409 si la reserva no está CONFIRMED.")
	public ApiResponseDto<ReservationResponseDto> start(@PathVariable UUID id) {
		ReservationResponseDto reservation = toResponse(reservationService.start(id));
		return ApiResponseDto.success(HttpStatus.OK, "Reservation started successfully", reservation);
	}

	@PatchMapping("/{id}/complete")
	@Operation(
			summary = "Completar una reserva",
			description = "Transición IN_PROGRESS → COMPLETED; marca completedAt. COMPLETED es un estado terminal. Responde 409 si la reserva no está en curso.")
	public ApiResponseDto<ReservationResponseDto> complete(@PathVariable UUID id) {
		ReservationResponseDto reservation = toResponse(reservationService.complete(id));
		return ApiResponseDto.success(HttpStatus.OK, "Reservation completed successfully", reservation);
	}

	private ReservationResponseDto toResponse(Reservation reservation) {
		Location destination = locationService.findById(reservation.destinationId());
		List<Character> companions = reservation.companionIds().stream()
				.map(characterService::findById)
				.toList();
		return ReservationResponseDto.from(reservation, destination, companions);
	}

}
