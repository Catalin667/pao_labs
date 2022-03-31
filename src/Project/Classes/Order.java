package Project.Classes;

import Project.Classes.Users.Costumer;

import java.util.ArrayList;
import java.util.UUID;

public class Order {
    private final UUID id;
    protected Costumer costumer;
    protected String paymentMethod;
    protected Store store;
    protected double price;
    protected ArrayList<Product> products = new ArrayList<>();

    public Order(){
        this.id = UUID.randomUUID();
    }

    public Order(Costumer costumer, String paymentMethod, Store store, double price, ArrayList<Product> products) {
        this.costumer = costumer;
        this.paymentMethod = paymentMethod;
        this.store = store;
        this.price = price;
        this.products = products;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public Costumer getCostumer() {
        return costumer;
    }

    public Order setCostumer(Costumer costumer) {
        this.costumer = costumer;
        return this;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public Order setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public Store getStore() {
        return store;
    }

    public Order setStore(Store store) {
        this.store = store;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Order setPrice(double price) {
        this.price = price;
        return this;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public Order setProducts(ArrayList<Product> products) {
        this.products = products;
        return this;
    }

    @Override
    public String toString() {
        return "Order{" +
                "costumer=" + costumer +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", store=" + store.toString() +
                ", price=" + price +
                ", products=" + products.toString() +
                '}';
    }
}
