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

class TemperatureThresholdCheckerTest {

  @Mock
  private ApplicationProperties applicationProperties;

  @InjectMocks
  private TemperatureThresholdChecker temperatureThresholdChecker;

  @BeforeEach
  void setUp() {
    // Initialize mocks
    applicationProperties = mock(ApplicationProperties.class);
    temperatureThresholdChecker = new TemperatureThresholdChecker(applicationProperties);
  }

  @Test
  void testExceedsThreshold_WhenValueExceedsThreshold() {
    // Given
    SensorData sensorData = new SensorData(SensorType.TEMPERATURE, "sensor-1", 100.0);
    when(applicationProperties.getTemperatureThreshold()).thenReturn(90.0);

    // When
    boolean result = temperatureThresholdChecker.exceedsThreshold(sensorData);

    // Then
    assertTrue(result);
  }

  @Test
  void testExceedsThreshold_WhenValueDoesNotExceedThreshold() {
    // Given
    SensorData sensorData = new SensorData(SensorType.TEMPERATURE, "sensor-1", 80.0);
    when(applicationProperties.getTemperatureThreshold()).thenReturn(90.0);

    // When
    boolean result = temperatureThresholdChecker.exceedsThreshold(sensorData);

    // Then
    assertFalse(result);
  }

  @Test
  void testExceedsThreshold_WhenValueEqualsThreshold() {
    // Given
    SensorData sensorData = new SensorData(SensorType.TEMPERATURE, "sensor-1", 90.0);
    when(applicationProperties.getTemperatureThreshold()).thenReturn(90.0);

    // When
    boolean result = temperatureThresholdChecker.exceedsThreshold(sensorData);

    // Then
    assertFalse(result);
  }
}