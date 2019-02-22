package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FormatUtilTest {
  @Test
  @DisplayName("formatList should return info message if list is empty")
  void formatUtilShouldReturnMessageIfEmpty() {
    final List<Integer> integers = new ArrayList<>(0);

    assertEquals("No open ports", FormatUtil.formatList(integers));
  }

  @Test
  @DisplayName("formatList should return string built from array")
  void formatUtilShouldReturnStringFromArray() {
    final List<Integer> integers = new ArrayList<>(2);
    integers.add(12);
    integers.add(24);

    assertEquals("Open ports: \n12, 24", FormatUtil.formatList(integers));
  }
}
