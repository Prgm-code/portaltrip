package cl.prgm.portaltrip.application.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.prgm.portaltrip.domain.exception.PortalStipendCooldownException;
import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.PortalStipendBeat;
import cl.prgm.portaltrip.domain.model.PortalStipendResult;
import cl.prgm.portaltrip.domain.model.UserAccount;
import cl.prgm.portaltrip.domain.service.PortalStipendCalculator;
import cl.prgm.portaltrip.infrastructure.persistence.PortalStipendEntity;
import cl.prgm.portaltrip.infrastructure.persistence.UserEntity;
import cl.prgm.portaltrip.infrastructure.persistence.repository.PortalStipendJpaRepository;
import cl.prgm.portaltrip.infrastructure.persistence.repository.UserJpaRepository;

@Service
public class PortalStipendServiceImpl implements PortalStipendService {

	private final UserJpaRepository userJpaRepository;
	private final PortalStipendJpaRepository portalStipendJpaRepository;
	private final PortalStipendCalculator calculator;

	public PortalStipendServiceImpl(
			UserJpaRepository userJpaRepository,
			PortalStipendJpaRepository portalStipendJpaRepository,
			PortalStipendCalculator calculator) {
		this.userJpaRepository = userJpaRepository;
		this.portalStipendJpaRepository = portalStipendJpaRepository;
		this.calculator = calculator;
	}

	@Override
	@Transactional
	public PortalStipendResult claim(UUID userId, double movement, long activeMs) {
		UserEntity userEntity = userJpaRepository.findByIdForUpdate(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", userId));
		OffsetDateTime now = OffsetDateTime.now();
		OffsetDateTime since = now.minus(Duration.ofMillis(PortalStipendCalculator.EARN_WINDOW_MS));
		List<PortalStipendBeat> recent = portalStipendJpaRepository
				.findByUser_IdAndCreatedAtGreaterThanEqualOrderByCreatedAtAsc(userId, since)
				.stream()
				.map(PortalStipendEntity::toBeat)
				.toList();
		if (calculator.coolingDown(recent, now)) {
			throw new PortalStipendCooldownException();
		}
		BigDecimal payout = calculator.sample(recent, now, movement, activeMs);
		portalStipendJpaRepository.save(PortalStipendEntity.create(userEntity, payout, now));
		UserAccount credited = userEntity.toDomain().credit(payout, now);
		userEntity.apply(credited);
		return new PortalStipendResult(payout, credited);
	}

}
