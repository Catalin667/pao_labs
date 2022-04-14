package com.company.stores;

public class Adress {
    private String city;
    private String street;
    private int number;
    private String county;

    public Adress(){

    }

    public Adress(String city, String street, int number, String county) {
        this.city = city;
        this.street = street;
        this.number = number;
        this.county = county;
    }

    public String getCity() {
        return city;
    }

    public Adress setCity(String city) {
        this.city = city;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public Adress setStreet(String street) {
        this.street = street;
        return this;
    }

    public int getNumber() {
        return number;
    }

    public Adress setNumber(int number) {
        this.number = number;
        return this;
    }

    public String getCounty() {
        return county;
    }

    public Adress setCounty(String county) {
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
