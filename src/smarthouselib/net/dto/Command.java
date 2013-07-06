package smarthouselib.net.dto;

import org.json.JSONObject;
import org.json.JSONWriter;

import java.io.StringWriter;

/**
 * @author cameri
 * @since 6/27/13
 */
public class Command
{
  private int length;
  private CommandType type = CommandType.Unknown;
  private String payload;

  public int getLength()
  {
    return this.getPayload().length();
  }

  public CommandType getType()
  {
    return type;
  }

  public void setType(CommandType type)
  {
    this.type = type;
  }

  public String getPayload()
  {
    return payload;
  }

  public void setPayload(String payload)
  {
    this.payload = payload;
  }

  public static String serialize(Command m)
  {


    StringWriter sw = new StringWriter();
    JSONWriter w = new JSONWriter(sw);
    w.object()
      .key("type")
      .value(m.getType())
      .key("length")
      .value(m.getLength())
      .key("payload")
      .value(m.getPayload())
      .endObject();


    return sw.toString();

  }

  public static Command deserialize(String s)
  {

    try
    {
      JSONObject jo = new JSONObject(s);
      Command c = new Command();
      c.setType(CommandType.values()[jo.getInt("type")]);
      c.setPayload(jo.getString("payload"));

      return c;
    } catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }


  @Override
  public String toString()
  {
    return String.format("Command{type=%s, payload='%s', length=%d}", type, payload, getLength());
  }

}
