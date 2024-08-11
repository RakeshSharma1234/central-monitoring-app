package com.company.centralmonitoringservice.configs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerConfigTest {

  @Mock
  private ApplicationProperties applicationProperties;

  @InjectMocks
  private KafkaConsumerConfig kafkaConsumerConfig;

  @BeforeEach
  public void setUp() {

  }

  @Test
  void testReceiverOptions() {
    // Setup mock application properties
    when(applicationProperties.getBootstrapServers()).thenReturn("localhost:9092");
    when(applicationProperties.getGroupId()).thenReturn("test-group");
    when(applicationProperties.getKeyDeserializer()).thenReturn("org.apache.kafka.common.serialization.StringDeserializer");
    when(applicationProperties.getValueDeserializer()).thenReturn("org.apache.kafka.common.serialization.StringDeserializer");
    when(applicationProperties.getAutoOffsetReset()).thenReturn("earliest");
    when(applicationProperties.getKafkaTopic()).thenReturn("test-topic");

    // Test the receiverOptions method
    ReceiverOptions<String, String> receiverOptions = kafkaConsumerConfig.receiverOptions();

    // Verify the properties are set correctly
    Map<String, Object> consumerProps = receiverOptions.consumerProperties();
    assertEquals("localhost:9092", consumerProps.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG));
    assertEquals("test-group", consumerProps.get(ConsumerConfig.GROUP_ID_CONFIG));
    assertEquals("org.apache.kafka.common.serialization.StringDeserializer",
        consumerProps.get(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG));
    assertEquals("org.apache.kafka.common.serialization.StringDeserializer",
        consumerProps.get(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG));
    assertEquals("earliest", consumerProps.get(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG));
    // Verify the topic subscription
    assertEquals(Collections.singleton("test-topic"), receiverOptions.subscriptionTopics());
  }

  @Test
  void testKafkaReceiver() {
    // Create a mock ReceiverOptions
    ReceiverOptions<String, String> mockReceiverOptions = mock(ReceiverOptions.class);

    // Test the kafkaReceiver method
    KafkaReceiver<String, String> kafkaReceiver = kafkaConsumerConfig.kafkaReceiver(mockReceiverOptions);

    // Verify that a KafkaReceiver is created with the correct ReceiverOptions
    assertNotNull(kafkaReceiver);
    ReceiverOptions<String, String> actualReceiverOptions = (ReceiverOptions<String, String>)
        ReflectionTestUtils.getField(kafkaReceiver, "receiverOptions");
    assertSame(mockReceiverOptions, actualReceiverOptions);
  }
}