package cl.tenpo.mvcrestapi.repository;

import cl.tenpo.mvcrestapi.repository.entity.ApiLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiLogRepository extends JpaRepository<ApiLogEntity, Long> {
}
