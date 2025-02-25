package cl.tenpo.tenpochallenge.service.exception;

import cl.tenpo.tenpochallenge.core.exception.ServiceUnavailableException;

public class UnavailableExternalPercentageServiceException extends ServiceUnavailableException {
  public UnavailableExternalPercentageServiceException(String message) {
    super(message);
  }
}
