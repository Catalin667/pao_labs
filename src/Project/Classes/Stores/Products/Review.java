package Project.Classes.Stores.Products;

import Project.Classes.Users.Costumer;

import java.util.UUID;

public class Review {
    private final UUID id;
    private Costumer costumer;
    private String mesaje;
    private int numberStars;

    public Review (){
        this.id = UUID.randomUUID();
    }

    public Review(Costumer costumer, String mesaje, int numberStars) {
        this.costumer = costumer;
        this.mesaje = mesaje;
        this.numberStars = numberStars;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public Costumer getCostumer() {
        return costumer;
    }

    public Review setCostumer(Costumer costumer) {
        this.costumer = costumer;
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
                "costumer=" + costumer.toString() +
                ", mesaje='" + mesaje + '\'' +
                ", numberStars=" + numberStars +
                '}';
    }
}
