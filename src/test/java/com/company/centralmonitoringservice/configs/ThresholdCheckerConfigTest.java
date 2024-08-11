package com.company.centralmonitoringservice.configs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.company.centralmonitoringservice.enums.SensorType;
import com.company.centralmonitoringservice.services.thresholds.HumidityThresholdChecker;
import com.company.centralmonitoringservice.services.thresholds.TemperatureThresholdChecker;
import com.company.centralmonitoringservice.services.thresholds.ThresholdChecker;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ThresholdCheckerConfigTest {

  @Mock
  private TemperatureThresholdChecker temperatureThresholdChecker;

  @Mock
  private HumidityThresholdChecker humidityThresholdChecker;

  @InjectMocks
  private ThresholdCheckerConfig thresholdCheckerConfig;

  private Map<SensorType, ThresholdChecker> thresholdCheckerMap;

  @BeforeEach
  void setUp() {
    thresholdCheckerMap = thresholdCheckerConfig.thresholdCheckers(temperatureThresholdChecker, humidityThresholdChecker);
  }

  @Test
  void testThresholdCheckersMap_NotNull() {
    // Verify that the map is not null
    assertNotNull(thresholdCheckerMap);
  }

  @Test
  void testThresholdCheckersMap_Size() {
    // Verify that the map has the correct number of entries
    assertEquals(2, thresholdCheckerMap.size());
  }

  @Test
  void testTemperatureThresholdCheckerMapping() {
    // Verify that the map contains the correct ThresholdChecker for TEMPERATURE
    assertTrue(thresholdCheckerMap.containsKey(SensorType.TEMPERATURE));
    assertSame(temperatureThresholdChecker, thresholdCheckerMap.get(SensorType.TEMPERATURE));
  }

  @Test
  void testHumidityThresholdCheckerMapping() {
    // Verify that the map contains the correct ThresholdChecker for HUMIDITY
    assertTrue(thresholdCheckerMap.containsKey(SensorType.HUMIDITY));
    assertSame(humidityThresholdChecker, thresholdCheckerMap.get(SensorType.HUMIDITY));
  }

  @Test
  void testNoUnexpectedMappings() {
    // Verify that the map doesn't contain any other unexpected SensorType mappings
    assertFalse(thresholdCheckerMap.containsKey(SensorType.UNKNOWN));
  }
}