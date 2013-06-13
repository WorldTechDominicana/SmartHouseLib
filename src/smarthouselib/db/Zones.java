package smarthouselib.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author cameri
 * @since 6/13/13
 */
public class Zones
{
  protected Zones()
  {

  }

  private static String sqlQueries[] = {
    "SELECT `id`, `name`, `floor` FROM `zones`;"
  };

  public static ZoneModel getById(int id)
  {
    ZoneModel zone = new ZoneModel();
    if (zone.load(id))
      return zone;
    return null;
  }

  public static ZoneModel[] getAll()
  {
    ArrayList<ZoneModel> zoneModels = new ArrayList<ZoneModel>();
    Connection _conn = Database.getInstance().getConnection();
    PreparedStatement stmt;

    try
    {
      stmt = _conn.prepareStatement(sqlQueries[0]);
      ResultSet rs = stmt.executeQuery();

      while (rs.next())
      {
        ZoneModel zm = new ZoneModel();

        zm.setId(rs.getInt(1));
        zm.setName(rs.getString(2));
        zm.setFloor(rs.getInt(3));
        zm.setChanged(false);

        zoneModels.add(zm);
      }

      rs.close();
      stmt.close();

    } catch (SQLException ex)
    {
      ex.printStackTrace();
    }

    return (ZoneModel[]) zoneModels.toArray();
  }
}
