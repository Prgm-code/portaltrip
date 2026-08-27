package cl.prgm.portaltrip.infrastructure.persistence;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import cl.prgm.portaltrip.domain.model.Episode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "episodes")
public class EpisodeEntity {

	@Id
	private Integer id;

	@Column(nullable = false)
	private String name;

	@Column(name = "air_date", nullable = false)
	private String airDate;

	@Column(name = "episode", nullable = false)
	private String code;

	@Column(nullable = false)
	private String url;

	@ManyToMany(mappedBy = "episodes")
	private Set<CharacterEntity> characters = new LinkedHashSet<>();

	protected EpisodeEntity() {
	}

	public EpisodeEntity(Integer id, String name, String airDate, String code, String url) {
		this.id = id;
		this.name = name;
		this.airDate = airDate;
		this.code = code;
		this.url = url;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAirDate() {
		return airDate;
	}

	public String getCode() {
		return code;
	}

	public String getUrl() {
		return url;
	}

	public Set<CharacterEntity> getCharacters() {
		return characters;
	}

	public Episode toSummary() {
		return new Episode(id, name, airDate, code, url, List.of());
	}

	public Episode toDomain() {
		List<Integer> characterIds = characters.stream()
				.map(CharacterEntity::getId)
				.sorted()
				.toList();
		return new Episode(id, name, airDate, code, url, characterIds);
	}

}
