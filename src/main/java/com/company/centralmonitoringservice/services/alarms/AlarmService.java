package com.company.centralmonitoringservice.services.alarms;

import com.company.centralmonitoringservice.models.SensorData;

/**
 * Interface for services that raise alarms based on sensor data.
 */
public interface AlarmService {

  /**
   * Raises an alarm when the sensor data exceeds the configured threshold.
   *
   * @param sensorData the object containing information about the sensor and its reported value
   */
  void raiseAlarm(SensorData sensorData);
}