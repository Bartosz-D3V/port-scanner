package service;

import domain.ScanResult;

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
import java.util.stream.IntStream;

public class IPScannerService {
  private final static Logger logger = Logger.getLogger(IPScannerService.class.getName());
  private final static int DIVIDER = 100;

  public int[] scanIP(final String ip) {
    final int[] ports = getPorts();
    final List<Future<List<ScanResult>>> scanResults = getOpenPorts(ip, ports);
    final List<Integer> openPorts = new ArrayList<>();
    for(Future<List<ScanResult>> future: scanResults) {
      try {
        openPorts.add(future.get().stream().filter(ScanResult::isOpen).map(scanResult -> scanResult.getPort()));
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {
        e.printStackTrace();
      }
    }
    System.out.print(openPorts.size());
  }

  private static int[] getPorts() {
    return IntStream.range(0, 65535).toArray();
  }

  private List<Future<List<ScanResult>>> getOpenPorts(final String ip, final int[] ports) {
    final ExecutorService executorService = Executors.newFixedThreadPool(DIVIDER);
    int[][] partialPorts = divideArray(ports, DIVIDER);
    final List<Future<List<ScanResult>>> openPorts = new ArrayList<>();

    for (int[] partialPortsSubArray : partialPorts) {
      openPorts.add(executorService.submit(() -> {
        final List<ScanResult> scanResults = new ArrayList<>();
        for (int port : partialPortsSubArray) {
          ScanResult scanResult = new ScanResult(port, false);
          try {
            final Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), 200);
            socket.close();
            scanResult.setOpen(true);
            logger.info("JEEEEEEEEEEEEEEEEEEEES");
          } catch (final IOException e) {
            logger.info("ScanResult: " + port + " is closed.");
          } finally {
            scanResults.add(scanResult);
          }
        }
        return scanResults;
      }));
    }
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
