package com.company.stores.orders;

import com.company.stores.products.Product;
import com.company.stores.Store;
import com.company.users.Customer;

import java.util.ArrayList;
import java.util.UUID;

public class Order {
    private final UUID id;
    private static int orderNumber = 0;
    private Customer customer;
    private String paymentMethod;
    private Store store;
    private double price;
    private  ArrayList<Product> products = new ArrayList<>();

    public Order(){
        this.id = UUID.randomUUID();
        orderNumber++;
    }

    public Order(Customer customer, String paymentMethod, Store store, double price, ArrayList<Product> products) {
        this.id = UUID.randomUUID();
        this.customer = customer;
        this.paymentMethod = paymentMethod;
        this.store = store;
        this.price = price;
        this.products = products;
        orderNumber++;
    }

    public UUID getId() {
        return id;
    }

    public Customer getCostumer() {
        return customer;
    }

    public Order setCostumer(Customer customer) {
        this.customer = customer;
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

    public static int getOrderNumber() {
        return orderNumber;
    }

    @Override
    public String toString() {
        return "Order{" +
                "costumer=" + customer +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", store=" + store.toString() +
                ", price=" + price +
                ", products=" + products.toString() +
                '}';
    }
}
