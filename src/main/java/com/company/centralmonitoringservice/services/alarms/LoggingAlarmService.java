package com.company.centralmonitoringservice.services.alarms;

import com.company.centralmonitoringservice.constants.Messages;
import com.company.centralmonitoringservice.models.SensorData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for handling and raising alarms based on sensor data.
 */
@Service
public class LoggingAlarmService implements AlarmService {

  private static final Logger logger = LoggerFactory.getLogger(LoggingAlarmService.class);

  /**
   * Raises an alarm by logging a warning message when the sensor data exceeds the threshold.
   *
   * @param sensorData the object containing information about the sensor and its reported value
   * @see SensorData
   * @see Messages#ALARM_SENSOR_VALUE_EXCEEDS_THRESHOLD
   */
  @Override
  public void raiseAlarm(SensorData sensorData) {
    logger.warn(Messages.ALARM_SENSOR_VALUE_EXCEEDS_THRESHOLD,
        sensorData.getSensorType(),
        sensorData.getSensorId(),
        sensorData.getValue());
  }
}