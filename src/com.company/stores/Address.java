package com.company.stores;

import java.util.UUID;

public class Address {
    private final UUID id = UUID.randomUUID();
    private String city;
    private String street;
    private int number;
    private String county;

    public Address(){

    }

    public UUID getId() {
        return id;
    }

    public Address(String city, String street, int number, String county) {
        this.city = city;
        this.street = street;
        this.number = number;
        this.county = county;
    }

    public String getCity() {
        return city;
    }

    public Address setCity(String city) {
        this.city = city;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public Address setStreet(String street) {
        this.street = street;
        return this;
    }

    public int getNumber() {
        return number;
    }

    public Address setNumber(int number) {
        this.number = number;
        return this;
    }

    public String getCounty() {
        return county;
    }

    public Address setCounty(String county) {
        this.county = county;
        return this;
    }

    @Override
    public String toString() {
        return "Adress{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", number=" + number +
                ", county='" + county + '\'' +
                '}';
    }
}
