/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

/**
 *
 * @author estudiante
 */
public class Electrodomestico extends Product implements ProductUtils {
    private String modelo;
    private String marca;
    private static int QuantityStock;

    public Electrodomestico( int id, String name, int Price, String Offer,String modelo, String marca) {
        super(id, name, Price, Offer);
        this.modelo = modelo;
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
