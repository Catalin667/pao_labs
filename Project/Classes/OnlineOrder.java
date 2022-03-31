package Project.Classes;

import Project.Classes.Users.Costumer;

import java.util.ArrayList;

public class OnlineOrder extends Order {///????
    private Adress delivaryAdress;
    private Voucher Voucher;

    public OnlineOrder(){

    }
    public OnlineOrder(Costumer costumer, String paymentMethod, Store store, double price, ArrayList<Product> products, Adress delivaryAdress, Project.Classes.Voucher voucher) {
        super(costumer, paymentMethod, store, price,products);
        this.delivaryAdress = delivaryAdress;
        Voucher = voucher;
    }

    public Adress getDelivaryAdress() {
        return delivaryAdress;
    }

    public OnlineOrder setDelivaryAdress(Adress delivaryAdress) {
        this.delivaryAdress = delivaryAdress;
        return this;
    }

    public Project.Classes.Voucher getVoucher() {
        return Voucher;
    }

    public OnlineOrder setVoucher(Project.Classes.Voucher voucher) {
        Voucher = voucher;
        return this;
    }

    @Override
    public String toString() {
        return "OnlineOrder{" +
                "delivaryAdress=" + delivaryAdress +
                ", Voucher=" + Voucher.toString() +
                ", costumer=" + costumer.toString() +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", store=" + store.toString() +
                ", price=" + price +
                '}';
    }
}
