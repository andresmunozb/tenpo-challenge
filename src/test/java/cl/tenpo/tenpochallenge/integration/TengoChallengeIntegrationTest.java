package cl.tenpo.tenpochallenge.integration;

import cl.tenpo.tenpochallenge.controller.dto.MathBinaryOperandsRequest;
import cl.tenpo.tenpochallenge.core.common.MinimalPage;
import cl.tenpo.tenpochallenge.core.common.Response;
import cl.tenpo.tenpochallenge.core.domain.ApiLog;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TengoChallengeIntegrationTest {

  @LocalServerPort
  private int port;

  private TestRestTemplate restTemplate;

  @BeforeEach
  void setUp() {
    restTemplate = new TestRestTemplate();
  }

  @Test
  void shouldReturnCorrectSumWithTwoOperandsWhenPostingToBinarySumEndpoint() {

    BigDecimal expected = BigDecimal.valueOf(11).setScale(10, RoundingMode.HALF_UP);

    String url = "http://localhost:" + port + "/api/v1/math/binary-sum";

    HttpEntity<MathBinaryOperandsRequest> requestEntity = new HttpEntity<>(
      MathBinaryOperandsRequest.builder().num1(BigDecimal.valueOf(5)).num2(BigDecimal.valueOf
        (5)).build());

    ResponseEntity<Response<BigDecimal>> response =
      restTemplate.exchange(
        url,
        HttpMethod.POST,
        requestEntity,
        new ParameterizedTypeReference<Response<BigDecimal>>() {
        });


    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());

    BigDecimal result = response.getBody().getData().setScale(10, RoundingMode.HALF_UP);
    Assertions.assertEquals(expected, result);
  }

  @Test
  void shouldReturnApiLogsPageWhenRequested() {

    String url = "http://localhost:" + port + "/api/v1/api-logs";

    ResponseEntity<Response<MinimalPage<ApiLog>>> response =
      restTemplate.exchange(
        url,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<Response<MinimalPage<ApiLog>>>() {
        });

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
  }
}
