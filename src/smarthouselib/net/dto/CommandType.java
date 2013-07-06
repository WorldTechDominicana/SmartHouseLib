package smarthouselib.net.dto;

/**
 * @author cameri
 * @since 6/27/13
 */
public enum CommandType
{
  Unknown(0), Ping(1), PingReply(2), Ack(3), Shutdown(4), Quit(5), Nack(6);

  private int code;

  private CommandType(int code)
  {
    this.code = code;
  }

  public int getCode()
  {
    return this.code;
  }

  @Override
  public String toString()
  {
    return String.format("%d", this.getCode());
  }
}
