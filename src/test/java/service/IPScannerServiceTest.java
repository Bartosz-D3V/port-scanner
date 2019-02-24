package service;

import domain.ScanResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

class IPScannerServiceTest {
  private final static String IP = "127.0.0.1";

  @Spy
  private IPScannerService scannerService;

  @Mock
  private Socket socket;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @DisplayName("connect should return true if connection is successful")
  void connectShouldReturnTrueIfConnectedSuccessfully() {
    assertTrue(scannerService.connect(socket, IP, 2));
  }

  @Test
  @DisplayName("connect should return false if connection was refused")
  void connectShouldReturnTrueIfConnectedUnsuccessfully() throws IOException {
    doThrow(new SocketException("Socket is closed")).when(socket).connect(any(InetSocketAddress.class), anyInt());

    assertFalse(scannerService.connect(socket, IP, 2));
  }

  @Test
  @DisplayName("getOpenPortsSync should return array of open ports")
  void getOpenPortsSyncShouldReturnArrayOfOpenPorts() {
    doReturn(true).when(scannerService).connect(any(Socket.class), eq(IP), eq(1000));
    doReturn(false).when(scannerService).connect(any(Socket.class), eq(IP), eq(2000));
    doReturn(false).when(scannerService).connect(any(Socket.class), eq(IP), eq(65000));
    doReturn(true).when(scannerService).connect(any(Socket.class), eq(IP), eq(65500));

    final int[] ports = {1000, 2000, 65000, 65500};
    final List<ScanResult> results = scannerService.getOpenPortsSync(IP, ports);

    assertEquals(1000, results.get(0).getPort());
    assertTrue(results.get(0).isOpen());

    assertEquals(2000, results.get(1).getPort());
    assertFalse(results.get(1).isOpen());

    assertEquals(65000, results.get(2).getPort());
    assertFalse(results.get(2).isOpen());

    assertEquals(65500, results.get(3).getPort());
    assertTrue(results.get(3).isOpen());
  }
}
