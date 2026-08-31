package cl.prgm.portaltrip.application.port.in;

import java.util.List;

import cl.prgm.portaltrip.domain.model.Character;

public interface CharacterService {

	List<Character> findAll();

	Character findById(Integer id);

}
