package cl.tenpo.tenpochallenge.service;

import java.math.BigDecimal;
import java.util.List;

public interface MathService {
  BigDecimal add(List<BigDecimal> numbers);
}
