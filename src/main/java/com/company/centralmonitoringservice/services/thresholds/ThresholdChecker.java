package com.company.centralmonitoringservice.services.thresholds;

import com.company.centralmonitoringservice.models.SensorData;

/**
 * Interface for checking sensor data against a threshold.
 */
public interface ThresholdChecker {

  /**
   * Checks if the provided sensor data exceeds the configured threshold.
   *
   * @param sensorData the object containing information about the sensor and its reported value
   * @return true if the sensor data exceeds the threshold, false otherwise
   */
  boolean exceedsThreshold(SensorData sensorData);
}