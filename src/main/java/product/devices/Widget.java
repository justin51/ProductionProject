package product.devices;

import product.ItemType;
import product.Product;

/**
 * @author Justin Kenney
 */
public class Widget extends Product {

  /**
   * @param name         name of product
   * @param manufacturer name of manufacturer of product
   * @param type         the type of product
   * @param id           the products id
   */
  public Widget(String name, String manufacturer, ItemType type, int id) {
    super(name, manufacturer, type, id);
  }

  /**
   * @param name         name of the product
   * @param manufacturer name of the manufacturer of the product
   * @param type         type of the product
   */
  public Widget(String name, String manufacturer, ItemType type) {
    super(name, manufacturer, type, -1);
  }
}
