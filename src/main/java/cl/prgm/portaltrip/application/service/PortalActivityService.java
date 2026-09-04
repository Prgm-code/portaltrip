package cl.prgm.portaltrip.application.service;

import java.time.OffsetDateTime;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.infrastructure.persistence.PortalActivityEntity;
import cl.prgm.portaltrip.infrastructure.persistence.UserEntity;
import cl.prgm.portaltrip.infrastructure.persistence.repository.PortalActivityJpaRepository;
import cl.prgm.portaltrip.infrastructure.persistence.repository.UserJpaRepository;
import cl.prgm.portaltrip.infrastructure.web.dto.PortalActivityRequestDto;
import cl.prgm.portaltrip.infrastructure.web.dto.PortalActivityResponseDto;

@Service
public class PortalActivityService {
	private final UserJpaRepository users;
	private final PortalActivityJpaRepository activities;
	private final PortalStipendService stipends;

	public PortalActivityService(UserJpaRepository users, PortalActivityJpaRepository activities,
			PortalStipendService stipends) {
		this.users = users;
		this.activities = activities;
		this.stipends = stipends;
	}

	@Transactional
	public PortalActivityResponseDto start(UUID userId) {
		UserEntity user = lock(userId);
		OffsetDateTime now = OffsetDateTime.now();
		PortalActivityEntity activity = activities.findById(userId).orElseGet(
				() -> activities.save(PortalActivityEntity.start(userId, now)));
		if (activity.completed() || activity.expired(now)) activity.restart(now);
		return response(activity, user);
	}

	@Transactional
	public PortalActivityResponseDto report(UUID userId, PortalActivityRequestDto sample) {
		UserEntity user = lock(userId);
		PortalActivityEntity activity = activities.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Portal activity", userId));
		activity.sample(sample.cycleId(), sample.sequence(), sample.activeMs(), sample.distance(), OffsetDateTime.now());
		if (!activity.completed() && activity.progress() >= 1) {
			activity.complete(stipends.claim(userId, activity.movement(), activity.activeDurationMs()).payout());
		}
		return response(activity, user);
	}

	private UserEntity lock(UUID userId) {
		return users.findByIdForUpdate(userId).orElseThrow(() -> new ResourceNotFoundException("User", userId));
	}

	private PortalActivityResponseDto response(PortalActivityEntity activity, UserEntity user) {
		return new PortalActivityResponseDto(activity.cycleId(), activity.nextSequence(), activity.progress(),
				activity.payout(), user.toDomain().balance());
	}
}
