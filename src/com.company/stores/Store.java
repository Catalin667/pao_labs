package com.company.stores;
import java.util.*;
import  java.util.UUID;

import com.company.stores.products.Product;

public class Store {
    private final UUID id;
    private String name;
    private Address address;
    private int numberProducts;
    private String status = ""; ///Open close;
    private ArrayList<Product> products = new ArrayList<Product>();
    private ArrayList<Map<Integer, Integer>> program = new ArrayList<Map<Integer, Integer>>(8);

    public Store(){
        this.id = UUID.randomUUID();
    }

    public Store(String name, Address address, int numberProducts, String status, ArrayList<Map<Integer, Integer>> program, ArrayList<Product> products) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.address = address;
        this.numberProducts = numberProducts;
        this.status = status;
        this.program = program;
        this.products = products;
    }

    public UUID getId() {
        return id;
    }

    public String getName() { return name;
    }

    public Store setName(String name) {
        this.name = name;
        return this;
    }

    public Address getAdress() {
        return address;
    }

    public Store setAdress(Address address) {
        this.address = address;
        return this;
    }

    public int getNumberProducts() {
        return numberProducts;
    }

    public Store setNumberProducts(int numberProducts) {
        this.numberProducts = numberProducts;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Store setStatus(String status) {
        this.status = status;
        return this;
    }

    public ArrayList<Map<Integer, Integer>> getProgram() {
        return program;
    }

    public Store setProgram(ArrayList<Map<Integer, Integer>> program) {
        this.program = program;
        return this;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public Store setProducts(ArrayList<Product> products) {
        this.products = products;
        return this;
    }

    public Store(Store store){
        this.id = store.id;
        this.name = store.name;
        this.address = store.address;
        this.numberProducts = store.numberProducts;
        this.status = store.status;
        this.program = store.program;
        this.products = store.products;
    }

    @Override
    public String toString() {
        return "Store{" +
                "name='" + name + '\'' +
                ", adress=" + address.toString() +
                ", numberProducts=" + numberProducts +
                ", status='" + status + '\'' +
                ", program=" + program +
                ", products=" + products.toString() +
                '}';
    }
}
