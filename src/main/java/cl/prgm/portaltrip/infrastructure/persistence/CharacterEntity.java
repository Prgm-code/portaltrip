package cl.prgm.portaltrip.infrastructure.persistence;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import cl.prgm.portaltrip.domain.model.Character;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "characters")
public class CharacterEntity {

	@Id
	private Integer id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String status;

	@Column(nullable = false)
	private String species;

	@Column(nullable = false)
	private String type;

	@Column(nullable = false)
	private String gender;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "origin_id")
	private LocationEntity origin;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	private LocationEntity location;

	@Column(nullable = false)
	private String image;

	@Column(nullable = false)
	private String url;

	@ManyToMany
	@JoinTable(
			name = "character_episodes",
			joinColumns = @JoinColumn(name = "character_id"),
			inverseJoinColumns = @JoinColumn(name = "episode_id"))
	private Set<EpisodeEntity> episodes = new LinkedHashSet<>();

	protected CharacterEntity() {
	}

	public CharacterEntity(
			Integer id,
			String name,
			String status,
			String species,
			String type,
			String gender,
			LocationEntity origin,
			LocationEntity location,
			String image,
			String url) {
		this.id = id;
		this.name = name;
		this.status = status;
		this.species = species;
		this.type = type;
		this.gender = gender;
		this.origin = origin;
		this.location = location;
		this.image = image;
		this.url = url;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getStatus() {
		return status;
	}

	public String getSpecies() {
		return species;
	}

	public String getType() {
		return type;
	}

	public String getGender() {
		return gender;
	}

	public LocationEntity getOrigin() {
		return origin;
	}

	public LocationEntity getLocation() {
		return location;
	}

	public String getImage() {
		return image;
	}

	public String getUrl() {
		return url;
	}

	public Set<EpisodeEntity> getEpisodes() {
		return episodes;
	}

	public Character toSummary() {
		return toCharacter(List.of());
	}

	public Character toDomain() {
		List<Integer> episodeIds = episodes.stream()
				.map(EpisodeEntity::getId)
				.sorted()
				.toList();
		return toCharacter(episodeIds);
	}

	private Character toCharacter(List<Integer> episodeIds) {
		return new Character(
				id,
				name,
				status,
				species,
				type,
				gender,
				origin == null ? null : origin.getId(),
				origin == null ? null : origin.getName(),
				location == null ? null : location.getId(),
				location == null ? null : location.getName(),
				image,
				url,
				episodeIds);
	}

}
