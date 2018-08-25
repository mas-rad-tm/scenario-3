package ch.globaz.tmmas.personnesservice.application.api.web.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

/**
 * Filtre pour toutes les requêtes. Permet d'appliquer l'id de correlation
 * pour toutes les requêtes entrantes.
 */
@Component
class MDCRequestFilter implements Filter{

	private final static Logger LOGGER = LoggerFactory.getLogger(MDCRequestFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

		LOGGER.info("Do filter MDC....");

		try{

			MDC.put("correlationId", UUID.randomUUID().toString());
			filterChain.doFilter(servletRequest,servletResponse);
			LOGGER.info("...filter chain do filter...");

		}
		finally{

			MDC.clear();
			LOGGER.info("...mdc clear...");
		}
	}

	@Override
	public void destroy() {}

}
