package smarthouselib.controllers;

import smarthouselib.controllers.drivers.IControllerDriver;
import smarthouselib.db.ControllerModel;
import smarthouselib.exceptions.InvalidControllerDriver;
import smarthouselib.exceptions.InvalidControllerState;

/**
 * @author cameri
 * @since 6/13/13
 */
public abstract class Controller extends ControllerModel implements IController
{
  ControllerState state = ControllerState.Uninitialized;
  IControllerDriver controller_driver;

  public Controller(int id, String controllerType, String controllerDriver, String name, String configuration)
  {
    super(id, controllerType, controllerDriver, name, configuration);
  }

  @Override
  public void initialize() throws InvalidControllerDriver, InvalidControllerState
  {
    if (getState() != ControllerState.Uninitialized)
      throw new InvalidControllerState("Controller is not uninitialized");

  }

  @Override
  public ControllerState getState()
  {
    return this.state;
  }

  @Override
  public void setState(ControllerState state)
  {
    this.state = state;
  }

  @Override
  public IControllerDriver getDriver()
  {
    return this.controller_driver;
  }

  @Override
  public void setDriver(IControllerDriver driver)
  {
    this.controller_driver = driver;
  }
}
