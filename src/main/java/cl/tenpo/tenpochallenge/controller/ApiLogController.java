package cl.tenpo.tenpochallenge.controller;

import cl.tenpo.tenpochallenge.core.common.Response;
import cl.tenpo.tenpochallenge.core.domain.ApiLog;
import cl.tenpo.tenpochallenge.service.ApiLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/api-logs")
@RequiredArgsConstructor
public class ApiLogController {

  private final ApiLogService apiLogService;

  @GetMapping()
  public ResponseEntity<Response<Page<ApiLog>>> findAll(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
    return ResponseEntity.ok(Response.of(apiLogService.findAll(page, size)));
  }
}
