package cl.prgm.portaltrip.application.port.out;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import cl.prgm.portaltrip.domain.model.Character;

public interface CharacterRepository {

	List<Character> findAllSummaries();

	Optional<Character> findDetailedById(Integer id);

	List<Character> findAllByIds(Collection<Integer> ids);

}
