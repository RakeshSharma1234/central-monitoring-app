package com.company.centralmonitoringservice.subscribers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.company.centralmonitoringservice.services.monitors.ThresholdMonitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOffset;
import reactor.kafka.receiver.ReceiverRecord;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

@SpringBootTest
class SensorDataSubscriberTest {

  @Mock
  private ThresholdMonitor thresholdMonitor;

  @Mock
  private KafkaReceiver<String, String> kafkaReceiver;

  @InjectMocks
  private SensorDataSubscriber sensorDataSubscriber;

  @BeforeEach
  public void setUp() {
    sensorDataSubscriber = new SensorDataSubscriber(thresholdMonitor, kafkaReceiver);
  }

  @Test
  void testStartConsumingMessages() {
    // Given
    ReceiverRecord<String, String> mockReceiverRecord = mock(ReceiverRecord.class);
    TestPublisher<ReceiverRecord<String, String>> testPublisher = TestPublisher.create();
    when(kafkaReceiver.receive()).thenReturn(testPublisher.flux());

    // When
    sensorDataSubscriber.startConsumingMessages();

    // Then
    testPublisher.emit(mockReceiverRecord);
    verify(kafkaReceiver).receive();
  }

  @Test
  void testStartConsumingMessages_EndToEnd() {
    // Given
    String mockSensorData = "type=TEMPERATURE;sensor_id=h1;value=40";
    ReceiverRecord<String, String> mockReceiverRecord = mock(ReceiverRecord.class);
    ReceiverOffset mockReceiverOffset = mock(ReceiverOffset.class);

    when(mockReceiverRecord.value()).thenReturn(mockSensorData);
    when(mockReceiverRecord.receiverOffset()).thenReturn(mockReceiverOffset);

    Flux<ReceiverRecord<String, String>> mockFlux = Flux.just(mockReceiverRecord);
    when(kafkaReceiver.receive()).thenReturn(mockFlux);

    // When & Then
    StepVerifier.create(mockFlux)
        .expectNext(mockReceiverRecord)
        .then(() -> sensorDataSubscriber.startConsumingMessages())
        .verifyComplete();

    verify(thresholdMonitor).checkThresholds(any());
    verify(mockReceiverOffset).acknowledge();
  }
}