package cl.tenpo.mvcrestapi.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "api_logs")
@Getter
@Setter
public class ApiLogEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "http_method", nullable = false, length = 10)
  private String httpMethod;

  @Column(name = "endpoint", nullable = false)
  private String endpoint;

  @Lob
  @Column(name = "query_params", columnDefinition = "TEXT")
  private String queryParams;

  @Lob
  @Column(name = "request_body", columnDefinition = "TEXT")
  private String requestBody;

  @Lob
  @Column(name = "response_body", columnDefinition = "TEXT")
  private String responseBody;

  @Column(name = "start_date_time", nullable = false)
  private Instant startDateTime;

  @Column(name = "end_date_time", nullable = false)
  private Instant endDateTime;
}