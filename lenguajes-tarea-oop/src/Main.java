import Taxones.*;
import Taxones.Class;

import java.sql.SQLOutput;

public class Main {
    public static void main(String[] args) {
        // Create objects and call the toString function
        Taxon kingdom = new Kingdom(1, "Plantae", "Haeckel", 1866, null);
        System.out.println(kingdom);

        Taxon divition = new Divition(2, "Magnoliophyta", "Scopoli", 1760, kingdom);
        System.out.println(divition);

        Taxon cClass = new Class(3, "Magnoliopsida", "Brongn", 1843, divition);
        System.out.println(cClass);

        Taxon order = new Order(4, "Fabales", "author", 1866, cClass, "Manual");
        System.out.println(order);

        Taxon family = new Family(5, "Fabaceae", "Lindl", 1866, order);
        System.out.println(family);

        Taxon genus = new Genus(6, "Enterolobium", "Friedrich", 1837, family);
        System.out.println(genus);

        Taxon species = new Species(7, "cyclocarpum", "Griseb", 1860, genus, "", "");
        System.out.println(species);

        // Trying to create and edit an object with a wrong family level
        // Taxon family2 = new Family(5, "Fabaceae", "Lindl", 1866, divition);

        // family.setTaxon_ancestor_id(divition);
        // family.setTaxon_ancestor_id(species);

        // Setting new values to objects to test set methods
        System.out.println(divition.getAuthor());
        divition.setAuthor("Test set method");
        System.out.println(divition.getAuthor());

        // The set method for scientific name also verify the name
        System.out.println(order.getScientificName());
        order.setScientificName("Test-ales");
        System.out.println(order.getScientificName());
    }
}