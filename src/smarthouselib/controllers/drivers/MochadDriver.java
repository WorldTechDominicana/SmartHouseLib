package smarthouselib.controllers.drivers;

import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author cameri
 * @since 6/7/13
 */
public class MochadDriver implements IControllerDriver
{
  ControllerDriverState state = ControllerDriverState.Uninitialized;
  static MochadDriver instance;
  String address = "localhost";
  int port = 1099;

  protected MochadDriver()
  {
    /* exists only to disallow instantiation */
  }

  @Override
  public void initialize()
  {
    setState(ControllerDriverState.Initialized);
  }

  public static MochadDriver getInstance()
  {
    if (instance == null)
    {
      instance = new MochadDriver();
    }
    return instance;
  }

  public void setAddress(String newAddress)
  {
    this.address = newAddress;
  }

  public String getAddress()
  {
    return this.address;
  }

  public void setPort(int newPort)
  {
    this.port = newPort;
  }

  public int getPort()
  {
    return this.port;
  }

  public synchronized boolean write(String command)
  {
    boolean successful = false;
    try
    {
      Socket socket = new Socket(this.address, this.port);
      PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
      writer.write(command);
      successful = true;
      writer.close();
      socket.close();
    } catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return successful;

  }

  @Override
  public ControllerDriverState getState()
  {
    return this.state;
  }

  @Override
  public void setState(ControllerDriverState state)
  {
    this.state = state;
  }

  @Override
  public String toString()
  {
    return String.format("MochadDriver{address='%s', port=%d}", address, port);
  }
}