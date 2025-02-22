package cl.tenpo.mvcrestapi.service.impl;

import cl.tenpo.mvcrestapi.core.domain.ApiLog;
import cl.tenpo.mvcrestapi.repository.ApiLogRepository;
import cl.tenpo.mvcrestapi.service.ApiLogService;
import cl.tenpo.mvcrestapi.service.mapper.ApiLogServiceMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiLogServiceImpl implements ApiLogService {

  private final ApiLogRepository apiLogRepository;
  private final ApiLogServiceMapper apiLogServiceMapper;

  @Override
  @Transactional
  public ApiLog save(ApiLog apiLog) {
    return apiLogServiceMapper.toApiLog(
      apiLogRepository.save(apiLogServiceMapper.toApiLogEntity(apiLog)));
  }
}
