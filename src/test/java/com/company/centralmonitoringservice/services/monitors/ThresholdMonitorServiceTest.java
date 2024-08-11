package com.company.centralmonitoringservice.services.monitors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ch.qos.logback.classic.Logger;
import com.company.centralmonitoringservice.commons.TestAppender;
import com.company.centralmonitoringservice.enums.SensorType;
import com.company.centralmonitoringservice.models.SensorData;
import com.company.centralmonitoringservice.services.alarms.AlarmService;
import com.company.centralmonitoringservice.services.thresholds.ThresholdChecker;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

@ExtendWith(MockitoExtension.class)
class ThresholdMonitorServiceTest {

  @Mock
  private AlarmService alarmService;

  @Mock
  private Map<SensorType, ThresholdChecker> thresholdCheckers;

  @Mock
  private ThresholdChecker humidityThresholdChecker;

  @Mock
  private ThresholdChecker temperatureThresholdChecker;

  @InjectMocks
  private ThresholdMonitorService thresholdMonitorService;

  private TestAppender testAppender;

  @BeforeEach
  void setUp() {
    // Create and attach custom appender to the logger
    testAppender = new TestAppender();
    Logger logger = (Logger) LoggerFactory.getLogger(ThresholdMonitorService.class);
    logger.addAppender(testAppender);
    testAppender.start();
  }

  @Test
  void testCheckThresholdsTemperature_ExceedsThreshold() {
    // Given
    SensorData sensorData = new SensorData(SensorType.TEMPERATURE, "sensor-1", 75.5);
    when(thresholdCheckers.get(SensorType.TEMPERATURE)).thenReturn(temperatureThresholdChecker);
    when(temperatureThresholdChecker.exceedsThreshold(sensorData)).thenReturn(true);

    // When
    thresholdMonitorService.checkThresholds(sensorData);

    // Then
    verify(alarmService).raiseAlarm(sensorData);
  }

  @Test
  void testCheckThresholdsTemperature_DoesNotExceedThreshold() {
    // Given
    SensorData sensorData = new SensorData(SensorType.TEMPERATURE, "sensor-1", 30.0);
    when(thresholdCheckers.get(SensorType.TEMPERATURE)).thenReturn(temperatureThresholdChecker);
    when(temperatureThresholdChecker.exceedsThreshold(sensorData)).thenReturn(false);

    // When
    thresholdMonitorService.checkThresholds(sensorData);

    // Then
    verify(alarmService, never()).raiseAlarm(sensorData);
  }

  @Test
  void testCheckThresholdsHumidity_ExceedsThreshold() {
    // Given
    SensorData sensorData = new SensorData(SensorType.HUMIDITY, "sensor-1", 55.0);
    when(thresholdCheckers.get(SensorType.HUMIDITY)).thenReturn(humidityThresholdChecker);
    when(humidityThresholdChecker.exceedsThreshold(sensorData)).thenReturn(true);

    // When
    thresholdMonitorService.checkThresholds(sensorData);

    // Then
    verify(alarmService).raiseAlarm(sensorData);
  }

  @Test
  void testCheckThresholdsHumidity_DoesNotExceedThreshold() {
    // Given
    SensorData sensorData = new SensorData(SensorType.HUMIDITY, "sensor-1", 30.0);
    when(thresholdCheckers.get(SensorType.HUMIDITY)).thenReturn(humidityThresholdChecker);
    when(humidityThresholdChecker.exceedsThreshold(sensorData)).thenReturn(false);

    // When
    thresholdMonitorService.checkThresholds(sensorData);

    // Then
    verify(alarmService, never()).raiseAlarm(sensorData);
  }

  @Test
  void testCheckThresholds_InvalidSensorType() {
    // Given
    SensorData sensorData = new SensorData(SensorType.UNKNOWN, "sensor-unknown", 50.0);
    when(thresholdCheckers.get(SensorType.UNKNOWN)).thenReturn(null);

    // When
    thresholdMonitorService.checkThresholds(sensorData);

    // Then
    assertTrue(testAppender.getEvents().stream()
        .anyMatch(event -> event.getFormattedMessage().contains(sensorData.getSensorType().toString())));
    assertEquals(1, testAppender.getEvents().size());
    verify(alarmService, never()).raiseAlarm(sensorData);
  }
}