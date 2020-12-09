package product;

/**
 * An interface used for Product.
 *
 * @author Justin Kenney
 */
public interface Item {

  int getId();

  void setName(String name);

  String getName();

  void setManufacturer(String name);

  String getManufacturer();
}
