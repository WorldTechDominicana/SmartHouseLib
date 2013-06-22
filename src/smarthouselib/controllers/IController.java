package smarthouselib.controllers;

/**
 * @author cameri
 * @since 6/6/13
 */
public interface IController
{

  void initialize() throws Exception;

  ControllerState getState();

  void setState(ControllerState state);
}
