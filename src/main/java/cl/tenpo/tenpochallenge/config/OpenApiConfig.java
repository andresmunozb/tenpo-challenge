package cl.tenpo.tenpochallenge.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
  info = @Info(
    title = "Tenpo Challenge",
    version = "1.0.0",
    description = "Documentation"
  )
)
@Configuration
public class OpenApiConfig {
}
