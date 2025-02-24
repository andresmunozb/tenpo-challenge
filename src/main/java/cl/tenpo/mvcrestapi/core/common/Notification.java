package cl.tenpo.mvcrestapi.core.common;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

@Data
@Builder
public class Notification {
  private String message;

  public static Notification valueOf(String error) {
    return Notification.builder().message(error).build();
  }

  public static Notification valueOf(ObjectError error) {
    String fieldName = ((FieldError) error).getField();
    String errorMessage = error.getDefaultMessage();
    return Notification.builder().message("[" + fieldName + "] " + errorMessage).build();
  }

  public static Notification valueOf(ConstraintViolation<?> violation) {
    return Notification.builder()
      .message("[" + violation.getPropertyPath() + "] " + violation.getMessage())
      .build();
  }

  public static List<Notification> valueOf(ConstraintViolationException exception) {
    return exception.getConstraintViolations().stream()
      .map(Notification::valueOf)
      .toList();
  }

  public static List<Notification> valueOf(MethodArgumentNotValidException exception) {
    return exception.getBindingResult().getAllErrors().stream()
      .map(Notification::valueOf)
      .toList();
  }
}
