package scanner;

import domain.ScanResult;
import util.CollectionUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

public class IPScanner {
  private final static Logger logger = Logger.getLogger(IPScanner.class.getName());
  private final static int PORTS_PER_THREAD = 20;

  public List<Integer> scanIP(final String ip, final int[] ports) {
    final List<Future<List<ScanResult>>> scanResults = getOpenPorts(ip, ports);
    final List<Integer> openPorts = new ArrayList<>();
    for (Future<List<ScanResult>> future : scanResults) {
      try {
        final List<ScanResult> partialScanResults = future.get();
        for (ScanResult scanResult : partialScanResults) {
          if (scanResult.isOpen()) {
            openPorts.add(scanResult.getPort());
          }
        }
      } catch (InterruptedException | ExecutionException e) {
        logger.warning(e.getMessage());
      }
    }
    return openPorts;
  }

  private List<Future<List<ScanResult>>> getOpenPorts(final String ip, final int[] ports) {
    final ExecutorService executorService = Executors.newFixedThreadPool(PORTS_PER_THREAD);
    final int[][] partialPorts = CollectionUtil.divideArray(ports, PORTS_PER_THREAD);
    final List<Future<List<ScanResult>>> openPorts = new ArrayList<>();

    Arrays
      .stream(partialPorts)
      .forEach(partialPort -> openPorts
        .add(executorService.submit(() -> getOpenPortsSync(ip, partialPort))));
    executorService.shutdown();

    return openPorts;
  }

  List<ScanResult> getOpenPortsSync(final String ip, final int ports[]) {
    final List<ScanResult> scanResults = new ArrayList<>();
    for (int port : ports) {
      final ScanResult scanResult = new ScanResult(port, false);
      scanResult.setOpen(connect(new Socket(), ip, port));
      scanResults.add(scanResult);
    }
    return scanResults;
  }

  boolean connect(final Socket socket, final String ip, final int port) {
    try {
      socket.connect(new InetSocketAddress(ip, port), 200);
      socket.close();
      return true;
    } catch (final IOException e) {
      logger.info("Port: " + port + " is closed.");
      return false;
    }
  }
}
