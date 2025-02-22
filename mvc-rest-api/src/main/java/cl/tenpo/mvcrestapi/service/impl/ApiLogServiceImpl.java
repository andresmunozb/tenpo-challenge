package cl.tenpo.mvcrestapi.service.impl;

import cl.tenpo.mvcrestapi.repository.ApiLogRepository;
import cl.tenpo.mvcrestapi.repository.entity.ApiLogEntity;
import cl.tenpo.mvcrestapi.service.ApiLogService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiLogServiceImpl implements ApiLogService {

  private final ApiLogRepository apiLogRepository;

  @Override
  @Transactional
  public ApiLogEntity save(ApiLogEntity apiLogEntity) {
    return apiLogRepository.save(apiLogEntity);
  }
}
