package smarthouselib.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cameri
 * @since 6/13/13
 */
public class Zones
{
  DatabaseContext db;

  public Zones(DatabaseContext db)
  {
    this.db = db;
  }

  private static String sqlQueries[] = {
    "SELECT `id`, `name`, `floor` FROM `zones`;"
  };

  public Zone getById(int id)
  {
    Zone zone = new Zone(this.db);
    if (zone.load(id))
      return zone;
    return null;
  }

  public List<Zone> getAll()
  {

    List<Zone> zones = new ArrayList<Zone>();

    Connection _conn = db.getConnection();
    PreparedStatement stmt;

    try
    {
      stmt = _conn.prepareStatement(sqlQueries[0]);
      ResultSet rs = stmt.executeQuery();

      while (rs.next())
      {
        Zone zm = new Zone(this.db);

        int i = 1;
        zm.setId(rs.getInt(i++));
        zm.setName(rs.getString(i++));
        zm.setFloor(rs.getInt(i++));
        zm.setChanged(false);
        zones.add(zm);
      }

      rs.close();
      stmt.close();

    } catch (SQLException ex)
    {
      ex.printStackTrace();
    }

    return zones;
  }
}
