import util.IPUtil;

import java.util.Scanner;

public final class Main {
  public static void main(final String[] args) {
    final Scanner scanner = new Scanner(System.in);
    System.out.print("IP address to scan: ");
    String ip = scanner.next();
    while (!IPUtil.validateIP(ip)) {
      System.out.print("Invalid IP address. Re-type: ");
      ip = scanner.next();
    }
  }
}
