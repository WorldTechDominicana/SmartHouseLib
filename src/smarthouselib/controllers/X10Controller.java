package smarthouselib.controllers;

import smarthouselib.controllers.drivers.IControllerDriver;
import smarthouselib.controllers.drivers.MochadDriver;
import smarthouselib.devices.IDevice;
import smarthouselib.exceptions.InvalidControllerDriver;
import smarthouselib.exceptions.InvalidControllerState;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cameri
 * @since 6/6/13
 */
public class X10Controller extends Controller
{

  Map<String, IDevice> deviceMap = new HashMap<String, IDevice>();

  public X10Controller(int id, String controllerType, String controllerDriver, String name, String configuration)
  {
    super(id, controllerType, controllerDriver, name, configuration);
  }

  @Override
  public void initialize() throws InvalidControllerDriver, InvalidControllerState
  {
    super.initialize();

    setState(ControllerState.Initializing);
    String cdriver = this.getControllerDriver();
    if (cdriver == "MochadDriver")
    {
      setDriver(MochadDriver.getInstance());
      setState(ControllerState.Initialized);
    } else
    {
      setState(ControllerState.Failed);
      throw new InvalidControllerDriver();
    }
  }

  public boolean write(String command)
  {
    IControllerDriver _driver = this.getDriver();
    if (_driver != null)
    {
      return _driver.write(command);
    }
    return false;
  }
}
