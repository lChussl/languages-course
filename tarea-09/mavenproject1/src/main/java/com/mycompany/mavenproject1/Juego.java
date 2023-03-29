/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

/**
 *
 * @author estudiante
 */
public class Juego extends Product implements ProductUtils {
    private int año;
    private String consola;
    private String distribuidora;
    private static int QuantityStock;

    public Juego(int id, String name, int Price, String Offer,int año, String consola, String distribuidora) {
        super(id, name, Price, Offer);
        this.año = año;
        this.consola = consola;
        this.distribuidora = distribuidora;
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
