package com.company.centralmonitoringservice.services.monitors;

import com.company.centralmonitoringservice.models.SensorData;

/**
 * Interface for monitoring sensor data against configured thresholds.
 */
public interface ThresholdMonitor {
  /**
   * Checks if the provided sensor data exceeds the configured threshold based on its type.
   *
   * @param sensorData to be checked against the thresholds
   */
  void checkThresholds(SensorData sensorData);
}