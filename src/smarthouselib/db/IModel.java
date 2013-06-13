package smarthouselib.db;

/**
 * @author cameri
 * @since 6/12/13
 */
public interface IModel
{
  boolean load(int Id);

  boolean save();

  boolean isChanged();
}
