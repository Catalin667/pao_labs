package com.company.stores.products;

import com.company.users.Customer;

import java.util.UUID;

public class Review {
    private final UUID id;
    private Customer customer;
    private String mesaje;
    private int numberStars;

    public Review (){
        this.id = UUID.randomUUID();
    }

    public Review(Customer customer, String mesaje, int numberStars) {
        this.customer = customer;
        this.mesaje = mesaje;
        this.numberStars = numberStars;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public Customer getCostumer() {
        return customer;
    }

    public Review setCostumer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public String getMesaje() {
        return mesaje;
    }

    public Review setMesaje(String mesaje) {
        this.mesaje = mesaje;
        return this;
    }

    public int getNumberStars() {
        return numberStars;
    }

    public Review setNumberStars(int numberStars) {
        this.numberStars = numberStars;
        return this;
    }

    @Override
    public String toString() {
        return "Review{" +
                "costumer=" + customer.toString() +
                ", mesaje='" + mesaje + '\'' +
                ", numberStars=" + numberStars +
                '}';
    }
}
