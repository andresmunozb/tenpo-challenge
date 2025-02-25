package cl.tenpo.tenpochallenge.controller;

import cl.tenpo.tenpochallenge.core.common.Response;
import cl.tenpo.tenpochallenge.service.CacheService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/cache/expire")
@RequiredArgsConstructor
public class CacheController {

  private final CacheService cacheService;

  @PutMapping
  @Operation(summary = "Expirar todas las entradas de redis")
  public ResponseEntity<Response<BigDecimal>> expire() {
    cacheService.expire();
    return ResponseEntity.ok(Response.empty());
  }
}
