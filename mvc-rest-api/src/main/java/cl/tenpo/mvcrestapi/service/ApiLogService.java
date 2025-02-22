package cl.tenpo.mvcrestapi.service;

import cl.tenpo.mvcrestapi.repository.entity.ApiLogEntity;

public interface ApiLogService {
  ApiLogEntity save(ApiLogEntity apiLogEntity);
}
