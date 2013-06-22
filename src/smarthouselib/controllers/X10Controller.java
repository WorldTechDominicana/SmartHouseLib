package smarthouselib.controllers;

import smarthouselib.db.DatabaseContext;
import smarthouselib.devices.IDevice;
import smarthouselib.exceptions.InvalidControllerState;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cameri
 * @since 6/6/13
 */
public class X10Controller extends Controller
{

  Map<String, IDevice> deviceMap = new HashMap<String, IDevice>();
  private String address = "localhost";
  private int port = 1099;

  public X10Controller(DatabaseContext db, int id, String controllerType, String controllerDriver, String name, String configuration)
  {
    super(db, id, controllerType, name, configuration);
  }

  @Override
  public void initialize() throws InvalidControllerState
  {
    super.initialize();

    setState(ControllerState.Initializing);


    setState(ControllerState.Initialized);
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

}
