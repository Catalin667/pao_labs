package com.company.stores.orders;

import com.company.stores.products.Product;
import com.company.stores.Address;
import com.company.stores.Store;
import com.company.stores.Voucher;
import com.company.users.Customer;

import java.util.ArrayList;

public class OnlineOrder extends Order {
    private Address delivaryAddress;
    private  Voucher Voucher;
    public OnlineOrder(){

    }

    public OnlineOrder(Customer customer, String paymentMethod, Store store, double price, ArrayList<Product> products, Address delivaryAddress, com.company.stores.Voucher voucher) {
        super(customer, paymentMethod, store, price,products);
        this.delivaryAddress = delivaryAddress;
        Voucher = voucher;
    }

    public Address getDelivaryAdress() {
        return delivaryAddress;
    }

    public OnlineOrder setDelivaryAdress(Address delivaryAddress) {
        this.delivaryAddress = delivaryAddress;
        return this;
    }

    public com.company.stores.Voucher getVoucher() {
        return Voucher;
    }

    public OnlineOrder setVoucher(com.company.stores.Voucher voucher) {
        Voucher = voucher;
        return this;
    }

    @Override
    public String toString() {
        return "OnlineOrder{" +
                "delivaryAdress=" + delivaryAddress +
                ", Voucher=" + Voucher.toString() +
                ", costumer=" + getCostumer().toString() +
                ", paymentMethod='" + getPaymentMethod() + '\'' +
                ", store=" + getStore().toString() +
                ", price=" + getPrice() +
                '}';
    }
}
