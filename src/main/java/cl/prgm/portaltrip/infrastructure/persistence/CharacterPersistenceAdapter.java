package cl.prgm.portaltrip.infrastructure.persistence;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import cl.prgm.portaltrip.application.port.out.CharacterRepository;
import cl.prgm.portaltrip.domain.model.Character;
import cl.prgm.portaltrip.infrastructure.persistence.repository.CharacterJpaRepository;

@Component
public class CharacterPersistenceAdapter implements CharacterRepository {

	private final CharacterJpaRepository characterJpaRepository;

	public CharacterPersistenceAdapter(CharacterJpaRepository characterJpaRepository) {
		this.characterJpaRepository = characterJpaRepository;
	}

	@Override
	public List<Character> findAllSummaries() {
		return characterJpaRepository.findAllSummaries().stream()
				.map(CharacterEntity::toSummary)
				.toList();
	}

	@Override
	public Optional<Character> findDetailedById(Integer id) {
		return characterJpaRepository.findDetailedById(id)
				.map(CharacterEntity::toDomain);
	}

	@Override
	public List<Character> findAllByIds(Collection<Integer> ids) {
		return characterJpaRepository.findAllById(ids).stream()
				.map(CharacterEntity::toSummary)
				.toList();
	}

}
