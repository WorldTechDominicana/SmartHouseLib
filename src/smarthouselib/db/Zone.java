package smarthouselib.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cameri
 * @since 6/12/13
 */
public class Zone implements IModel
{
  private int id = 0;
  private String name;
  private int floor;
  DatabaseContext db;

  private boolean changed = false;
  private static String tableName = "zones";
  private static String[] sqlQueries = {
    "SELECT `id`, `name`, `floor` FROM `zones` WHERE `id` = ?;",
    "INSERT INTO `zones` (`id`, `name`, `floor`) VALUES (?, ?, ?);",
    "UPDATE `zones` SET `name`= ?, `floor`=? WHERE `id` = ?"
  };

  protected Zone(DatabaseContext db)
  {
    this.db = db;
    this.id = 0;
    this.name = "";
    this.floor = 0;
    this.changed = false;
  }

  public Zone(DatabaseContext db, int id, String name, int floor)
  {
    this.db = db;
    if (id == 0) // new Zone
    {
      setChanged(true);
    } else // existing Zone
    {
      this.setId(id);
    }
    this.setName(name);
    this.setFloor(floor);
  }

  public int getId()
  {
    return this.id;
  }

  public void setId(int newId)
  {
    if (this.id == 0 && newId > 0)
    {
      this.id = newId;
      setChanged(true);
    }
  }

  public int getFloor()
  {
    return this.floor;
  }

  public void setFloor(int newFloor)
  {
    if (newFloor != this.floor)
    {
      setChanged(true);
      this.floor = newFloor;
    }
  }

  public void setName(String newName)
  {
    if (newName != null && !newName.isEmpty() && newName != this.name)
    {
      setChanged(true);
      this.name = newName;
    }
  }

  public String getName()
  {
    return this.name;
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
        setName(rs.getString(2));
        setFloor(rs.getInt(3));
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
      Connection _conn = DatabaseContext.getInstance().getConnection();
      PreparedStatement stmt;

      stmt = _conn.prepareStatement(sqlQueries[1]); // insert
      stmt.setInt(1, 0);
      stmt.setString(2, this.getName());
      stmt.setInt(3, this.getFloor());

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
      Connection _conn = DatabaseContext.getInstance().getConnection();
      PreparedStatement stmt;

      stmt = _conn.prepareStatement(sqlQueries[2]); // update
      stmt.setString(1, this.getName());
      stmt.setInt(2, this.getFloor());
      stmt.setInt(3, this.getId());

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

  @Override
  public boolean isChanged()
  {
    return changed;
  }

  public void setChanged(boolean changed)
  {
    this.changed = changed;
  }

  @Override
  public String toString()
  {
    return String.format("Zone{id=%d, name='%s', floor=%d, changed=%s}", id, name, floor, changed);
  }
}
