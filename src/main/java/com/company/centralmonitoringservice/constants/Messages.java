package com.company.centralmonitoringservice.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * A constant class that holds message templates used throughout the application.
 * It is designed to be used by other classes for consistent message formatting.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Messages {
  /**
   * Message template for indicating that a sensor's value has exceeded its threshold.
   */
  public static final String ALARM_SENSOR_VALUE_EXCEEDS_THRESHOLD = "ALARM! {} sensor {} exceeded threshold with value: {}";

  /**
   * Message template for indicating illegal sensor's type encountered while parsing the kafka message.
   */
  public static final String ILLEGAL_SENSOR_TYPE_ENCOUNTERED = "Illegal sensor type encountered: {}";

  /**
   * Message template for indicating invalid sensor's type.
   */
  public static final String INVALID_SENSOR_TYPE = "Invalid sensor type: {}";

}
