/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

/**
 *
 * @author estudiante
 */
public class Zapatos extends Product implements ProductUtils{
    private int talla;
    private String marca;
    private static int QuantityStock;

    public Zapatos(int id, String name, int Price, String Offer,int talla, String marca) {
        super(id, name, Price, Offer);
        this.talla = talla;
        this.marca = marca;
        QuantityStock++;
    }
        public Zapatos(int id, String name, int Price,int talla, String marca) {
        super(id, name, Price);
        this.talla = talla;
        this.marca = marca;
        QuantityStock++;
    }
    
    static {
        QuantityStock = 0;
    }
    
    @Override
    public void ChangeStock(int n) {
        QuantityStock -= n;
    }
    
    @Override
    public boolean CheckStock(int n) {
        return QuantityStock >= n;
    }
    
}
