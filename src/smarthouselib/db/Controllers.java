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
public class Controllers
{
  DatabaseContext db = null;

  private static String sqlQueries[] = {
    "SELECT `id`, `controller_type`, `name`, `configuration` FROM `controllers`;"
  };

  public Controllers(DatabaseContext db)
  {
    this.db = db;
  }


  public Controller getById(int id)
  {
    Controller controller = new Controller(this.db);
    if (controller.load(id))
      return controller;
    return null;
  }

  public List<Controller> getAll()
  {
    ArrayList<Controller> controllers = new ArrayList<Controller>();
    Connection _conn = DatabaseContext.getInstance().getConnection();
    PreparedStatement stmt;

    try
    {
      stmt = _conn.prepareStatement(sqlQueries[0]);
      ResultSet rs = stmt.executeQuery();

      while (rs.next())
      {
        Controller controller = new Controller(this.db);
        int i = 1;
        controller.setId(rs.getInt(i++));
        controller.setControllerType(rs.getString(i++));
        controller.setName(rs.getString(i++));
        controller.setConfiguration(rs.getString(i++));
        controller.setChanged(false);
        controllers.add(controller);
      }

      rs.close();
      stmt.close();

    } catch (SQLException ex)
    {
      ex.printStackTrace();
    }

    return controllers;
  }
}
