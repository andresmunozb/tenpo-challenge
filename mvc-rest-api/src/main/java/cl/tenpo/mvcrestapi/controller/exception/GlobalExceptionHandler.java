package cl.tenpo.mvcrestapi.controller.exception;

import cl.tenpo.mvcrestapi.core.common.Notification;
import cl.tenpo.mvcrestapi.core.common.Response;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Response<?>> handleValidationExceptions(
    MethodArgumentNotValidException ex) {
    return new ResponseEntity<>(
      Response.of(null, Notification.valueOf(ex)), HttpStatus.BAD_REQUEST);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Response<?>> handleConstraintViolationException(
    ConstraintViolationException ex) {
    return new ResponseEntity<>(
      Response.of(null, Notification.valueOf(ex)), HttpStatus.BAD_REQUEST);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Response<?>> handleHttpMessageNotReadableException(
    HttpMessageNotReadableException ex) {
    String errorMessage = "malformed json or invalid data type"; // todo move to constant
    Notification notification = Notification.valueOf(errorMessage);
    return new ResponseEntity<>(
      Response.of(null, List.of(notification)),
      HttpStatus.BAD_REQUEST
    );
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Response<?>> handleGenericException(Exception ex) {
    Notification notification =
      Notification.valueOf("internal server error"); // todo move to constant
    return new ResponseEntity<>(
      Response.of(null, List.of(notification)),
      HttpStatus.BAD_REQUEST
    );
  }
}
