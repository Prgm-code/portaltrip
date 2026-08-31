package cl.prgm.portaltrip.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.prgm.portaltrip.application.port.in.CharacterService;
import cl.prgm.portaltrip.application.port.out.CharacterRepository;
import cl.prgm.portaltrip.domain.exception.ResourceNotFoundException;
import cl.prgm.portaltrip.domain.model.Character;

@Service
@Transactional(readOnly = true)
public class CharacterServiceImpl implements CharacterService {

	private final CharacterRepository characterRepository;

	public CharacterServiceImpl(CharacterRepository characterRepository) {
		this.characterRepository = characterRepository;
	}

	@Override
	public List<Character> findAll() {
		return characterRepository.findAllSummaries();
	}

	@Override
	public Character findById(Integer id) {
		return characterRepository.findDetailedById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Character", id));
	}

}
