package com.company.stores.products;

public class Accessory extends Product {
    private Boolean gift;

    public Accessory(){

    }

    public Accessory(String name, Review review, double price, String manufacturer, String category, String description, Boolean gift) {
        super(name, review, price, manufacturer, category, description);
        this.gift = gift;
    }

    public Boolean getGift() {
        return gift;
    }

    public Accessory setGift(Boolean gift) {
        this.gift = gift;
        return this;
    }

    @Override
    public String toString() {
        return "Accessory{" +
                " name='" + getName() + '\'' +
                ", review=" + getReview() +
                ", price=" + getPrice() +
                ", manufacturer='" + getManufacturer() + '\'' +
                ", category='" + getCategory() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", gift=" + gift +
                '}';
    }
}


