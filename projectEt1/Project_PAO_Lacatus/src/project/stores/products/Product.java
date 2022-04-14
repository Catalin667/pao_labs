package project.stores.products;

import java.util.UUID;

public abstract class Product {
    private static int productNumber = 0;
    private final UUID id;
    private String name;
    private Review review;
    private double price;
    private String manufacturer;
    private String category;
    private  String description;

    public Product(){
        this.id = UUID.randomUUID();
        productNumber ++;
    }

    public Product(String name, Review review, double price, String manufacturer, String category, String description) {
        this.id = UUID.randomUUID();
        productNumber ++;
        this.name = name;
        this.review = review;
        this.price = price;
        this.manufacturer = manufacturer;
        this.category = category;
        this.description = description;
    }

    public static int getProductNumber() {
        return productNumber;
    }

    public static void setProductNumber(int productNumber) {  Product.productNumber = productNumber;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public Review getReview() { return review;
    }

    public Product setReview(Review review) {
        this.review = review;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Product setPrice(double price) {
        this.price = price;
        return this;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Product setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Product setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return "Product{" +
                ", name='" + name + '\'' +
                ", review=" + review +
                ", price=" + price +
                ", manufacturer='" + manufacturer + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

