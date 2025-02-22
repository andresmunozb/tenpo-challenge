package cl.tenpo.mvcrestapi.event;

import cl.tenpo.mvcrestapi.core.domain.ApiLog;
import cl.tenpo.mvcrestapi.service.ApiLogService;
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
