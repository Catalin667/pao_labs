package com.company.users;

import com.company.stores.orders.Card;
import com.company.stores.Adress;

import java.util.UUID;

public class Costumer {
    private final UUID id;
    private String firstName;
    private String lastName;
    private Adress adress;
    private Card card;

    public Costumer(){
        this.id = UUID.randomUUID();
    }
    public Costumer(String firstName, String lastName, Adress adress, Card card) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.adress = adress;
        this.card = card;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Costumer setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Costumer setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Adress getAdress() {
        return adress;
    }

    public Costumer setAdress(Adress adress) {
        this.adress = adress;
        return this;
    }

    public Card getCard() {
        return card;
    }

    public Costumer setCard(Card card) {
        this.card = card;
        return this;
    }

    @Override
    public String toString() {
        return "Costumer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", adress=" + adress +
                ", card=" + card +
                '}';
    }
}
