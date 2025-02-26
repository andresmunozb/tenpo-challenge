package cl.tenpo.tenpochallenge.core.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
  private T data;
  private List<Notification> notifications;

  public Response(T data) {
    this.data = data;
    this.notifications = new ArrayList<>();
  }

  public static <T> Response<T> of(T data) {
    return new Response<>(data);
  }

  public static <T> Response<T> of(List<Notification> notifications) {
    return new Response<>(null, notifications);
  }

  public static <T> Response<T> of(T data, List<Notification> notifications) {
    return new Response<>(data, notifications);
  }

  public static <T> Response<T> empty() {
    return new Response<>(null);
  }
}
