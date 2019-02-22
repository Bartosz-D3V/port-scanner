package domain;

public class ScanResult {
  private int port;
  private boolean open;

  public ScanResult(int port, boolean open) {
    this.port = port;
    this.open = open;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public boolean isOpen() {
    return open;
  }

  public void setOpen(boolean open) {
    this.open = open;
  }
}
