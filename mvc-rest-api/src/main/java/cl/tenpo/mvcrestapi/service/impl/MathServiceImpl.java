package cl.tenpo.mvcrestapi.service.impl;

import cl.tenpo.mvcrestapi.service.MathService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class MathServiceImpl implements MathService {

  @Override
  public BigDecimal add(List<BigDecimal> numbers) {
    BigDecimal percentage = BigDecimal.valueOf(0.7);
    BigDecimal sum = numbers.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    return percentage.multiply(sum);
  }
}
