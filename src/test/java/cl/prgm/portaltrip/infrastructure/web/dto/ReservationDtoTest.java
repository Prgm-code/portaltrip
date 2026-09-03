package cl.prgm.portaltrip.infrastructure.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import cl.prgm.portaltrip.domain.model.Character;
import cl.prgm.portaltrip.domain.model.Location;
import cl.prgm.portaltrip.domain.model.Quote;
import cl.prgm.portaltrip.domain.model.Reservation;
import cl.prgm.portaltrip.domain.model.ReservationDraft;
import cl.prgm.portaltrip.domain.model.ReservationStatus;
import cl.prgm.portaltrip.domain.model.RiskLevel;
import cl.prgm.portaltrip.domain.model.TripType;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationDtoTest {

	@Test
	void requestNormalizesNullCompanionIdsAndComments() {
		ReservationRequestDto request = new ReservationRequestDto(
				"Rick Sanchez", 1, LocalDate.of(2099, 1, 1),
				2, null, "express", false, null);

		assertThat(request.companionIds()).isEmpty();
		assertThat(request.comments()).isEmpty();
	}

	@Test
	void requestMapsToDraft() {
		ReservationRequestDto request = new ReservationRequestDto(
				"Rick Sanchez", 1, LocalDate.of(2099, 1, 1),
				2, List.of(2), "premium", true, "stable portal");

		ReservationDraft draft = request.toDraft();

		assertThat(draft.passengerName()).isEqualTo("Rick Sanchez");
		assertThat(draft.destinationId()).isEqualTo(1);
		assertThat(draft.travelDate()).isEqualTo(LocalDate.of(2099, 1, 1));
		assertThat(draft.passengers()).isEqualTo(2);
		assertThat(draft.companionIds()).containsExactly(2);
		assertThat(draft.tripType()).isEqualTo(TripType.PREMIUM);
		assertThat(draft.insurance()).isTrue();
		assertThat(draft.comments()).isEqualTo("stable portal");
	}

	@Test
	void responseMapsReservationWithDestinationAndCompanions() {
		Reservation reservation = reservation();
		Location destination = new Location(1, "Earth (C-137)", "Planet", "Dimension C-137", List.of(1, 2, 3, 4, 5));
		Character morty = new Character(
				2, "Morty Smith", "Alive", "Human", "", "Male",
				1, "Earth (C-137)", 1, "Earth (C-137)", "img-morty", List.of());

		ReservationResponseDto dto = ReservationResponseDto.from(reservation, destination, List.of(morty));

		assertThat(dto.id()).isEqualTo(reservation.id());
		assertThat(dto.number()).isEqualTo("PT-2026-000001");
		assertThat(dto.status()).isEqualTo(ReservationStatus.CONFIRMED);
		assertThat(dto.passengerName()).isEqualTo("Rick Sanchez");
		assertThat(dto.email()).isEqualTo("rick@sanchez.dev");
		assertThat(dto.destination()).isEqualTo(new NamedRefDto(1, "Earth (C-137)"));
		assertThat(dto.travelDate()).isEqualTo(LocalDate.of(2099, 1, 1));
		assertThat(dto.passengers()).isEqualTo(2);
		assertThat(dto.companions()).containsExactly(new CompanionDto(2, "Morty Smith", "img-morty"));
		assertThat(dto.tripType()).isEqualTo("express");
		assertThat(dto.insurance()).isTrue();
		assertThat(dto.comments()).isEqualTo("stable portal");
		assertThat(dto.quote().total()).isEqualByComparingTo(new BigDecimal("1776.00"));
		assertThat(dto.quote().risk()).isEqualTo(RiskLevel.LOW);
		assertThat(dto.createdAt()).isEqualTo(OffsetDateTime.parse("2026-01-01T10:00:00Z"));
		assertThat(dto.startedAt()).isNull();
		assertThat(dto.completedAt()).isNull();
	}

	private static Reservation reservation() {
		Quote quote = new Quote(
				new BigDecimal("1200"),
				BigDecimal.ZERO,
				new BigDecimal("216.00"),
				BigDecimal.ZERO,
				new BigDecimal("360"),
				new BigDecimal("1776.00"),
				RiskLevel.LOW);
		return new Reservation(
				UUID.fromString("00000000-0000-0000-0000-000000000001"),
				UUID.fromString("00000000-0000-0000-0000-000000000002"),
				UUID.fromString("00000000-0000-0000-0000-000000000003"),
				"PT-2026-000001",
				ReservationStatus.CONFIRMED,
				"Rick Sanchez",
				"rick@sanchez.dev",
				1,
				LocalDate.of(2099, 1, 1),
				2,
				List.of(2),
				TripType.EXPRESS,
				true,
				"stable portal",
				quote,
				OffsetDateTime.parse("2026-01-01T10:00:00Z"),
				null,
				null);
	}

}
