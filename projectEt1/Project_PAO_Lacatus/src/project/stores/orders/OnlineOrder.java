package project.stores.orders;

import project.stores.Adress;
import project.stores.products.Product;
import project.stores.Store;
import project.stores.Voucher;
import project.users.Costumer;

import java.util.ArrayList;

public class OnlineOrder extends Order {
    private Adress delivaryAdress;
    private  Voucher Voucher;
    public OnlineOrder(){

    }

    public OnlineOrder(Costumer costumer, String paymentMethod, Store store, double price, ArrayList<Product> products, Adress delivaryAdress, project.stores.Voucher voucher) {
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

    public project.stores.Voucher getVoucher() {
        return Voucher;
    }

    public OnlineOrder setVoucher(project.stores.Voucher voucher) {
        Voucher = voucher;
        return this;
    }

    @Override
    public String toString() {
        return "OnlineOrder{" +
                "delivaryAdress=" + delivaryAdress +
                ", Voucher=" + Voucher.toString() +
                ", costumer=" + getCostumer().toString() +
                ", paymentMethod='" + getPaymentMethod() + '\'' +
                ", store=" + getStore().toString() +
                ", price=" + getPrice() +
                '}';
    }
}
