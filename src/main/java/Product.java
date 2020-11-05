public abstract class Product implements Item {

    private int id;
    private ItemType type;
    private String manufacturer;
    private String name;

    Product(String name, String manufacturer, ItemType type) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Name: "+this.getName()+"\nManufacturer: "+this.getManufacturer()+"\nType: "+this.type;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setManufacturer(String name) {
        this.manufacturer = name;
    }

    @Override
    public String getManufacturer() {
        return this.manufacturer;
    }

    public ItemType getType() { return this.type; }
}
