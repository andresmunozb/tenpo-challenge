package cl.tenpo.tenpochallenge.controller.filter;

import cl.tenpo.tenpochallenge.core.common.Notification;
import cl.tenpo.tenpochallenge.core.common.Response;
import cl.tenpo.tenpochallenge.service.CacheService;
import cl.tenpo.tenpochallenge.service.exception.RateLimitException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(2)
public class RateLimitFilter extends OncePerRequestFilter {

  private final CacheService cacheService;
  private final ObjectMapper objectMapper;

  @Override
  public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                               FilterChain chain)
    throws ServletException, IOException {

    String endpoint = request.getRequestURI();

    if (FilterUtils.shouldIgnoreEndpoint(endpoint)) {
      chain.doFilter(request, response);
      return;
    }

    String ip = request.getRemoteAddr();

    try {
      cacheService.incrementRateLimitCountTo(ip);
    } catch (RateLimitException e) {
      sendErrorResponse(response, 429, "too many requests - only 3 requests per minute are " +
        "allowed");
      return;
    } catch (RedisConnectionFailureException e) {
      log.error("redis connection error {}", e.getMessage());
      sendErrorResponse(response, 503, "service temporarily unavailable");
      return;
    } catch (RedisSystemException e) {
      log.error("redis operation error {}", e.getMessage());
      sendErrorResponse(response, 500, "error processing request");
      return;
    } catch (Exception e) {
      log.error("rate limit filter error");
      sendErrorResponse(response, 500, "internal server error");
      return;
    }
    chain.doFilter(request, response);
  }

  private void sendErrorResponse(HttpServletResponse response, int statusCode, String message)
    throws IOException {
    response.setStatus(statusCode);
    response.setContentType("application/json");
    objectMapper.writeValue(
      response.getWriter(),
      Response.of(List.of(Notification.valueOf(message)))
    );
  }
}
