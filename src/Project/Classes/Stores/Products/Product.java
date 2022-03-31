package Project.Classes.Stores.Products;

import java.util.UUID;

public abstract class Product {
    private static int productNumber;
    protected final UUID id;
    protected String name;
    protected Review review;
    protected double price;
    public Product(){
        this.id = UUID.randomUUID();
        productNumber ++;
    }

    public Product(String name, Review review, double price) {
        this.name = name;
        this.review = review;
        this.price = price;
        productNumber ++;
        this.id = UUID.randomUUID();
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

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", review=" + review +
                ", price=" + price +
                '}';
    }
}
