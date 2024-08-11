package com.company.centralmonitoringservice.services.alarms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ch.qos.logback.classic.Logger;
import com.company.centralmonitoringservice.commons.TestAppender;
import com.company.centralmonitoringservice.enums.SensorType;
import com.company.centralmonitoringservice.models.SensorData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

class LoggingAlarmServiceTest {

  private LoggingAlarmService loggingAlarmService;
  private TestAppender testAppender;

  @BeforeEach
  void setUp() {
    // Create an instance of LoggingAlarmService
    loggingAlarmService = new LoggingAlarmService();

    // Set up a custom appender to capture log messages
    testAppender = new TestAppender();
    Logger logger = (Logger) LoggerFactory.getLogger(LoggingAlarmService.class);
    logger.addAppender(testAppender);
    testAppender.start();
  }

  @Test
  void testRaiseAlarm() {
    // Given
    SensorData sensorData = new SensorData(SensorType.TEMPERATURE, "sensor-1", 75.5);

    // When
    loggingAlarmService.raiseAlarm(sensorData);

    // Then
    assertEquals(1, testAppender.getEvents().size());
    assertTrue(testAppender.getEvents().get(0).getFormattedMessage().contains(sensorData.getSensorType().toString()));
    assertTrue(testAppender.getEvents().get(0).getFormattedMessage().contains(sensorData.getSensorId()));
    assertTrue(testAppender.getEvents().get(0).getFormattedMessage().contains(String.valueOf(sensorData.getValue())));
  }
}