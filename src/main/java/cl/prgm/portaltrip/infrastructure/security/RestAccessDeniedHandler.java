package cl.prgm.portaltrip.infrastructure.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import tools.jackson.databind.json.JsonMapper;

import cl.prgm.portaltrip.infrastructure.web.dto.ApiResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

	private final JsonMapper jsonMapper;

	public RestAccessDeniedHandler(JsonMapper jsonMapper) {
		this.jsonMapper = jsonMapper;
	}

	@Override
	public void handle(
			HttpServletRequest request,
			HttpServletResponse response,
			AccessDeniedException exception) throws IOException, ServletException {
		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		jsonMapper.writeValue(
				response.getOutputStream(),
				ApiResponseDto.error(HttpStatus.FORBIDDEN, "Access denied"));
	}

}
