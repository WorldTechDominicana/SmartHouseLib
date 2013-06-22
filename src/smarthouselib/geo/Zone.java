package smarthouselib.geo;

import smarthouselib.db.DatabaseContext;

/**
 * @author cameri
 * @since 6/7/13
 */
public class Zone extends smarthouselib.db.Zone
{

  public Zone(DatabaseContext db, int id, String name, int floor)
  {
    super(db, id, name, floor);
  }

}
