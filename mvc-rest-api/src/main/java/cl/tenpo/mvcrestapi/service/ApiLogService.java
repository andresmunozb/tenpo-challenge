package cl.tenpo.mvcrestapi.service;

import cl.tenpo.mvcrestapi.core.domain.ApiLog;
import org.springframework.data.domain.Page;

public interface ApiLogService {
  ApiLog save(ApiLog apiLog);

  Page<ApiLog> findAll(int page, int size);
}
