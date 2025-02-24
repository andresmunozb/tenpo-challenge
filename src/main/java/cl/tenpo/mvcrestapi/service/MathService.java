package cl.tenpo.mvcrestapi.service;

import java.math.BigDecimal;
import java.util.List;

public interface MathService {
  BigDecimal add(List<BigDecimal> numbers);
}
