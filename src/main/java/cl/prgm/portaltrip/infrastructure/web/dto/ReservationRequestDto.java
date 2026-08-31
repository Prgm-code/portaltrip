package cl.prgm.portaltrip.infrastructure.web.dto;

import java.time.LocalDate;
import java.util.List;

import cl.prgm.portaltrip.domain.model.ReservationDraft;
import cl.prgm.portaltrip.domain.model.TripType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ReservationRequestDto(
		@NotBlank String passengerName,
		@NotBlank @Email String email,
		@NotNull @Min(1) Integer destinationId,
		@NotNull @Future LocalDate travelDate,
		@Min(1) @Max(8) int passengers,
		@Size(max = 3) List<Integer> companionIds,
		@NotBlank @Pattern(regexp = "express|exploration|premium") String tripType,
		boolean insurance,
		String comments) {

	public ReservationRequestDto {
		if (companionIds == null) {
			companionIds = List.of();
		}
		if (comments == null) {
			comments = "";
		}
	}

	public ReservationDraft toDraft() {
		return new ReservationDraft(
				passengerName,
				email,
				destinationId,
				travelDate,
				passengers,
				companionIds,
				TripType.fromCode(tripType),
				insurance,
				comments);
	}

}
