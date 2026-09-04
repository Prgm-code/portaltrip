package cl.prgm.portaltrip.application.service;

import java.util.UUID;

import cl.prgm.portaltrip.domain.model.PortalStipendResult;

public interface PortalStipendService {

	PortalStipendResult claim(UUID userId, double movement, long activeMs);

}
