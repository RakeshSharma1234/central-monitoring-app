package com.company.centralmonitoringservice.services.thresholds;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.company.centralmonitoringservice.configs.ApplicationProperties;
import com.company.centralmonitoringservice.enums.SensorType;
import com.company.centralmonitoringservice.models.SensorData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class HumidityThresholdCheckerTest {

  @Mock
  private ApplicationProperties applicationProperties;

  @InjectMocks
  private HumidityThresholdChecker humidityThresholdChecker;

  @BeforeEach
  void setUp() {
    // Initialize mocks
    applicationProperties = mock(ApplicationProperties.class);
    humidityThresholdChecker = new HumidityThresholdChecker(applicationProperties);
  }

  @Test
  void testExceedsThreshold_WhenValueExceedsThreshold() {
    // Given
    SensorData sensorData = new SensorData(SensorType.HUMIDITY, "sensor-1", 75.0);
    when(applicationProperties.getHumidityThreshold()).thenReturn(70.0);

    // When
    boolean result = humidityThresholdChecker.exceedsThreshold(sensorData);

    // Then
    assertTrue(result);
  }

  @Test
  void testExceedsThreshold_WhenValueDoesNotExceedThreshold() {
    // Given
    SensorData sensorData = new SensorData(SensorType.HUMIDITY, "sensor-1", 65.0);
    when(applicationProperties.getHumidityThreshold()).thenReturn(70.0);

    // When
    boolean result = humidityThresholdChecker.exceedsThreshold(sensorData);

    // Then
    assertFalse(result);
  }

  @Test
  void testExceedsThreshold_WhenValueEqualsThreshold() {
    // Given
    SensorData sensorData = new SensorData(SensorType.HUMIDITY, "sensor-1", 70.0);
    when(applicationProperties.getHumidityThreshold()).thenReturn(70.0);

    // When
    boolean result = humidityThresholdChecker.exceedsThreshold(sensorData);

    // Then
    assertFalse(result);
  }
}