package Project.Classes;

public class Instrument extends Product{
    private int yearCreated;
    private int warranty;
    private String manufacturer;
    private String category;
    private String description;

    public Instrument(){

    }

    public Instrument(String name, Review review, double price, int yearCreated, int warranty, String manufacturer, String category, String description) {
        super(name, review,price);
        this.yearCreated = yearCreated;
        this.warranty = warranty;
        this.manufacturer = manufacturer;
        this.category = category;
        this.description = description;
    }

    public int getYearCreated() {
        return yearCreated;
    }

    public Instrument setYearCreated(int yearCreated) {
        this.yearCreated = yearCreated;
        return this;
    }

    public int getWarranty() {
        return warranty;
    }

    public Instrument setWarranty(int warranty) {
        this.warranty = warranty;
        return this;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Instrument setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Instrument setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Instrument setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "yearCreated=" + yearCreated +
                ", warranty=" + warranty +
                ", manufacturer='" + manufacturer + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", review=" + review +
                ", price=" + price +
                '}';
    }
}
