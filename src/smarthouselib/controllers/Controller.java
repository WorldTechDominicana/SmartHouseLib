package smarthouselib.controllers;

import smarthouselib.db.DatabaseContext;
import smarthouselib.exceptions.InvalidControllerState;

/**
 * @author cameri
 * @since 6/13/13
 */
public abstract class Controller extends smarthouselib.db.Controller implements IController
{
  ControllerState state = ControllerState.Uninitialized;

  public Controller(DatabaseContext db, int id, String controllerType, String name, String configuration)
  {
    super(db, id, controllerType, name, configuration);
    setState(ControllerState.Uninitialized);
  }

  @Override
  public void initialize() throws InvalidControllerState
  {
    if (getState() != ControllerState.Uninitialized)
      throw new InvalidControllerState("Controller already initialized");

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

}
