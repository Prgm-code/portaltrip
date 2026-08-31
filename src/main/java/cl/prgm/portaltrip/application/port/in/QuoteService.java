package cl.prgm.portaltrip.application.port.in;

import cl.prgm.portaltrip.domain.model.Quote;

public interface QuoteService {

	Quote quote(QuoteQuery query);

}
