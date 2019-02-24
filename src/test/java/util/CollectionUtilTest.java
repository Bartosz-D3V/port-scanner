package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CollectionUtilTest {
  @Test
  @DisplayName("divideArray should divide array into equal sub-arrays")
  void divideArrayShouldDivideArrayIntoSubArrays() {
    final int[] mockArr = {1, 2, 3, 4, 5, 6, 7};
    final int[][] result = CollectionUtil.divideArray(mockArr, 3);

    assertEquals(3, result.length);
    assertArrayEquals(new int[]{1, 2, 3}, result[0]);
    assertArrayEquals(new int[]{4, 5, 6}, result[1]);
    assertArrayEquals(new int[]{7}, result[2]);
  }
}
