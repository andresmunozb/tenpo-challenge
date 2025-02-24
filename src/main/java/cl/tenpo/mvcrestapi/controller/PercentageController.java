package cl.tenpo.mvcrestapi.controller;

import cl.tenpo.mvcrestapi.core.common.Response;
import cl.tenpo.mvcrestapi.service.ExternalPercentageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/percentage")
@RequiredArgsConstructor
public class PercentageController {

  private final ExternalPercentageService externalPercentageService;

  @GetMapping
  public ResponseEntity<Response<BigDecimal>> getPercentage() {
    return ResponseEntity.ok(Response.of(externalPercentageService.getPercentage()));
  }

  @PutMapping
  public ResponseEntity<Response<BigDecimal>> updatePercentage() {
    externalPercentageService.updatePercentage();
    return ResponseEntity.ok(Response.empty());
  }
}
