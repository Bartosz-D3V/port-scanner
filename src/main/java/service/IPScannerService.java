package service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class IPScannerService {
  private final static Logger logger = Logger.getLogger(IPScannerService.class.getName());
  private final static int DIVIDER = 100;

  public void scanIP(final String ip) {
    final int[] ports = getPorts();
    final ConcurrentLinkedQueue<Integer> openPorts = getOpenPorts(ip, ports);
    System.out.print(openPorts.size());
  }

  private static int[] getPorts() {
    return IntStream.range(0, 65535).toArray();
  }

  private ConcurrentLinkedQueue<Integer> getOpenPorts(final String ip, final int[] ports) {
    final ExecutorService executorService = Executors.newFixedThreadPool(DIVIDER);
    final ConcurrentLinkedQueue<Integer> openPorts = new ConcurrentLinkedQueue<>();
    int[][] partialPorts = divideArray(ports, DIVIDER);
    AtomicInteger arrayOffset = new AtomicInteger(0);
    Arrays
      .stream(partialPorts)
      .forEach((i) -> executorService.submit(() ->
        Arrays
          .stream(partialPorts[arrayOffset.getAndIncrement()])
          .forEach(port -> {
            try {
              final Socket socket = new Socket();
              socket.connect(new InetSocketAddress(ip, port), 200);
              socket.close();
              openPorts.add(port);
              logger.info("JEEEEEEEEEEEEEEEEEEEES");
            } catch (final IOException e) {
              logger.info("Port: " + port + " is closed.");
            }
          })));
    executorService.shutdown();
    return openPorts;
  }

  private static int[][] divideArray(final int[] array, final int size) {
    final int[][] smallArrays = new int[(array.length / size) + 1][size];
    for (int i = 0; i < array.length / size; i++) {
      smallArrays[i] = Arrays.copyOfRange(array, size * i, size * i + size);
    }
    return smallArrays;
  }
}
