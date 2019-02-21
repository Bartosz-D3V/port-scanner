package service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class IPScannerService {
  private final static Logger logger = Logger.getLogger(IPScannerService.class.getName());

  public void scanIP(final String ip) {
    final int[] ports = getPorts();
    final int[] openPorts = getOpenPorts(ports);
  }

  private int[] getPorts() {
    return IntStream.range(0, 65535).toArray();
  }

  private int[] getOpenPorts(final int[] ports) {
    final ExecutorService executorService = Executors.newFixedThreadPool(10);
    final Socket socket = new Socket();
    final int[] openPorts = {};
    executorService.submit(() -> {
      try {
        socket.connect(new InetSocketAddress(ip));
      } catch (final IOException e) {
        logger.info("Port: " + ip + " is closed.");
      }
    });
    return openPorts;
  }
}
