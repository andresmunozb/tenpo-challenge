package cl.tenpo.tenpochallenge.service.impl;

import cl.tenpo.tenpochallenge.service.ExternalPercentageService;
import cl.tenpo.tenpochallenge.service.exception.UnavailableExternalPercentageServiceException;
import cl.tenpo.tenpochallenge.utils.SleepUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class ExternalPercentageServiceImpl implements ExternalPercentageService {

  @Override
  @Cacheable("percentage")
  @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 500, multiplier = 1.5))
  public BigDecimal getPercentage() {
    SleepUtil.sleep(1000);

    if (Math.random() > 0.4) {
      throw new UnavailableExternalPercentageServiceException();
    }

    return BigDecimal.valueOf(0.1);
  }
}
