package cl.tenpo.mvcrestapi.controller.config;

import cl.tenpo.mvcrestapi.repository.entity.ApiLogEntity;
import cl.tenpo.mvcrestapi.service.ApiLogService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingFilter implements Filter {

  private final ApiLogService apiLogService;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {

    if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
      chain.doFilter(request, response);
      return;
    }

    Instant startTime = Instant.now();

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    String endpoint = httpRequest.getRequestURI();
    String method = httpRequest.getMethod();

    ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(httpRequest);
    ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(httpResponse);

    chain.doFilter(wrappedRequest, wrappedResponse);

    Instant endTime = Instant.now();

    String requestBody =
      new String(wrappedRequest.getContentAsByteArray(), wrappedRequest.getCharacterEncoding());

    String responseBody =
      new String(wrappedResponse.getContentAsByteArray(), wrappedResponse.getCharacterEncoding());

    log.info("Endpoint: {}", endpoint);
    log.info("Método HTTP: {}", method);
    log.info("Fecha y hora de la llamada: {}", startTime);
    log.info("Fecha y hora de respuesta: {}", endTime);
    log.info("Request Body: {}", requestBody);
    log.info("Response Body: {}", responseBody);

    // save to database async
    ApiLogEntity apiLogEntity = new ApiLogEntity();
    apiLogEntity.setEndpoint(endpoint);
    apiLogEntity.setHttpMethod(method);
    apiLogEntity.setCallDateTime(startTime);
    apiLogEntity.setQueryString("");
    apiLogEntity.setRequestBody(requestBody);
    apiLogEntity.setResponseBody(responseBody);

    this.apiLogService.save(apiLogEntity);

    wrappedResponse.copyBodyToResponse();
  }
}