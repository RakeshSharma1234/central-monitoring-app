package com.company.centralmonitoringservice.enums;

import lombok.AllArgsConstructor;

/**
 * Enum representing different types of sensors.
 */
@AllArgsConstructor
public enum SensorType {
  /**
   * Temperature sensor type.
   */
  TEMPERATURE,

  /**
   * Humidity sensor type.
   */
  HUMIDITY,

  /**
   * Unknown or unspecified sensor type.
   */
  UNKNOWN
}