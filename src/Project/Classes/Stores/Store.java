package Project.Classes.Stores;
import java.util.*;
import  java.util.UUID;

import Project.Classes.Stores.Products.Product;
import javafx.util.Pair;

public class Store {
    private final UUID id;
    private String name;
    private Adress adress;
    private int numberProducts;
    private String status = ""; ///Open close;
    private ArrayList<Product> products = new ArrayList<Product>();
    private ArrayList<Pair<Integer, Integer>> program = new ArrayList<Pair<Integer, Integer>>(8);

    public Store(){
        this.id = UUID.randomUUID();
    }

    public Store( String name, Adress adress, int numberProducts, String status, ArrayList<Pair<Integer, Integer>> program, ArrayList<Product> products) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.adress = adress;
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

    public Adress getAdress() {
        return adress;
    }

    public Store setAdress(Adress adress) {
        this.adress = adress;
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

    public ArrayList<Pair<Integer, Integer>> getProgram() {
        return program;
    }

    public Store setProgram(ArrayList<Pair<Integer, Integer>> program) {
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
        this.adress = store.adress;
        this.numberProducts = store.numberProducts;
        this.status = store.status;
        this.program = store.program;
        this.products = store.products;
    }

    @Override
    public String toString() {
        return "Store{" +
                "name='" + name + '\'' +
                ", adress=" + adress.toString() +
                ", numberProducts=" + numberProducts +
                ", status='" + status + '\'' +
                ", program=" + program +
                ", products=" + products.toString() +
                '}';
    }
}
