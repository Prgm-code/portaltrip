package cl.prgm.portaltrip.infrastructure.persistence;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import cl.prgm.portaltrip.domain.model.PortalStipendBeat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "portal_stipends")
public class PortalStipendEntity {

	@Id
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;

	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal amount;

	@Column(name = "created_at", nullable = false)
	private OffsetDateTime createdAt;

	protected PortalStipendEntity() {
	}

	public static PortalStipendEntity create(UserEntity user, BigDecimal amount, OffsetDateTime now) {
		PortalStipendEntity entity = new PortalStipendEntity();
		entity.id = UUID.randomUUID();
		entity.user = user;
		entity.amount = amount;
		entity.createdAt = now;
		return entity;
	}

	public UUID getId() {
		return id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}

	public PortalStipendBeat toBeat() {
		return new PortalStipendBeat(createdAt, amount);
	}

}
