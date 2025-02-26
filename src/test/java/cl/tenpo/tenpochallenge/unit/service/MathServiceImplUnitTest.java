package cl.tenpo.tenpochallenge.unit.service;

import cl.tenpo.tenpochallenge.service.exception.UnavailableExternalPercentageServiceException;
import cl.tenpo.tenpochallenge.service.impl.ExternalPercentageServiceImpl;
import cl.tenpo.tenpochallenge.service.impl.MathServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class MathServiceImplUnitTest {

  @Mock
  private ExternalPercentageServiceImpl externalPercentageService;

  @InjectMocks
  private MathServiceImpl mathService;

  @Test
  void shouldReturnCorrectSumWithPercentageApplied() {
    BigDecimal expected = BigDecimal.valueOf(11).setScale(10, RoundingMode.HALF_UP);
    Mockito.when(externalPercentageService.getPercentage()).thenReturn(BigDecimal.valueOf(0.1));

    List<BigDecimal> numbers = List.of(BigDecimal.valueOf(5), BigDecimal.valueOf(5));
    BigDecimal result = mathService.add(numbers).setScale(10, RoundingMode.HALF_UP);

    Assertions.assertNotNull(result);
    Assertions.assertEquals(expected, result);
  }

  @Test
  void shouldThrowUnavailableExternalPercentageServiceException() {

    Mockito.when(externalPercentageService.getPercentage())
      .thenThrow(new UnavailableExternalPercentageServiceException());

    List<BigDecimal> numbers = List.of(BigDecimal.valueOf(5), BigDecimal.valueOf(5));


    Assertions.assertThrows(
      UnavailableExternalPercentageServiceException.class,
      () -> mathService.add(numbers).setScale(10, RoundingMode.HALF_UP));
  }
}
