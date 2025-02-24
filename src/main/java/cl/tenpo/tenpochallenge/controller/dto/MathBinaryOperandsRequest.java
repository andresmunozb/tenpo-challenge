package cl.tenpo.tenpochallenge.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MathBinaryOperandsRequest {

  @NotNull
  private BigDecimal num1;

  @NotNull
  private BigDecimal num2;
}
