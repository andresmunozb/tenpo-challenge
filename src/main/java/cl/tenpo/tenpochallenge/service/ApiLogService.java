package cl.tenpo.tenpochallenge.service;

import cl.tenpo.tenpochallenge.core.common.MinimalPage;
import cl.tenpo.tenpochallenge.core.domain.ApiLog;

public interface ApiLogService {
  ApiLog save(ApiLog apiLog);

  MinimalPage<ApiLog> findAll(int page, int size);
}
