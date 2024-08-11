package com.company.centralmonitoringservice.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.company.centralmonitoringservice.commons.TestAppender;
import com.company.centralmonitoringservice.enums.SensorType;
import com.company.centralmonitoringservice.models.SensorData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class SensorDataFactoryTest {
  private TestAppender testAppender;

  @BeforeEach
  void setUp() {
    // Set up a custom appender to capture log messages
    testAppender = new TestAppender();
    ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(SensorDataFactory.class);
    logger.addAppender(testAppender);
    testAppender.start();
  }

  @Test
  void testCreateSensorData_ValidMessage() {
    // Given
    String message = "sensorType=temperature;sensorId=sensor-1;value=25.5";

    // When
    SensorData sensorData = SensorDataFactory.createSensorData(message);

    // Then
    assertNotNull(sensorData);
    assertEquals(SensorType.TEMPERATURE, sensorData.getSensorType());
    assertEquals("sensor-1", sensorData.getSensorId());
    assertEquals(25.5, sensorData.getValue());
  }

  @Test
  void testCreateSensorData_InvalidSensorType() {
    // Given
    String message = "sensorType=invalidType;sensorId=sensor-1;value=25.5";

    try (MockedStatic<LoggerFactory> mockedLoggerFactory = Mockito.mockStatic(LoggerFactory.class)) {
      Logger mockLogger = mock(Logger.class);
      mockedLoggerFactory.when(() -> LoggerFactory.getLogger(SensorDataFactory.class)).thenReturn(mockLogger);

      // When
      SensorData sensorData = SensorDataFactory.createSensorData(message);

      // Then
      assertNotNull(sensorData);
      assertNull(sensorData.getSensorType()); // SensorType should be null due to invalid type
      assertEquals("sensor-1", sensorData.getSensorId());
      assertEquals(25.5, sensorData.getValue());
      assertEquals(1, testAppender.getEvents().size());
      assertTrue(testAppender.getEvents().get(0).getFormattedMessage().contains("invalidType"));
    }
  }

  @Test
  void testCreateSensorData_MalformedMessage() {
    // Given
    String message = "sensorType=temperature;sensorId=sensor-1";

    assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
      // When
      SensorDataFactory.createSensorData(message);
    });
  }
}