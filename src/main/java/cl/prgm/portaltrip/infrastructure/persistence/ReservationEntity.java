package cl.prgm.portaltrip.infrastructure.persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import cl.prgm.portaltrip.domain.model.Quote;
import cl.prgm.portaltrip.domain.model.Reservation;
import cl.prgm.portaltrip.domain.model.ReservationStatus;
import cl.prgm.portaltrip.domain.model.RiskLevel;
import cl.prgm.portaltrip.domain.model.TripType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
		name = "reservations",
		uniqueConstraints = @UniqueConstraint(
				name = "reservations_user_idempotency_key_uq",
				columnNames = {"user_id", "idempotency_key"}))
public class ReservationEntity {

	@Id
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;

	@Column(name = "idempotency_key", nullable = false)
	private UUID idempotencyKey;

	@Column(nullable = false, unique = true)
	private String number;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ReservationStatus status;

	@Column(name = "passenger_name", nullable = false)
	private String passengerName;

	@Column(nullable = false)
	private String email;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "destination_id", nullable = false)
	private LocationEntity destination;

	@Column(name = "travel_date", nullable = false)
	private LocalDate travelDate;

	@Column(nullable = false)
	private int passengers;

	@Column(name = "trip_type", nullable = false)
	private String tripType;

	@Column(nullable = false)
	private boolean insurance;

	@Column(nullable = false)
	private String comments;

	@Column(name = "base_price", nullable = false)
	private BigDecimal basePrice;

	@Column(name = "location_surcharge", nullable = false)
	private BigDecimal locationSurcharge;

	@Column(name = "passenger_surcharge", nullable = false)
	private BigDecimal passengerSurcharge;

	@Column(name = "trip_surcharge", nullable = false)
	private BigDecimal tripSurcharge;

	@Column(name = "insurance_cost", nullable = false)
	private BigDecimal insuranceCost;

	@Column(nullable = false)
	private BigDecimal total;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private RiskLevel risk;

	@Column(name = "created_at", nullable = false)
	private OffsetDateTime createdAt;

	@Column(name = "started_at")
	private OffsetDateTime startedAt;

	@Column(name = "completed_at")
	private OffsetDateTime completedAt;

	@ManyToMany
	@JoinTable(
			name = "reservation_companions",
			joinColumns = @JoinColumn(name = "reservation_id"),
			inverseJoinColumns = @JoinColumn(name = "character_id"))
	private Set<CharacterEntity> companions = new LinkedHashSet<>();

	protected ReservationEntity() {
	}

	public Reservation toDomain() {
		List<Integer> companionIds = companions.stream()
				.map(CharacterEntity::getId)
				.sorted()
				.toList();
		Quote quote = new Quote(
				basePrice,
				locationSurcharge,
				passengerSurcharge,
				tripSurcharge,
				insuranceCost,
				total,
				risk);
		return new Reservation(
				id,
				user.getId(),
				idempotencyKey,
				number,
				status,
				passengerName,
				email,
				destination.getId(),
				travelDate,
				passengers,
				companionIds,
				TripType.fromCode(tripType),
				insurance,
				comments,
				quote,
				createdAt,
				startedAt,
				completedAt);
	}

	public static ReservationEntity fromDomain(
			Reservation reservation,
			UserEntity user,
			LocationEntity destination,
			Set<CharacterEntity> companions) {
		ReservationEntity entity = new ReservationEntity();
		entity.id = reservation.id();
		entity.user = user;
		entity.idempotencyKey = reservation.idempotencyKey();
		entity.number = reservation.number();
		entity.status = reservation.status();
		entity.passengerName = reservation.passengerName();
		entity.email = reservation.email();
		entity.destination = destination;
		entity.travelDate = reservation.travelDate();
		entity.passengers = reservation.passengers();
		entity.tripType = reservation.tripType().code();
		entity.insurance = reservation.insurance();
		entity.comments = reservation.comments();
		entity.basePrice = reservation.quote().basePrice();
		entity.locationSurcharge = reservation.quote().locationSurcharge();
		entity.passengerSurcharge = reservation.quote().passengerSurcharge();
		entity.tripSurcharge = reservation.quote().tripSurcharge();
		entity.insuranceCost = reservation.quote().insuranceCost();
		entity.total = reservation.quote().total();
		entity.risk = reservation.quote().risk();
		entity.createdAt = reservation.createdAt();
		entity.startedAt = reservation.startedAt();
		entity.completedAt = reservation.completedAt();
		entity.companions = new LinkedHashSet<>(companions);
		return entity;
	}

}
