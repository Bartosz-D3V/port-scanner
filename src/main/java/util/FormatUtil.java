package util;

import java.util.List;

public class FormatUtil {
  public static String formatList(final List<Integer> integers) {
    if (integers.isEmpty()) {
      return "No open ports";
    }
    StringBuilder sb = new StringBuilder("Open ports: \n");
    for (Integer integer : integers) {
      sb
        .append(String.valueOf(integer))
        .append(integers.indexOf(integer) + 1 != integers.size() ? ", " : "");
    }
    return sb.toString();
  }
}
