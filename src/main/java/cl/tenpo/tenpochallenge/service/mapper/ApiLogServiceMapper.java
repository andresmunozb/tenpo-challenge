package cl.tenpo.tenpochallenge.service.mapper;

import cl.tenpo.tenpochallenge.core.domain.ApiLog;
import cl.tenpo.tenpochallenge.repository.entity.ApiLogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApiLogServiceMapper {
  ApiLogEntity toApiLogEntity(ApiLog apiLog);

  ApiLog toApiLog(ApiLogEntity apiLogEntity);
}
