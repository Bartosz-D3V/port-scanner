import util.IPUtil;

import java.util.Scanner;

public final class Main {
  public static void main(final String[] args) {
    final Scanner scanner = new Scanner(System.in);
    System.out.print("IP address to scan: ");
    final String ip = scanner.next();
    IPUtil.validateIP(ip);
  }
}
