package cl.tenpo.tenpochallenge.controller.filter;

import cl.tenpo.tenpochallenge.core.domain.ApiLog;
import cl.tenpo.tenpochallenge.event.ApiLogPublisher;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.time.Instant;

@Slf4j
@Component
@Order(1)
@RequiredArgsConstructor
public class LoggingFilter extends OncePerRequestFilter {

  private final ApiLogPublisher apiLogPublisher;


  @Override
  public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                               FilterChain chain)
    throws IOException, ServletException {

    Instant startTime = Instant.now();

    String endpoint = request.getRequestURI();
    String method = request.getMethod();
    String queryParams = request.getQueryString();

    if (FilterUtils.shouldIgnoreEndpoint(endpoint)) {
      chain.doFilter(request, response);
      return;
    }

    ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
    ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

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
}