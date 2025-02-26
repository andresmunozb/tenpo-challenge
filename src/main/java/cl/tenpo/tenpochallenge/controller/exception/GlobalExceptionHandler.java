package cl.tenpo.tenpochallenge.controller.exception;

import cl.tenpo.tenpochallenge.core.common.Notification;
import cl.tenpo.tenpochallenge.core.common.Response;
import cl.tenpo.tenpochallenge.core.exception.ServiceUnavailableException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  private static final String ERROR_TEMPLATE = "{}: {}";
  private static final String METHOD_ARGUMENT_NOT_VALID_ERROR_MESSAGE = "method argument not valid";
  private static final String CONSTRAIN_VIOLATION_ERROR_MESSAGE = "constrain violation";
  private static final String JSON_ERROR_MESSAGE = "malformed json or invalid data type";
  private static final String INTERNAL_SERVER_ERROR_MESSAGE = "internal server error";
  private static final String SERVICE_UNAVAILABLE_ERROR_MESSAGE = "service unavailable";
  private static final String NOT_FOUND_ERROR_MESSAGE = "the requested resource was not found";


  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Response<?>> handleValidationExceptions(
    MethodArgumentNotValidException ex) {
    log.error(ERROR_TEMPLATE, METHOD_ARGUMENT_NOT_VALID_ERROR_MESSAGE, ex.getMessage());
    return new ResponseEntity<>(Response.of(null, Notification.valueOf(ex)),
      HttpStatus.BAD_REQUEST);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Response<?>> handleConstraintViolationException(
    ConstraintViolationException ex) {
    log.error(ERROR_TEMPLATE, CONSTRAIN_VIOLATION_ERROR_MESSAGE, ex.getMessage());
    return new ResponseEntity<>(Response.of(null, Notification.valueOf(ex)),
      HttpStatus.BAD_REQUEST);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Response<?>> handleHttpMessageNotReadableException(
    HttpMessageNotReadableException ex) {
    log.error(ERROR_TEMPLATE, JSON_ERROR_MESSAGE, ex.getMessage());
    Notification notification = Notification.valueOf(JSON_ERROR_MESSAGE);
    return new ResponseEntity<>(Response.of(null, List.of(notification)), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ServiceUnavailableException.class)
  public ResponseEntity<Response<?>> handleServiceUnavailableException(
    ServiceUnavailableException ex) {
    log.error(ERROR_TEMPLATE, SERVICE_UNAVAILABLE_ERROR_MESSAGE, ex.getMessage());
    Notification notification = Notification.valueOf(SERVICE_UNAVAILABLE_ERROR_MESSAGE);
    return new ResponseEntity<>(Response.of(null, List.of(notification)),
      HttpStatus.SERVICE_UNAVAILABLE);
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<Response<?>> handleNoResourceFoundException(NoResourceFoundException ex) {
    Notification notification = Notification.valueOf(NOT_FOUND_ERROR_MESSAGE);
    return new ResponseEntity<>(Response.of(null, List.of(notification)), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<Response<?>> handleHttpRequestMethodNotSupported(
    HttpRequestMethodNotSupportedException ex) {
    Notification notification = Notification.valueOf(
      "the HTTP method " + ex.getMethod() + " is not supported for this endpoint");
    return new ResponseEntity<>(Response.of(null, List.of(notification)),
      HttpStatus.METHOD_NOT_ALLOWED);
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Response<?>> handleGenericException(Exception ex) {
    log.error(ERROR_TEMPLATE, INTERNAL_SERVER_ERROR_MESSAGE, ex.getMessage());
    Notification notification = Notification.valueOf(INTERNAL_SERVER_ERROR_MESSAGE);
    return new ResponseEntity<>(Response.of(null, List.of(notification)),
      HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
