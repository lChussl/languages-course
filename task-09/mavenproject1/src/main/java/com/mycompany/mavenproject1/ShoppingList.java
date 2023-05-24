package com.mycompany.mavenproject1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javafx.util.Pair;

public class ShoppingList {
    
    private Map<Product, Integer> products = new HashMap<>();
    private Client client;

    public ShoppingList(Client client) {
        this.client = client;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void buyProduct(Product product, int n) {
        if(product.CheckStock(n)) {
            product.ChangeStock(n);
            products.put(product, n);
        } else {
            System.out.println("No hay suficiente stock para ese producto");
        }
    }
    
    public boolean empty() {
        return products.isEmpty();
    }
    
    public int countProduct() {
        Integer total = 0;
        for (Map.Entry<Product,Integer> entry : products.entrySet()) 
            total += entry.getValue();
        
        return total;
    }
    
    public int totalPurchase() {
        Integer total = 0;
        
        for (Map.Entry<Product,Integer> entry : products.entrySet()) 
            total += entry.getKey().getPrice() * entry.getValue();
        
        return total;
    }
    
    public void displayList() {
        for (Map.Entry<Product,Integer> entry : products.entrySet()) 
            System.out.println("Producto: " + entry.getKey().getName() + " Cantidad: " + entry.getValue());
    }
    
    public void displayClientInfo() {
        System.out.println(this.client.toString());
    }
}
