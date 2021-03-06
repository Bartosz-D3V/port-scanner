import scanner.IPScanner;
import util.FormatUtil;
import util.IPUtil;

import java.util.Scanner;
import java.util.stream.IntStream;

public final class Main {
  public static void main(final String[] args) {
    final Scanner scanner = new Scanner(System.in);
    System.out.print("IP address to scan: ");
    String ip = scanner.next();
    while (!IPUtil.validateIP(ip)) {
      System.out.print("Invalid IP address. Re-type: ");
      ip = scanner.next();
    }

    final long startTime = System.currentTimeMillis();
    final String openIPs = FormatUtil.formatList(new IPScanner().scanIP(ip, IntStream.range(0, 65535).toArray()));
    final long endTime = System.currentTimeMillis();
    System.out.println(openIPs);
    System.out.println("Executed in: " + (endTime - startTime) / 60000 + " minutes");
  }
}
