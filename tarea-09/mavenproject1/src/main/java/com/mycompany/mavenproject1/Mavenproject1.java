/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.mavenproject1;

/**
 *
 * @author estudiante
 */
public class Mavenproject1 {

    public static void main(String[] args) {
        Client cliente = new Client(1,"Facu","Heredia");
        ShoppingList compra= new ShoppingList(cliente);
        Product adidas = new Zapatos(1,"Adidas Supra",15000,"40",38,"Adidas");
        Product Zelda= new Juego(2,"Ocarina of time",34000,"30",2002,"Nintndo 3ds","Nintendo");
        Product lavadora = new Electrodomestico(3,"Lavadora",340000,"20","2003","Oster");
        Product camisa = new Ropa(4,"Camisa",23000,"20",38,"Adidas");
        Product camisa2 = new Ropa(5,"Camisa",23000,"20",38,"Adidas");
        compra.buyProduct(Zelda, 1);
        compra.buyProduct(adidas, 1);
        compra.buyProduct(lavadora, 2);
        compra.buyProduct(camisa, 2);
        System.out.println(compra.countProduct());
        compra.displayList();
        compra.displayClientInfo();
        System.out.println(compra.totalPurchase());
    }
}
