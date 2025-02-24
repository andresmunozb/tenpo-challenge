package cl.tenpo.mvcrestapi.event;

import cl.tenpo.mvcrestapi.core.domain.ApiLog;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiLogPublisher {
  private final ApplicationEventPublisher eventPublisher;

  public void publishEvent(ApiLog event) {
    eventPublisher.publishEvent(event);
  }
}
