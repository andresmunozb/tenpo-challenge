package cl.tenpo.tenpochallenge.service;

import cl.tenpo.tenpochallenge.core.domain.ApiLog;
import org.springframework.data.domain.Page;

public interface ApiLogService {
  ApiLog save(ApiLog apiLog);

  Page<ApiLog> findAll(int page, int size);
}
