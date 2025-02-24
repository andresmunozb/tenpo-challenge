package cl.tenpo.mvcrestapi.service;

import java.math.BigDecimal;

public interface ExternalPercentageService {
  BigDecimal getPercentage();

  void updatePercentage();
}
