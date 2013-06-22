package smarthouselib.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cameri
 * @since 6/22/13
 */
public class Devices
{
  DatabaseContext db = null;

  private static String sqlQueries[] = {
    "SELECT `id`, `device_type`, `controller_id`, `zone_id`, `name`, `address` FROM `devices`;"
  };

  public Devices(DatabaseContext db)
  {
    this.db = db;
  }

  public Device getById(int id)
  {
    Device device = new Device(this.db);
    if (device.load(id))
      return device;
    return null;
  }

  public List<Device> getAll()
  {
    List<Device> devices = new ArrayList<Device>();
    Connection _conn = db.getConnection();
    PreparedStatement stmt;

    try
    {
      stmt = _conn.prepareStatement(sqlQueries[0]);
      ResultSet rs = stmt.executeQuery();

      while (rs.next())
      {
        Device device = new Device(this.db);
        int i = 1;
        device.setId(rs.getInt(i++));
        device.setDeviceType(rs.getString(i++));
        device.setControllerId(rs.getInt(i++));
        device.setZoneId(rs.getInt(i++));
        device.setName(rs.getString(i++));
        device.setAddress(rs.getString(i++));
        device.setChanged(false);
        devices.add(device);
      }

      rs.close();
      stmt.close();

    } catch (SQLException ex)
    {
      ex.printStackTrace();
    }

    return devices;
  }
}
