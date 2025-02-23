package cl.tenpo.mvcrestapi.controller.filter;

import cl.tenpo.mvcrestapi.core.common.Notification;
import cl.tenpo.mvcrestapi.core.common.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Order(2)
public class RedisRateLimitFilter extends OncePerRequestFilter {

  private final RedisTemplate<String, Long> redisTemplate;
  private final ObjectMapper objectMapper;

  @Override
  public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                               FilterChain chain)
    throws ServletException, IOException {

    String ip = request.getRemoteAddr();
    String key = "rate-limit:" + ip;

    // incrementar contador de manera atómica
    Long count = redisTemplate.opsForValue().increment(key, 1);


    if (count == null) {
      response.setStatus(500);
      response.setContentType("application/json");
      objectMapper.writeValue(
        response.getWriter(),
        Response.of(List.of(Notification.valueOf("error updating rate limit key in redis")))
      );
      return;
    }

    if (count == 1L) {
      // no atómico pero idempotente
      redisTemplate.expire(key, 1, TimeUnit.MINUTES);
    }

    if (count > 3) {
      response.setStatus(429);
      response.setContentType("application/json");
      objectMapper.writeValue(
        response.getWriter(),
        Response.of(List.of(Notification.valueOf("too many requests")))
      );
      return;
    }

    chain.doFilter(request, response);
  }
}
