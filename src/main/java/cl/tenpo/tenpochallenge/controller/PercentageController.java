package cl.tenpo.tenpochallenge.controller;

import cl.tenpo.tenpochallenge.core.common.Response;
import cl.tenpo.tenpochallenge.service.ExternalPercentageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/percentage")
@RequiredArgsConstructor
public class PercentageController {

  private final ExternalPercentageService externalPercentageService;

  @GetMapping
  @Operation(summary = "Obtener porcentaje desde servicio externo (mock)", description = "Este " +
    "endpoint demora 1 segundo para simular un llamado a un servicio externo. Además se simulan " +
    "errores con una probabilidad del 60% con la finalidad de gatillar reintentos y fallos")
  public ResponseEntity<Response<BigDecimal>> getPercentage() {
    return ResponseEntity.ok(Response.of(externalPercentageService.getPercentage()));
  }
}
