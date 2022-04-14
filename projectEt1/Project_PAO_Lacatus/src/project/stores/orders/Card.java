package project.stores.orders;

import java.util.Date;

public final class Card {
    private  long  number;
    private   Date validThru;
    private  String type;
    private int authorisedSignature;

    public Card(){
    }

    public Card(long number, Date validThru, String type, int authorisedSignature) {
        this.number = number;
        this.validThru = validThru;
        this.type = type;
        this.authorisedSignature = authorisedSignature;
    }

    public long getNumber() {
        return number;
    }

    public Card setNumber(long number) {
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

    @Override
    public String toString() {
        return "Card{" +
                "number=" + number +
                ", validThru=" + validThru +
                ", type='" + type + '\'' +
                ", authorisedSignature=" + authorisedSignature +
                '}';
    }
}
