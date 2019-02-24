package util;

import java.util.Arrays;

public class CollectionUtil {
  public static int[][] divideArray(final int[] array, final int size) {
    final int[][] smallArrays = new int[(array.length / size + 1)][size];
    for (int i = 0; i < array.length / size + 1; i++) {
      final int to = (size * i + size) > array.length ? array.length : (size * i + size);
      smallArrays[i] = Arrays.copyOfRange(array, size * i, to);
    }
    return smallArrays;
  }
}
