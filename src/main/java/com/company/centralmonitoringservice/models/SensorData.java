package com.company.centralmonitoringservice.models;

import com.company.centralmonitoringservice.enums.SensorType;
import lombok.Getter;

/**
 * Represents the data received from a sensor.
 */
@Getter
public class SensorData {

  /**
   * The type of sensor that generated the data.
   */
  private final SensorType sensorType;

  /**
   * The unique identifier of the sensor.
   */
  private final String sensorId;

  /**
   * The value reported by the sensor.
   */
  private final double value;

  /**
   * Constructs a new SensorData instance with the specified sensor type, sensor ID, and value.
   *
   * @param sensorType the type of sensor that generated the data
   * @param sensorId   the unique identifier of the sensor
   * @param value      the measurement value reported by the sensor
   */
  public SensorData(SensorType sensorType, String sensorId, double value) {
    this.sensorType = sensorType;
    this.sensorId = sensorId;
    this.value = value;
  }
}