package util;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IPUtil {
  private final static Logger logger = Logger.getLogger(IPUtil.class.getName());

  public static boolean validateIP(final String ip) {
    final String[] groups = ip.split("\\.");
    if (groups.length != 4) return false;
    try {
      return Arrays
        .stream(groups)
        .map(Integer::parseInt)
        .filter(group -> (group >= 0 && group <= 255))
        .count() == 4;
    } catch (NumberFormatException ex) {
      logger.log(Level.WARNING, "Incorrect IP format.");
      return false;
    }
  }
}
