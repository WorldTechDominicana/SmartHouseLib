package smarthouselib.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cameri
 * @since 6/13/13
 */
public class Device implements IModel
{
  private int id;
  private String deviceType;
  private int controllerId;
  private int zoneId;
  private String name;
  private String address;
  private DatabaseContext db;

  private boolean changed = false;
  private static String tableName = "devices";
  private static String[] sqlQueries = {
    "SELECT `id`, `device_type`, `controller_id`, `zone_id`, `name`, `address` FROM `zones` WHERE `id` = ?;",
    "INSERT INTO `zones` (`id`, `device_type`, `controller_id`, `zone_id`, `name`, `address`) VALUES (?, ?, ?, ?, ?, ?);",
    "UPDATE `zones` SET `device_type`= ?, `controller_id` = ?, `zone_id` = ?, `name` = ?, `address` = ? WHERE `id` = ?"
  };

  public Device(DatabaseContext db, int id, String deviceType, int controllerId, int zoneId, String name, String address)
  {
    this.db = db;
    this.id = id;
    this.deviceType = deviceType;
    this.controllerId = controllerId;
    this.zoneId = zoneId;
    this.name = name;
    this.address = address;
    setChanged(false);
  }

  public Device(DatabaseContext db)
  {
    this.db = db;
    this.id = 0;
    this.deviceType = "";
    this.controllerId = 0;
    this.zoneId = 0;
    this.name = "Unset";
    this.address = "";
    setChanged(false);
  }

  @Override
  public boolean load(int id)
  {
    Connection _conn = db.getConnection();
    PreparedStatement stmt;
    boolean found = false;
    try
    {
      stmt = _conn.prepareStatement(sqlQueries[0]);
      stmt.setInt(1, id);

      ResultSet rs = stmt.executeQuery();

      if (rs.next())
      {
        setId(rs.getInt(1));
        setDeviceType(rs.getString(2));
        setControllerId(rs.getInt(3));
        setZoneId(rs.getInt(4));
        setName(rs.getString(5));
        setAddress(rs.getString(6));
        setChanged(false);
        found = true;
      }
      rs.close();
      stmt.close();

    } catch (SQLException ex)
    {
      ex.printStackTrace();
    }

    return found;
  }

  @Override
  public boolean save()
  {
    if (!this.isChanged())
      return true;

    if (this.getId() == 0)
      return this.insert();
    else
      return this.update();
  }

  private boolean insert()
  {
    try
    {
      Connection _conn = db.getConnection();
      PreparedStatement stmt;
// "INSERT INTO `zones` (`id`, `device_type`, `controller_id`, `zone_id`, `name`, `address`) VALUES (?, ?, ?, ?, ?, ?);",
      stmt = _conn.prepareStatement(sqlQueries[1]); // insert
      stmt.setInt(1, 0);
      stmt.setString(2, this.getDeviceType());
      stmt.setInt(3, this.getControllerId());
      stmt.setInt(4, this.getZoneId());
      stmt.setString(5, this.getName());
      stmt.setString(6, this.getAddress());

      int affectedRows = stmt.executeUpdate();

      if (affectedRows == 1)
      {
        ResultSet rs = stmt.getGeneratedKeys();

        if (rs.next())
        {
          this.setId(rs.getInt(1));
          setChanged(false);
        }
        rs.close();
      }
      stmt.close();
      return !isChanged();
    } catch (SQLException ex)
    {
      ex.printStackTrace();
    }
    return false;
  }

  private boolean update()
  {
    try
    {
      Connection _conn = db.getConnection();
      PreparedStatement stmt;

      stmt = _conn.prepareStatement(sqlQueries[2]); // update

      stmt.setString(1, this.getDeviceType());
      stmt.setInt(2, this.getControllerId());
      stmt.setInt(3, this.getZoneId());
      stmt.setString(4, this.getName());
      stmt.setString(5, this.getAddress());
      stmt.setInt(6, this.getId());

      int affectedRows = stmt.executeUpdate();

      if (affectedRows == 1)
      {
        setChanged(false);
      }
      stmt.close();
      return !isChanged();
    } catch (SQLException ex)
    {
      ex.printStackTrace();
    }
    return false;
  }

  public void setChanged(boolean changed)
  {
    this.changed = changed;
  }

  @Override
  public boolean isChanged()
  {
    return this.changed;
  }

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    if (this.id != 0 && id > 0)
    {
      this.id = id;
      setChanged(true);
    }
  }

  public String getDeviceType()
  {
    return deviceType;
  }

  public void setDeviceType(String deviceType)
  {
    if (this.deviceType != deviceType)
    {
      this.deviceType = deviceType;
      setChanged(true);
    }
  }

  public int getControllerId()
  {
    return controllerId;
  }

  public void setControllerId(int controllerId)
  {
    this.controllerId = controllerId;
  }

  public int getZoneId()
  {
    return zoneId;
  }

  public void setZoneId(int zoneId)
  {
    if (this.zoneId != zoneId)
    {
      this.zoneId = zoneId;
      setChanged(true);
    }
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    if (this.name != name)
    {
      this.name = name;
      setChanged(true);
    }
  }

  public String getAddress()
  {
    return address;
  }

  public void setAddress(String address)
  {
    if (this.address != address)
    {
      this.address = address;
      setChanged(true);
    }
  }

  @Override
  public String toString()
  {
    return String.format("Device{name='%s', deviceType='%s', id=%d, address='%s', changed=%s}", name, deviceType, id, address, changed);
  }
}
