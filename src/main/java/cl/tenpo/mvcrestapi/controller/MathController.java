package cl.tenpo.mvcrestapi.controller;

import cl.tenpo.mvcrestapi.controller.dto.MathBinaryOperandsRequest;
import cl.tenpo.mvcrestapi.controller.dto.MathOperandsRequest;
import cl.tenpo.mvcrestapi.core.common.Response;
import cl.tenpo.mvcrestapi.service.MathService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/math")
@RequiredArgsConstructor
public class MathController {

  private final MathService mathService;

  @PostMapping("/sum")
  public ResponseEntity<Response<BigDecimal>> sum(@Valid @RequestBody MathOperandsRequest request) {
    return ResponseEntity.ok(Response.of(mathService.add(request.getNumbers())));
  }

  @PostMapping("/binary-sum")
  public ResponseEntity<Response<BigDecimal>> binarySum(
    @Valid @RequestBody MathBinaryOperandsRequest request) {
    return ResponseEntity.ok(
      Response.of(mathService.add(List.of(request.getNum1(), request.getNum2()))));
  }
}
