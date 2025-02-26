package cl.tenpo.tenpochallenge.core.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MinimalPage<T> {

  @Builder.Default
  private List<T> content = List.of();

  @Builder.Default
  private Integer totalPages = 0;

  @Builder.Default
  private Long totalElements = 0L;

  @Builder.Default
  private Integer numberOfElements = 0;

  public static <T> MinimalPage<T> of(Page<T> page) {
    return MinimalPage.<T>builder().content(page.getContent())
      .totalPages(page.getTotalPages())
      .totalElements(page.getTotalElements())
      .numberOfElements(page.getNumberOfElements())
      .build();
  }
}
