package com.mycompany.mavenproject1;

public abstract class Product implements ProductUtils{
    private int id;
    private String name;
    private int Price;
    private String Offer;

    public Product(int id, String name, int Price, String Offer) {
        this.id = id;
        this.name = name;
        this.Price = Price;
        this.Offer = Offer;
    }
    
        public Product(int id, String name, int Price) {
        this.id = id;
        this.name = name;
        this.Price = Price;
        this.Offer = "0";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int Price) {
        this.Price = Price;
    }

    public String getOffer() {
        return Offer;
    }

    public void setOffer(String Offer) {
        this.Offer = Offer;
    }
    
}
