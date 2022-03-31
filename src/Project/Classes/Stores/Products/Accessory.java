package Project.Classes.Stores.Products;

public class Accessory extends Product {
    private String manufacturer;
    private String category;
    private Boolean gift;
    private String description;

    public Accessory(){

    }

    public Accessory(String name, Review review, double price, String manufacturer, String category, Boolean gift, String description) {
        super(name, review, price);
        this.manufacturer = manufacturer;
        this.category = category;
        this.gift = gift;
        this.description = description;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Accessory setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Accessory setCategory(String category) {
        this.category = category;
        return this;
    }

    public Boolean getGift() {
        return gift;
    }

    public Accessory setGift(Boolean gift) {
        this.gift = gift;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Accessory setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return "Accessory{" +
                "manufacturer='" + manufacturer + '\'' +
                ", category='" + category + '\'' +
                ", gift=" + gift +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", review=" + review +
                ", price=" + price +
                '}';
    }
}
