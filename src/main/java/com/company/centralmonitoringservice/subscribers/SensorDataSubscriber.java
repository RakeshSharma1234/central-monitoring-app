package com.company.centralmonitoringservice.subscribers;

import com.company.centralmonitoringservice.models.SensorData;
import com.company.centralmonitoringservice.services.monitors.ThresholdMonitor;
import com.company.centralmonitoringservice.utils.SensorDataFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverRecord;

/**
 * This service subscribes to a Kafka topic, processes the received sensor data
 * and check the predefined threshold against the sensor data via the ThresholdMonitorService.
 */
@Component
public class SensorDataSubscriber {

  private final ThresholdMonitor thresholdMonitor;
  private final KafkaReceiver<String, String> kafkaReceiver;

  /**
   * Constructs a new SensorDataSubscriber instance.
   *
   * @param thresholdMonitor the service responsible for checking sensor data against thresholds
   * @param kafkaReceiver    the Kafka receiver for subscribing to sensor data
   */
  public SensorDataSubscriber(ThresholdMonitor thresholdMonitor, KafkaReceiver<String, String> kafkaReceiver) {
    this.thresholdMonitor = thresholdMonitor;
    this.kafkaReceiver = kafkaReceiver;
  }

  /**
   * Initializes the subscription to the Kafka topic.
   * It subscribes to the Kafka receiver and processes each record as it is received.
   */
  @PostConstruct
  public void startConsumingMessages() {
    kafkaReceiver.receive().doOnNext(this::processRecord).subscribe();
  }

  /**
   * Processes a received Kafka record and extracts the sensor data from the received Kafka record,
   * converts it to a SensorData object and monitor the sensor data against configured thresholds,
   * and acknowledges the record offset.
   *
   * @param receiverRecord the received Kafka record
   */
  private void processRecord(ReceiverRecord<String, String> receiverRecord) {
    String recordValue = receiverRecord.value();
    SensorData sensorData = SensorDataFactory.createSensorData(recordValue);
    thresholdMonitor.checkThresholds(sensorData);
    receiverRecord.receiverOffset().acknowledge();
  }
}
