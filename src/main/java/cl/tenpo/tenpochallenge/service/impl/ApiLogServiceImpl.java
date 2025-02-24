package cl.tenpo.tenpochallenge.service.impl;

import cl.tenpo.tenpochallenge.core.domain.ApiLog;
import cl.tenpo.tenpochallenge.repository.ApiLogRepository;
import cl.tenpo.tenpochallenge.service.ApiLogService;
import cl.tenpo.tenpochallenge.service.mapper.ApiLogServiceMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

  public Page<ApiLog> findAll(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return apiLogRepository.findAll(pageable).map(apiLogServiceMapper::toApiLog);
  }
}
