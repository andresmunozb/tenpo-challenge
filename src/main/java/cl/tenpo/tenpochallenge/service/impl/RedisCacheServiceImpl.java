package cl.tenpo.tenpochallenge.service.impl;

import cl.tenpo.tenpochallenge.service.CacheService;
import cl.tenpo.tenpochallenge.service.exception.RateLimitException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisCacheServiceImpl implements CacheService {

  private static final String LUA_SCRIPT =
    "local current = redis.call('incr', KEYS[1])\n" +
      "if current == 1 then\n" +
      "    redis.call('expire', KEYS[1], ARGV[1])\n" +
      "end\n" +
      "return current";

  private final RedisScript<Long> rateLimitScript =
    new DefaultRedisScript<>(LUA_SCRIPT, Long.class);

  private final RedisTemplate<String, Object> redisTemplateObject;

  @Override
  public void expire() {
    Set<String> keys = redisTemplateObject.keys("*");
    for (String key : keys) {
      redisTemplateObject.expire(key, 0, TimeUnit.SECONDS);
    }
  }

  public Long incrementRateLimitCountTo(String ip) {
    String key = "rate-limit:" + ip;
    Long count = redisTemplateObject.execute(rateLimitScript, List.of(key), 60);
    if (count > 3) {
      log.warn("too many request for {}", ip);
      throw new RateLimitException();
    }
    return count;
  }
}