package cl.tenpo.tenpochallenge.controller.filter;

import java.util.List;

public class FilterUtils {

  private FilterUtils() {
  }

  private static final List<String> EXCLUDED_PATHS =
    List.of(".*/swagger-ui.*", ".*/v3/api-docs.*", ".*/api-logs.*", ".*/api/v1/cache/expire.*");

  public static boolean shouldIgnoreEndpoint(String endpoint) {
    return EXCLUDED_PATHS.stream().anyMatch(endpoint::matches);
  }
}
