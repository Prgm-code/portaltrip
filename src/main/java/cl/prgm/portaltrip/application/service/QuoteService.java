package cl.prgm.portaltrip.application.service;

import cl.prgm.portaltrip.domain.model.Quote;

public interface QuoteService {

	Quote quote(QuoteQuery query);

}
