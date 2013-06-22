package smarthouselib.devices.drivers;

import smarthouselib.controllers.IController;
import smarthouselib.devices.IDevice;

/**
 * @author cameri
 * @since 6/6/13
 */
public interface IDeviceDriver
{

  void initialize(IDevice device, IController controller);

  IController getController();

  IDevice getDevice();

  DeviceDriverState getState();

}
