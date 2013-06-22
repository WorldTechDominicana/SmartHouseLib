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

  public Zone[] getAll()
  {
    ArrayList<Zone> zones = new ArrayList<Zone>();
    Connection _conn = db.getConnection();
    PreparedStatement stmt;

    try
    {
      stmt = _conn.prepareStatement(sqlQueries[0]);
      ResultSet rs = stmt.executeQuery();

      while (rs.next())
      {
        Zone zm = new Zone(this.db);

        zm.setId(rs.getInt(1));
        zm.setName(rs.getString(2));
        zm.setFloor(rs.getInt(3));
        zm.setChanged(false);

        zones.add(zm);
      }

      rs.close();
      stmt.close();

    } catch (SQLException ex)
    {
      ex.printStackTrace();
    }

    return (Zone[]) zones.toArray();
  }
}
