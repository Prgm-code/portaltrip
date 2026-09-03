package cl.prgm.portaltrip.infrastructure.persistence;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import cl.prgm.portaltrip.domain.model.UserAccount;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "users")
public class UserEntity {

	@Id
	private UUID id;

	@Column(nullable = false, unique = true, length = 320)
	private String email;

	@Column(name = "password_hash", nullable = false)
	private String passwordHash;

	@Column(name = "full_name", nullable = false, length = 100)
	private String fullName;

	@Column(nullable = false, length = 30)
	private String role;

	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal balance;

	@Version
	@Column(nullable = false)
	private long version;

	@Column(name = "created_at", nullable = false)
	private OffsetDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	private OffsetDateTime updatedAt;

	protected UserEntity() {
	}

	public UUID getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public String getRole() {
		return role;
	}

	public UserAccount toDomain() {
		return new UserAccount(id, email, passwordHash, fullName, role, balance, createdAt, updatedAt);
	}

	public static UserEntity fromDomain(UserAccount user) {
		UserEntity entity = new UserEntity();
		entity.id = user.id();
		entity.email = user.email();
		entity.passwordHash = user.passwordHash();
		entity.fullName = user.fullName();
		entity.role = user.role();
		entity.balance = user.balance();
		entity.createdAt = user.createdAt();
		entity.updatedAt = user.updatedAt();
		return entity;
	}

	public void apply(UserAccount user) {
		if (!id.equals(user.id())) {
			throw new IllegalArgumentException("Cannot apply a different user account");
		}
		email = user.email();
		passwordHash = user.passwordHash();
		fullName = user.fullName();
		role = user.role();
		balance = user.balance();
		createdAt = user.createdAt();
		updatedAt = user.updatedAt();
	}

}
