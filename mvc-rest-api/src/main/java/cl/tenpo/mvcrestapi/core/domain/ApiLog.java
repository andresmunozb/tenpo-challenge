package cl.tenpo.mvcrestapi.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
public class ApiLog {
  private Long id;
  private String httpMethod;
  private String endpoint;
  private String queryParams;
  private String requestBody;
  private String responseBody;
  private Instant startDateTime;
  private Instant endDateTime;
}