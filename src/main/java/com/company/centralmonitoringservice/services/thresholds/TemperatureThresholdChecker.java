package com.company.centralmonitoringservice.services.thresholds;

import com.company.centralmonitoringservice.configs.ApplicationProperties;
import com.company.centralmonitoringservice.models.SensorData;
import org.springframework.stereotype.Service;

/**
 * Implementation of ThresholdChecker for temperature sensors.
 */
@Service
public class TemperatureThresholdChecker implements ThresholdChecker {

  private final ApplicationProperties applicationProperties;

  /**
   * Constructs a new TemperatureThresholdChecker with the specified ApplicationProperties.
   *
   * @param applicationProperties the application properties containing the temperature threshold
   */
  public TemperatureThresholdChecker(ApplicationProperties applicationProperties) {
    this.applicationProperties = applicationProperties;
  }

  /**
   * Checks if the temperature sensor data exceeds the configured threshold.
   */
  @Override
  public boolean exceedsThreshold(SensorData sensorData) {
    return sensorData.getValue() > applicationProperties.getTemperatureThreshold();
  }
}