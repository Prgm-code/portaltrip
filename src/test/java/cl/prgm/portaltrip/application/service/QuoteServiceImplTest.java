package cl.prgm.portaltrip.application.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.prgm.portaltrip.application.port.in.QuoteQuery;
import cl.prgm.portaltrip.application.port.out.LocationRepository;
import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.Location;
import cl.prgm.portaltrip.domain.model.Quote;
import cl.prgm.portaltrip.domain.model.RiskLevel;
import cl.prgm.portaltrip.domain.model.TripType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuoteServiceImplTest {

	@Mock
	private LocationRepository locationRepository;

	@InjectMocks
	private QuoteServiceImpl quoteService;

	@Test
	void quotesWithRequestedInsurance() {
		when(locationRepository.findDetailedById(1)).thenReturn(Optional.of(destination("Dimension C-137")));

		Quote quote = quoteService.quote(new QuoteQuery(1, 2, TripType.EXPLORATION, true));

		assertThat(quote.insuranceCost()).isEqualByComparingTo(new BigDecimal("380"));
		assertThat(quote.tripSurcharge()).isEqualByComparingTo(new BigDecimal("360.00"));
		assertThat(quote.total()).isEqualByComparingTo(new BigDecimal("2156.00"));
		assertThat(quote.risk()).isEqualTo(RiskLevel.LOW);
	}

	@Test
	void forcesInsuranceForUnknownDimension() {
		when(locationRepository.findDetailedById(1)).thenReturn(Optional.of(destination("unknown")));

		Quote quote = quoteService.quote(new QuoteQuery(1, 1, TripType.EXPRESS, false));

		assertThat(quote.insuranceCost()).isEqualByComparingTo(new BigDecimal("190"));
		assertThat(quote.total()).isEqualByComparingTo(new BigDecimal("1390"));
		assertThat(quote.risk()).isEqualTo(RiskLevel.MEDIUM);
	}

	@Test
	void skipsInsuranceWhenNotRequiredNorRequested() {
		when(locationRepository.findDetailedById(1)).thenReturn(Optional.of(destination("Dimension C-137")));

		Quote quote = quoteService.quote(new QuoteQuery(1, 1, TripType.EXPRESS, false));

		assertThat(quote.insuranceCost()).isEqualByComparingTo(BigDecimal.ZERO);
		assertThat(quote.total()).isEqualByComparingTo(new BigDecimal("1200"));
	}

	@Test
	void throwsWhenDestinationMissing() {
		when(locationRepository.findDetailedById(99)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> quoteService.quote(new QuoteQuery(99, 1, TripType.EXPRESS, false)))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("Location with id '99' not found");
	}

	private static Location destination(String dimension) {
		return new Location(1, "Earth (C-137)", "Planet", dimension, List.of(1, 2, 3, 4, 5));
	}

}
