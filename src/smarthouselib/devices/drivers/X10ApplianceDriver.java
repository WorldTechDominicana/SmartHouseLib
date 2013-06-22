package smarthouselib.devices.drivers;

import smarthouselib.controllers.IController;
import smarthouselib.controllers.X10Controller;
import smarthouselib.devices.IDevice;

/**
 * @author cameri
 * @since 6/10/13
 */
public class X10ApplianceDriver implements IDeviceDriver, IToggleableDriver
{
  IController controller;
  IDevice device;
  DeviceDriverState currentState = DeviceDriverState.Uninitialized;

  @Override
  public void initialize(IDevice device, IController controller)
  {
    this.device = device;
    this.controller = controller;

    // Set state as initialized
    currentState = DeviceDriverState.Initialized;
  }

  @Override
  public IController getController()
  {
    return this.controller;
  }

  @Override
  public IDevice getDevice()
  {
    return this.device;
  }

  @Override
  public DeviceDriverState getState()
  {
    return this.currentState;
  }

  @Override
  public void turnOn()
  {
    if (this.controller instanceof X10Controller)
    {
      String command = String.format("rf %s on\n", this.getDevice().getAddress());
      ((X10Controller) this.controller).write(command);
    }
  }

  @Override
  public void turnOff()
  {
    if (this.controller instanceof X10Controller)
    {
      String command = String.format("rf %s off\n", this.getDevice().getAddress());
      ((X10Controller) this.controller).write(command);
    }
  }

  @Override
  public String toString()
  {
    return "X10ApplianceDriver{" +
      "currentState=" + currentState +
      '}';
  }
}
