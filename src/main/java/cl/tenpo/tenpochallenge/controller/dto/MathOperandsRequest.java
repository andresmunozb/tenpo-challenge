package cl.tenpo.tenpochallenge.controller.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MathOperandsRequest {
  @NotEmpty
  @JsonDeserialize(contentUsing = NumberDeserializers.BigDecimalDeserializer.class)
  private List<@NotNull BigDecimal> numbers;
}
