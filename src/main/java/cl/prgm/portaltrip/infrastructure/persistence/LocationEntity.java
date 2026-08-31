package cl.prgm.portaltrip.infrastructure.persistence;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import cl.prgm.portaltrip.domain.model.Location;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "locations")
public class LocationEntity {

	@Id
	private Integer id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String type;

	@Column(nullable = false)
	private String dimension;

	@ManyToMany
	@JoinTable(
			name = "location_residents",
			joinColumns = @JoinColumn(name = "location_id"),
			inverseJoinColumns = @JoinColumn(name = "character_id"))
	private Set<CharacterEntity> residents = new LinkedHashSet<>();

	protected LocationEntity() {
	}

	public LocationEntity(Integer id, String name, String type, String dimension) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.dimension = dimension;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getDimension() {
		return dimension;
	}

	public Set<CharacterEntity> getResidents() {
		return residents;
	}

	public Location toDomain() {
		List<Integer> residentIds = residents.stream()
				.map(CharacterEntity::getId)
				.sorted()
				.toList();
		return new Location(id, name, type, dimension, residentIds);
	}

}
