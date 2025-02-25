package cl.tenpo.tenpochallenge.controller.filter;

import cl.tenpo.tenpochallenge.core.common.Notification;
import cl.tenpo.tenpochallenge.core.common.Response;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(2)
public class RedisRateLimitFilter extends OncePerRequestFilter {

  private final RedisTemplate<String, Long> redisTemplate;
  private final ObjectMapper objectMapper;

  private static final String LUA_SCRIPT =
    "local current = redis.call('incr', KEYS[1])\n" +
      "if current == 1 then\n" +
      "    redis.call('expire', KEYS[1], ARGV[1])\n" +
      "end\n" +
      "return current";

  private final RedisScript<Long> rateLimitScript =
    new DefaultRedisScript<>(LUA_SCRIPT, Long.class);

  @Override
  public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                               FilterChain chain)
    throws ServletException, IOException {

    String ip = request.getRemoteAddr();
    String key = "rate-limit:" + ip;

    try {
      Long count = redisTemplate.execute(rateLimitScript, List.of(key), 60);
      if (count > 3) {
        log.warn("too many request for {}", ip);
        sendErrorResponse(response, 429, "too many requests");
        return;
      }
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
