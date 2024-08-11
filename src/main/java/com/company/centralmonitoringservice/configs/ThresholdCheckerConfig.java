package com.company.centralmonitoringservice.configs;

import com.company.centralmonitoringservice.enums.SensorType;
import com.company.centralmonitoringservice.services.thresholds.HumidityThresholdChecker;
import com.company.centralmonitoringservice.services.thresholds.TemperatureThresholdChecker;
import com.company.centralmonitoringservice.services.thresholds.ThresholdChecker;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up the threshold checkers.
 */
@Configuration
public class ThresholdCheckerConfig {

  /**
   * Creates a map of ThresholdChecker instances with key SensorType.
   * This map allows to retrieve the correct ThresholdChecker implementation based on the sensor type.
   * For each sensor type, a specific implementation of ThresholdChecker is used to perform the threshold check.
   *
   * @param temperatureThresholdChecker for temperature sensors
   * @param humidityThresholdChecker    for humidity sensors
   * @return a Map where keys are SensorType and values are ThresholdChecker instances.
   */
  @Bean
  public Map<SensorType, ThresholdChecker> thresholdCheckers(
      TemperatureThresholdChecker temperatureThresholdChecker,
      HumidityThresholdChecker humidityThresholdChecker) {
    return Map.of(
        SensorType.TEMPERATURE, temperatureThresholdChecker,
        SensorType.HUMIDITY, humidityThresholdChecker
    );
  }
}