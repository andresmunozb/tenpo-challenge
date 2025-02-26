package cl.tenpo.tenpochallenge.unit.service;

import cl.tenpo.tenpochallenge.service.exception.RateLimitException;
import cl.tenpo.tenpochallenge.service.impl.RedisCacheServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;

@ExtendWith(MockitoExtension.class)
class RedisCacheServiceImplUnitTest {

  @Mock
  private RedisTemplate<String, Object> redisTemplateObject;

  @InjectMocks
  private RedisCacheServiceImpl redisCacheService;

  @Test
  void shouldThrowRateLimitException() {

    Mockito.when(redisTemplateObject.execute(Mockito.any(), Mockito.any(), Mockito.any()))
      .thenReturn(4L);

    Assertions.assertThrows(RateLimitException.class, () ->
      redisCacheService.incrementRateLimitCountTo("localhost"));
  }

  @Test
  void shouldPassWithoutExceptions() {

    Long expected = 3L;

    Mockito.when(redisTemplateObject.execute(Mockito.any(), Mockito.any(), Mockito.any()))
      .thenReturn(3L);

    Long count = redisCacheService.incrementRateLimitCountTo("localhost");

    Assertions.assertEquals(expected, count);
  }
}
