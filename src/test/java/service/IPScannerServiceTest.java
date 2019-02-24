package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class IPScannerServiceTest {
  private final static String IP = "127.0.0.1";
  private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
  @Mock
  private Socket socket;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @DisplayName("connect should return true if connection is successful")
  void connectShouldReturnTrueIfConnectedSuccessfully() throws IOException {
    when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);

    assertTrue(IPScannerService.connect(socket, IP, 2));
  }

  @Test
  @DisplayName("connect should return false if connection was refused")
  void connectShouldReturnTrueIfConnectedUnsuccessfully() throws IOException {
    doThrow(new SocketException("Socket is closed")).when(socket).connect(any(InetSocketAddress.class), anyInt());

    assertFalse(IPScannerService.connect(socket, IP, 2));
  }
}
