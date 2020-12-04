import java.util.Date;
import product.Product;

/**
 * A class to hold the data related to a ProductionRecord
 *
 * @author Justin Kenney
 */
public class ProductionRecord {

  /**
   * the produced products production number
   */
  private int productionNumber;
  /**
   * the products id
   */
  private int productId;
  /**
   * the produced products serial number
   */
  private String serialNumber;
  /**
   * the date of production
   */
  private Date dateProduced;

  /**
   * Constructor for creating a ProductionRecord object using a products id
   *
   * @param productId product id
   */
  public ProductionRecord(int productId) {
    this.productId = productId;
    this.productionNumber = 0;
    this.serialNumber = "0";
    this.dateProduced = new Date();
  }

  /**
   * Constructor for creating a ProductionRecord object
   *
   * @param productionNumber products production number
   * @param productId        products id
   * @param serialNumber     products serial number
   * @param dateProduced     date of production
   */
  public ProductionRecord(int productionNumber, int productId, String serialNumber,
      Date dateProduced) {
    this.productionNumber = productionNumber;
    this.productId = productId;
    this.serialNumber = serialNumber;
    this.dateProduced = dateProduced;
  }

  /**
   * Constructor for creating a ProductionRecord object
   *
   * @param product the product that was produced
   * @param count   the amount produced
   */
  public ProductionRecord(Product product, int count) {
    this.productId = product.getId();
    this.productionNumber = count;
    this.serialNumber = String
        .format("%s%s%05d", product.getManufacturer().substring(0, 3), product.getType(), count);
    this.dateProduced = new Date();
  }

  /**
   * Converts the properties of the ProductionRecord into a string of text
   *
   * @return string of text representing the ProductionRecord
   */
  @Override
  public String toString() {
    return "Prod. Num: " + this.productionNumber + " product.Product ID: " + this.productId
        + " Serial Num: " + this.serialNumber + " Date: " + this.dateProduced;
  }

  /**
   * gets the production number
   *
   * @return production number
   */
  public int getProductionNum() {
    return productionNumber;
  }

  /**
   * sets the production number
   *
   * @param productionNumber how many units were produced before this
   */
  public void setProductionNum(int productionNumber) {
    this.productionNumber = productionNumber;
  }

  /**
   * gets the product id
   *
   * @return product id
   */
  public int getProductID() {
    return productId;
  }

  /**
   * sets the products id
   *
   * @param productId product id
   */
  public void setProductID(int productId) {
    this.productId = productId;
  }

  /**
   * gets the serial number
   *
   * @return product serial number
   */
  public String getSerialNum() {
    return serialNumber;
  }

  /**
   * sets the product serial number
   *
   * @param serialNumber product serial number
   */
  public void setSerialNum(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  /**
   * gets the production date
   *
   * @return production date
   */
  public Date getProdDate() {
    return dateProduced;
  }

  /**
   * Sets the production date
   *
   * @param dateProduced date of production
   */
  public void setProdDate(Date dateProduced) {
    this.dateProduced = new Date(dateProduced.getTime());
  }
}
