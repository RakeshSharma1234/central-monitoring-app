package com.company.centralmonitoringservice.services.monitors;

import com.company.centralmonitoringservice.constants.Messages;
import com.company.centralmonitoringservice.enums.SensorType;
import com.company.centralmonitoringservice.models.SensorData;
import com.company.centralmonitoringservice.services.alarms.AlarmService;
import com.company.centralmonitoringservice.services.thresholds.ThresholdChecker;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service class for monitoring sensor data against configured thresholds.
 */
@Service
public class ThresholdMonitorService implements ThresholdMonitor {

  private static final Logger logger = LoggerFactory.getLogger(ThresholdMonitorService.class);

  private final Map<SensorType, ThresholdChecker> thresholdCheckers;
  private final AlarmService alarmService;

  /**
   * Constructs a new ThresholdMonitorService with the specified AlarmService and threshold checkers.
   *
   * @param alarmService      the service responsible for raising alarms
   * @param thresholdCheckers a map of sensor types to their corresponding threshold checkers
   */
  public ThresholdMonitorService(AlarmService alarmService, Map<SensorType, ThresholdChecker> thresholdCheckers) {
    this.alarmService = alarmService;
    this.thresholdCheckers = thresholdCheckers;
  }

  /**
   * Checks if the provided sensor data exceeds the configured threshold based on its type.
   *
   * @param sensorData to be checked against the thresholds
   */
  @Override
  public void checkThresholds(SensorData sensorData) {
    ThresholdChecker thresholdChecker = thresholdCheckers.get(sensorData.getSensorType());
    if (thresholdChecker != null && thresholdChecker.exceedsThreshold(sensorData)) {
      alarmService.raiseAlarm(sensorData);
    } else if (thresholdChecker == null) {
      // Log the invalid sensor type
      logger.error(Messages.INVALID_SENSOR_TYPE, sensorData.getSensorType());
    }
  }
}