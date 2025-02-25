package cl.tenpo.tenpochallenge.service.impl;

import cl.tenpo.tenpochallenge.service.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements CacheService {

  private final RedisTemplate<String, Object> redisTemplate;

  @Override
  public void expire() {
    Set<String> keys = redisTemplate.keys("*");
    for (String key : keys) {
      redisTemplate.expire(key, 0, TimeUnit.SECONDS);
    }
  }
}