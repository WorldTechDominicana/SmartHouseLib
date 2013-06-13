package smarthouselib.controllers.drivers;

/**
 * @author cameri
 * @since 6/6/13
 */
public interface IControllerDriver
{
  void initialize();

  boolean write(String command);

  ControllerDriverState getState();

  void setState(ControllerDriverState state);
}
