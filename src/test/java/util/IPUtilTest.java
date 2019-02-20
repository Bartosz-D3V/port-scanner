package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

class IPUtilTest {

  @ParameterizedTest
  @ValueSource(strings = {
    "119.175.208.93",
    "10.232.49.251",
    "151.26.133.1",
    "151.57.195.64",
    "96.236.227.198",
    "38.68.227.41",
    "133.20.213.239",
    "177.33.228.124",
    "177.28.75.88",
    "57.229.35.201"})
  @DisplayName("ValidateIP should return true if IP is correct")
  void validateIPShouldReturnTrueIfIPIsCorrect(String ip) {
    assertTrue(IPUtil.validateIP(ip));
  }
}
