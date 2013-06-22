package smarthouselib.devices.drivers;

import smarthouselib.controllers.IController;
import smarthouselib.controllers.X10Controller;
import smarthouselib.devices.IDevice;

/**
 * @author cameri
 * @since 6/6/13
 */
public class X10DimmableLampDriver implements IDeviceDriver, IDimmableDriver
{

  IController controller;
  IDevice device;
  DeviceDriverState currentState = DeviceDriverState.Uninitialized;

  @Override
  public void initialize(IDevice device, IController controller)
  {
    this.controller = controller;

    this.currentState = DeviceDriverState.Initialized;
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
  public void setBrightness(int brightness)
  {

  }

  @Override
  public int getBrightness() throws UnsupportedOperationException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public void decreaseBrightness(int amount)
  {
    amount = Math.min(31, Math.max(0, amount));
    if (this.controller instanceof X10Controller)
    {
      String command = String.format("rf %s dim %d\n", this.getDevice().getAddress(), amount);
      ((X10Controller) this.controller).write(command);
    }
  }

  @Override
  public void increaseBrightness(int amount)
  {
    amount = Math.min(31, Math.max(0, amount));
    if (this.controller instanceof X10Controller)
    {
      String command = String.format("rf %s bright %d\n", this.getDevice().getAddress(), amount);
      ((X10Controller) this.controller).write(command);
    }
  }


  @Override
  public String toString()
  {
    return "X10DimmableLampDriver{" +
      "currentState=" + currentState +
      '}';
  }
}
