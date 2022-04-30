package com.company.stores.orders;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public final class Card {
    private static final UUID id = UUID.randomUUID();
    private  String  number;
    private   Date validThru;
    private  String type;
    private int authorisedSignature;

    public Card(){
    }

    public Card(String number, Date validThru, String type, int authorisedSignature) {
        this.number = number;
        this.validThru = validThru;
        this.type = type;
        this.authorisedSignature = authorisedSignature;
    }

    public String getNumber() {
        return number;
    }

    public Card setNumber(String number) {
        this.number = number;
        return this;
    }

    public Date getValidThru() {
        return validThru;
    }

    public Card setValidThru(Date validThru) {
        this.validThru = validThru;
        return this;
    }

    public String getType() {
        return type;
    }

    public Card setType(String type) {
        this.type = type;
        return this;
    }

    public int getAuthorisedSignature() {
        return authorisedSignature;
    }

    public Card setAuthorisedSignature(int authorisedSignature) {
        this.authorisedSignature = authorisedSignature;
        return this;
    }

    public UUID getId(){return id;}

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String validThruDate = (new SimpleDateFormat()).format(validThru);
        String[] validThruDateString = validThruDate.split(",");
        return "Card{" +
                "number=" + number +
                ", validThru=" + validThruDateString[0] +
                ", type='" + type + '\'' +
                ", authorisedSignature=" + authorisedSignature +
                '}';
    }
}
