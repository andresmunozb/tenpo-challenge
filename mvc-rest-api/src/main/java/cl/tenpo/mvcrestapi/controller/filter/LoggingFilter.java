package cl.tenpo.mvcrestapi.controller.filter;

import cl.tenpo.mvcrestapi.core.domain.ApiLog;
import cl.tenpo.mvcrestapi.event.ApiLogPublisher;
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
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingFilter implements Filter {

  private final ApiLogPublisher apiLogPublisher;

  private static final List<String> EXCLUDED_PATHS = List.of(".*/swagger-ui.*", ".*/v3/api-docs.*");

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
    String queryParams = httpRequest.getQueryString();

    if (shouldIgnoreEndpoint(endpoint)) {
      chain.doFilter(request, response);
      return;
    }

    ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(httpRequest);
    ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(httpResponse);

    chain.doFilter(wrappedRequest, wrappedResponse);

    Instant endTime = Instant.now();

    String requestBody =
      new String(wrappedRequest.getContentAsByteArray(), wrappedRequest.getCharacterEncoding());

    String responseBody =
      new String(wrappedResponse.getContentAsByteArray(), wrappedResponse.getCharacterEncoding());

    ApiLog apiLog = ApiLog.builder()
      .endpoint(endpoint)
      .httpMethod(method)
      .queryParams(queryParams)
      .requestBody(requestBody)
      .responseBody(responseBody)
      .startDateTime(startTime)
      .endDateTime(endTime)
      .build();

    this.apiLogPublisher.publishEvent(apiLog);

    wrappedResponse.copyBodyToResponse();
  }

  private boolean shouldIgnoreEndpoint(String endpoint) {
    return EXCLUDED_PATHS.stream().anyMatch(endpoint::matches);
  }
}