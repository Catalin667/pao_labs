package com.company.stores.products;

public class Instrument extends Product {
    private int yearCreated;
    private int warranty;

    public Instrument(){

    }

    public Instrument( String name, Review review, double price, String manufacturer, String category, String description,int yearCreated,int warranty) {
        super(name,review,price,manufacturer,category,description);
        this.yearCreated = yearCreated;
        this.warranty = warranty;
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

    @Override
    public String toString() {
        return "Instrument{" +
                " name='" + getName() + '\'' +
                ", review=" + getReview() +
                ", price=" + getPrice() +
                ", manufacturer='" + getManufacturer() + '\'' +
                ", category='" + getCategory() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", yearCreated=" + yearCreated +
                ", warranty=" + warranty +
                '}';
    }
}
