package com.company.centralmonitoringservice.utils;

import com.company.centralmonitoringservice.constants.Messages;
import com.company.centralmonitoringservice.enums.SensorType;
import com.company.centralmonitoringservice.models.SensorData;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory class for creating SensorData objects.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SensorDataFactory {
  private static final Logger logger = LoggerFactory.getLogger(SensorDataFactory.class);

  /**
   * Creates a SensorData object from a kafka message.
   *
   * @param message the kafka message
   * @return the created SensorData object
   */
  public static SensorData createSensorData(String message) {
    String[] parts = message.split(";");
    String sensorTypeStr = parts[0].split("=")[1];
    String sensorId = parts[1].split("=")[1];
    double value = Double.parseDouble(parts[2].split("=")[1]);
    SensorType sensorType = null;
    try {
      sensorType = SensorType.valueOf(sensorTypeStr.toUpperCase());
    } catch (IllegalArgumentException e) {
      logger.error(Messages.ILLEGAL_SENSOR_TYPE_ENCOUNTERED, sensorTypeStr);
    }
    return new SensorData(sensorType, sensorId, value);
  }
}