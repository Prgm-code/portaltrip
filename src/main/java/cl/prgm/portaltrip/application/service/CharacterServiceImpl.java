package cl.prgm.portaltrip.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.Character;
import cl.prgm.portaltrip.infrastructure.persistence.CharacterEntity;
import cl.prgm.portaltrip.infrastructure.persistence.repository.CharacterJpaRepository;

@Service
@Transactional(readOnly = true)
public class CharacterServiceImpl implements CharacterService {

	private final CharacterJpaRepository characterJpaRepository;

	public CharacterServiceImpl(CharacterJpaRepository characterJpaRepository) {
		this.characterJpaRepository = characterJpaRepository;
	}

	@Override
	public List<Character> findAll() {
		return characterJpaRepository.findAllSummaries().stream()
				.map(CharacterEntity::toSummary)
				.toList();
	}

	@Override
	public Character findById(Integer id) {
		return characterJpaRepository.findDetailedById(id)
				.map(CharacterEntity::toDomain)
				.orElseThrow(() -> new ResourceNotFoundException("Character", id));
	}

}
