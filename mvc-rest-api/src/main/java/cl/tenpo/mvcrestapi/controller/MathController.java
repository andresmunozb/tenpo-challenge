package cl.tenpo.mvcrestapi.controller;

import cl.tenpo.mvcrestapi.common.Response;
import cl.tenpo.mvcrestapi.controller.dto.MathOperandsRequest;
import cl.tenpo.mvcrestapi.service.MathService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/math")
@RequiredArgsConstructor
public class MathController {

  private final MathService mathService;

  @PostMapping("/sum")
  public ResponseEntity<Response<BigDecimal>> sum(@Valid @RequestBody MathOperandsRequest request) {
    return ResponseEntity.ok(Response.of(mathService.add(request.getNumbers())));
  }
}
