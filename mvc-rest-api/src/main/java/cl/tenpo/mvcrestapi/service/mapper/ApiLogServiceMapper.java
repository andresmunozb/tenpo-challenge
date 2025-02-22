package cl.tenpo.mvcrestapi.service.mapper;

import cl.tenpo.mvcrestapi.core.domain.ApiLog;
import cl.tenpo.mvcrestapi.repository.entity.ApiLogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApiLogServiceMapper {
  ApiLogEntity toApiLogEntity(ApiLog apiLog);
  ApiLog toApiLog(ApiLogEntity apiLogEntity);
}
