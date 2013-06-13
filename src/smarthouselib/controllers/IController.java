package smarthouselib.controllers;

import smarthouselib.controllers.drivers.IControllerDriver;

/**
 * @author cameri
 * @since 6/6/13
 */
public interface IController
{

  void initialize() throws Exception;

  IControllerDriver getDriver();

  void setDriver(IControllerDriver driver);

  ControllerState getState();

  void setState(ControllerState state);
}
