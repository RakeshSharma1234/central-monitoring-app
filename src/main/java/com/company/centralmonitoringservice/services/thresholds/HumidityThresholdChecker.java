package com.company.centralmonitoringservice.services.thresholds;

import com.company.centralmonitoringservice.configs.ApplicationProperties;
import com.company.centralmonitoringservice.models.SensorData;
import org.springframework.stereotype.Service;

/**
 * Implementation of ThresholdChecker for humidity sensors.
 */
@Service
public class HumidityThresholdChecker implements ThresholdChecker {

  private final ApplicationProperties applicationProperties;

  /**
   * Constructs a new HumidityThresholdChecker with the specified ApplicationProperties.
   *
   * @param applicationProperties the application properties containing the humidity threshold
   */
  public HumidityThresholdChecker(ApplicationProperties applicationProperties) {
    this.applicationProperties = applicationProperties;
  }

  /**
   * Checks if the humidity sensor data exceeds the configured threshold.
   */
  @Override
  public boolean exceedsThreshold(SensorData sensorData) {
    return sensorData.getValue() > applicationProperties.getHumidityThreshold();
  }
}