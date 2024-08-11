package com.company.centralmonitoringservice.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for application properties related to Kafka and sensor settings.
 * This class maps application configuration properties from
 * the `application.properties` file to Java fields.
 */
@Configuration
@Getter
@Setter
public class ApplicationProperties {
  /**
   * The Kafka bootstrap servers.
   */
  @Value("${spring.kafka.consumer.bootstrap-servers}")
  private String bootstrapServers;

  /**
   * The Kafka consumer group ID.
   */
  @Value("${spring.kafka.consumer.group-id}")
  private String groupId;

  /**
   * The Kafka key deserializer class.
   */
  @Value("${spring.kafka.consumer.key-deserializer}")
  private String keyDeserializer;

  /**
   * The Kafka value deserializer class.
   */
  @Value("${spring.kafka.consumer.value-deserializer}")
  private String valueDeserializer;

  /**
   * The Kafka auto-offset reset policy.
   */
  @Value("${spring.kafka.consumer.auto-offset-reset}")
  private String autoOffsetReset;

  /**
   * The Kafka topic for sensor data.
   */
  @Value("${kafka.topic.sensor-data}")
  private String kafkaTopic;

  /**
   * The threshold for temperature sensors.
   */
  @Value("${sensor.temperature.threshold}")
  private double temperatureThreshold;

  /**
   * The threshold for humidity sensors.
   */
  @Value("${sensor.humidity.threshold}")
  private double humidityThreshold;
}