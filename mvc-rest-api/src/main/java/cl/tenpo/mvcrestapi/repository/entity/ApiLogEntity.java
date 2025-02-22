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

  @Column(name = "call_date_time", nullable = false)
  private Instant callDateTime;

  @Column(name = "http_method", nullable = false, length = 10)
  private String httpMethod;

  @Column(name = "endpoint", nullable = false, length = 255)
  private String endpoint;

  @Column(name = "query_string", length = 500)
  private String queryString;

  @Lob
  @Column(name = "request_body", columnDefinition = "TEXT")
  private String requestBody;

  @Lob
  @Column(name = "response_body", columnDefinition = "TEXT")
  private String responseBody;
}