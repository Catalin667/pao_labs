package com.company.comparator;

import com.company.stores.products.Product;

import java.util.Comparator;

public class ProductComparator implements Comparator<Product> {
    public int compare(Product product1,Product product2){
        return Double.compare(product2.getPrice(), product1.getPrice());
    }
}
