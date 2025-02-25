package cl.tenpo.tenpochallenge.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
@Configuration
public class RedisConfig {

  @Bean
  public RedisTemplate<String, Long> redisTemplateLong(RedisConnectionFactory connectionFactory) {
    RedisTemplate<String, Long> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(connectionFactory);

    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplateObject(RedisConnectionFactory connectionFactory) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(connectionFactory);

    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }

  @Bean
  public CommandLineRunner testRedisConnection(RedisTemplate<String, Long> redisTemplate) {
    return args -> {
      RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();

      if (connectionFactory instanceof LettuceConnectionFactory lettuceFactory) {
        log.info("Connecting to Redis on: {}:{}", lettuceFactory.getHostName(),
          lettuceFactory.getPort());
      }

      try {
        assert connectionFactory != null;
        RedisConnection connection = connectionFactory.getConnection();
        String response = connection.ping();
        log.info("Redis ping response: {}", response);
        connection.close();
      } catch (Exception e) {
        log.info("Error connecting to Redis: {}", e.getMessage());
      }
    };
  }
}
