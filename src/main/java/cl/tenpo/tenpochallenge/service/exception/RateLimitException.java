package cl.tenpo.tenpochallenge.service.exception;

public class RateLimitException extends RuntimeException {
  public RateLimitException() {
    super("rate limit exception");
  }
}
