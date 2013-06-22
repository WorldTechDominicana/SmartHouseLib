package smarthouselib.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cameri
 * @since 6/9/13
 */
public class Controller implements IModel
{
  private int id;
  private String controllerType;
  private String name;
  private String configuration;
  private boolean changed = false;
  private DatabaseContext db;
  private static String tableName = "controllers";
  private static String[] sqlQueries = {
    "SELECT `id`, `controller_type`, `name`, `configuration` FROM `controllers` WHERE `id` = ?;",
    "INSERT INTO `controllers` (`id`, `controller_type`, `name`, `configuration`) VALUES (?, ?, ?, ?);",
    "UPDATE `controllers` SET `controller_type` = ?, `name` = ?, `configuration` = ? WHERE `id` = ?;"
  };

  public Controller(DatabaseContext db, int id, String controllerType, String name, String configuration)
  {
    this.db = db;
    this.id = id;
    this.controllerType = controllerType;
    this.name = name;
    this.configuration = configuration;
    setChanged(false);
  }

  public Controller(DatabaseContext db)
  {
    this.db = db;
    this.id = 0;
    this.controllerType = "";
    this.name = "Unset";
    this.configuration = "";
    setChanged(false);
  }

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    if (this.id == 0 && id > 0)
    {
      this.id = id;
      setChanged(true);
    }
  }

  public String getControllerType()
  {
    return controllerType;
  }

  public void setControllerType(String controllerType)
  {
    if (this.controllerType != controllerType)
    {
      this.controllerType = controllerType;
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

  @Override
  public boolean load(int Id)
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
        int i = 1;
        setId(rs.getInt(i++));
        setControllerType(rs.getString(i++));
        setName(rs.getString(i++));
        setConfiguration(rs.getString(i++));
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

      stmt = _conn.prepareStatement(sqlQueries[1]); // insert
      int i = 1;
      stmt.setInt(i++, 0);
      stmt.setString(i++, this.getControllerType());
      stmt.setString(i++, this.getName());
      stmt.setString(i++, this.getConfiguration());

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
      int i = 1;
      stmt.setString(i++, this.getControllerType());
      stmt.setString(i++, this.getName());
      stmt.setString(i++, this.getConfiguration());
      stmt.setInt(i++, this.getId());

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

  public String getConfiguration()
  {
    return configuration;
  }

  public void setConfiguration(String configuration)
  {
    if (this.configuration != configuration)
    {
      this.configuration = configuration;
      setChanged(true);
    }
  }

  @Override
  public String toString()
  {
    return String.format("Controller{id=%d, controllerType='%s', name='%s', changed=%s}", id, controllerType, name, changed);
  }
}
