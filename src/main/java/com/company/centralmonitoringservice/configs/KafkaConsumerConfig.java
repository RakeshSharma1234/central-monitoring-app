package com.company.centralmonitoringservice.configs;

import java.util.Collections;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

/**
 * Configuration class for setting up Kafka consumer properties and beans.
 * This class configures the Kafka consumer by creating beans for ReceiverOptions
 * and KafkaReceiver based on properties provided in ApplicationProperties.
 */
@Configuration
public class KafkaConsumerConfig {
  private final ApplicationProperties applicationProperties;

  /**
   * Constructs a KafkaConsumerConfig instance with the provided application properties.
   *
   * @param applicationProperties the application properties containing Kafka configuration properties
   */
  public KafkaConsumerConfig(ApplicationProperties applicationProperties) {
    this.applicationProperties = applicationProperties;
  }

  /**
   * Creates a ReceiverOptions bean configured with properties from ApplicationProperties file.
   * This method sets up the Kafka consumer options, including:
   * It also sets up a subscription to the Kafka topic specified in the application properties.
   *
   * @return a configured ReceiverOptions instance
   */
  @Bean
  public ReceiverOptions<String, String> receiverOptions() {
    ReceiverOptions<String, String> receiverOptions = ReceiverOptions.create(
        Map.of(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, applicationProperties.getBootstrapServers(),
            ConsumerConfig.GROUP_ID_CONFIG, applicationProperties.getGroupId(),
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, applicationProperties.getKeyDeserializer(),
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, applicationProperties.getValueDeserializer(),
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, applicationProperties.getAutoOffsetReset()
        )
    );
    return receiverOptions.subscription(Collections.singleton(applicationProperties.getKafkaTopic()));
  }

  /**
   * Creates a KafkaReceiver bean configured with the provided ReceiverOptions.
   *
   * @param receiverOptions the receiver options to configure the Kafka receiver
   * @return a configured KafkaReceiver instance
   */
  @Bean
  public KafkaReceiver<String, String> kafkaReceiver(ReceiverOptions<String, String> receiverOptions) {
    return KafkaReceiver.create(receiverOptions);
  }
}