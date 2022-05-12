package com.company.stores;
import java.util.*;
import  java.util.UUID;

import com.company.stores.products.Product;

public class Store {
    private final UUID id;
    private String name;
    private Address address;
    private int numberProducts;
    private String status = "closed"; ///Open/closed;
    private List<Product> products = new ArrayList<>();
    private List<Map<String, String>> program = new ArrayList<>(8);

    public Store(){
        this.id = UUID.randomUUID();
    }

    public Store(String name, Address address, int numberProducts, String status, List<Map<String, String>> program, List<Product> products) {
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

    public List<Map<String, String>> getProgram() {
        return program;
    }

    public Store setProgram(List<Map<String, String>> program) {
        this.program = program;
        return this;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Store setProducts(List<Product> products) {
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
        String afisare = "Store{";
        afisare += "name='" + name + '\'';
        if (address != null)
            afisare += ", adress=" + address.toString();
        else
            afisare += ", adress=" + address;
        afisare += ", numberProducts=" + numberProducts + ", status='" + status + '\'';
        if (program != null)
            afisare += ", program=" + program.toString();
        else
            afisare += ", program=" + program;
        if (products != null)
            afisare += ", products=" + products.toString();
        else
            afisare += ", products=" + products;
        afisare += '}';
        return afisare;
    }
}
