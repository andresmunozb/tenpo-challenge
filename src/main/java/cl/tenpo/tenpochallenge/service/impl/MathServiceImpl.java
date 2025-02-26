package cl.tenpo.tenpochallenge.service.impl;

import cl.tenpo.tenpochallenge.service.ExternalPercentageService;
import cl.tenpo.tenpochallenge.service.MathService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MathServiceImpl implements MathService {

  private final ExternalPercentageService externalPercentageService;

  @Override
  public BigDecimal add(List<BigDecimal> numbers) {
    BigDecimal percentage = externalPercentageService.getPercentage();
    BigDecimal sum = numbers.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    return sum.multiply(BigDecimal.ONE.add(percentage));
  }
}
