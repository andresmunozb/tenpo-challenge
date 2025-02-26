package cl.tenpo.tenpochallenge.service;

public interface CacheService {
  void expire();

  Long incrementRateLimitCountTo(String ip);
}
