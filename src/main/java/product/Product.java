package product;

/**
 * Class to store the information of a product.
 *
 * @author Justin Kenney
 */
public abstract class Product implements Item {

  /**
   * id of the product.
   */
  private int id;
  /**
   * type of the product.
   */
  private ItemType type;
  /**
   * name of the product manufacturer.
   */
  private String manufacturer;
  /**
   * name of the product
   */
  private String name;

  /**
   * Constructor for creating a Product object.
   *
   * @param name         product name
   * @param manufacturer product manudacturer
   * @param type         item type
   */
  public Product(String name, String manufacturer, ItemType type) {
    this.id = -1;
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = type;
  }

  /**
   * Constructor for creating a Product object.
   *
   * @param name         product name
   * @param manufacturer product manufacturer
   * @param type         item type
   * @param id           product id
   */
  public Product(String name, String manufacturer, ItemType type, int id) {
    this.id = id;
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = type;
  }

  /**
   * Creates a string made up of the products properties.
   *
   * @return product name, manufacturer, type
   */
  @Override
  public String toString() {
    return "Name: " + this.getName() + "\nManufacturer: " + this.getManufacturer() + "\nType: "
        + this.type;
  }

  /**
   * Gets the product id.
   *
   * @return product id
   */
  @Override
  public int getId() {
    return this.id;
  }

  /**
   * sets the product name.
   *
   * @param name the products name
   */
  @Override
  public void setName(String name) {
    this.name = name;
  }

  /**
   * gets the products name.
   *
   * @return products name
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * sets the manufacturers name.
   *
   * @param name manufacturers name
   */
  @Override
  public void setManufacturer(String name) {
    this.manufacturer = name;
  }

  /**
   * gets the manufacturers name.
   *
   * @return manufacturers name
   */
  @Override
  public String getManufacturer() {
    return this.manufacturer;
  }

  /**
   * get the item type.
   *
   * @return item type
   */
  public ItemType getType() {
    return this.type;
  }
}
