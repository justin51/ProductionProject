import java.util.Date;

public class ProductionRecord {

    private int productionNumber;
    private int productId;
    String serialNumber;
    Date dateProduced;

    ProductionRecord(int productId) {
        this.productId = productId;
        this.productionNumber = 0;
        this.serialNumber = "0";
        this.dateProduced = new Date();
    }

    ProductionRecord(int productionNumber, int productId, String serialNumber, Date dateProduced) {
        this.productionNumber = productionNumber;
        this.productId = productId;
        this.serialNumber = serialNumber;
        this.dateProduced = dateProduced;
    }

    ProductionRecord(Product product, int count) {
        this.productId = product.getId();
        this.productionNumber = count;
        this.serialNumber = String.format("%s%s%05d", product.getManufacturer().substring(0, 3), product.getType(), count);
        this.dateProduced = new Date();
    }

    @Override
    public String toString() {
        return "Prod. Num: "+this.productionNumber+" Product ID: "+this.productId+" Serial Num: "+this.serialNumber+" Date: "+this.dateProduced;
    }

    public int getProductionNum() {
        return productionNumber;
    }

    public void setProductionNum(int productionNumber) {
        this.productionNumber = productionNumber;
    }

    public int getProductID() {
        return productId;
    }

    public void setProductID(int productId) {
        this.productId = productId;
    }

    public String getSerialNum() {
        return serialNumber;
    }

    public void setSerialNum(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getProdDate() {
        return dateProduced;
    }

    public void setProdDate(Date dateProduced) {
        this.dateProduced = dateProduced;
    }
}
