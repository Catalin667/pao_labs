package com.company.users;

import com.company.stores.orders.Card;
import com.company.stores.Address;

import java.util.UUID;

public class Customer {
    private final UUID id;
    private String firstName;
    private String lastName;
    private Address address;
    private Card card;

    public Customer(){
        this.id = UUID.randomUUID();
    }
    public Customer(String firstName, String lastName, Address address, Card card) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.card = card;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Customer setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Customer setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Address getAdress() {
        return address;
    }

    public Customer setAdress(Address address) {
        this.address = address;
        return this;
    }

    public Card getCard() {
        return card;
    }

    public Customer setCard(Card card) {
        this.card = card;
        return this;
    }

    @Override
    public String toString() {
        return "Costumer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", adress=" + address +
                ", card=" + card +
                '}';
    }
}
