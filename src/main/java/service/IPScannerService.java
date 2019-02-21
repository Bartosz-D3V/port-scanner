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
    final int[] openPorts = getOpenPorts(ip, ports);
  }

  private static int[] getPorts() {
    return IntStream.range(0, 65535).toArray();
  }

  private int[] getOpenPorts(final String ip, final int[] ports) {
    final ExecutorService executorService = Executors.newFixedThreadPool(DIVIDER);
    final ConcurrentLinkedQueue<Integer> openPorts = new ConcurrentLinkedQueue<>();
    AtomicInteger arrayOffset = new AtomicInteger(0);
    IntStream
      .range(0, ports.length / DIVIDER)
      .forEach((i) -> executorService.submit(() -> {
        int[] partialPorts = divideArray(ports, arrayOffset.getAndIncrement());
        Arrays
          .stream(partialPorts)
          .forEach(port -> {
            System.out.println(port);
            try {
              final Socket socket = new Socket();
              socket.connect(new InetSocketAddress(ip, port));
              openPorts.add(port);
              socket.close();
            } catch (final IOException e) {
//            logger.info("Port: " + ip + " is closed.");
            }
          });
      }));

    return openPorts
      .stream()
      .mapToInt(Integer::intValue)
      .toArray();
  }

  private static int[] divideArray(final int[] array, final int offset) {
    return Arrays.copyOfRange(array, (offset * (array.length / (array.length / DIVIDER))), ((offset + 1) * DIVIDER));
  }
}
