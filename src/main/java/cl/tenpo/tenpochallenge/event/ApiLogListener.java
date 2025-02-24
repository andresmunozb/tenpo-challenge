package cl.tenpo.tenpochallenge.event;

import cl.tenpo.tenpochallenge.core.domain.ApiLog;
import cl.tenpo.tenpochallenge.service.ApiLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiLogListener {

  private final ApiLogService apiLogService;

  @EventListener
  @Async
  public void handleEvent(ApiLog event) {
    apiLogService.save(event);
  }
}
