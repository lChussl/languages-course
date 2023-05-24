package com.example.code.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Date;

@Entity
@NoArgsConstructor
@Table(name = "order")
public class Order extends Class{
    private String collecting_method;

    /**
     * Constructor
     */
    public Order(String scientificName, String author, int publication_year, Taxon taxon_ancestor_id, String collecting_method) {
        super(scientificName, author, publication_year, taxon_ancestor_id);

        if(!(Arrays.asList("Manual", "Hand clippers", "Branch shears with extension").contains(collecting_method))) {
            throw new IllegalArgumentException("Collecting method must be 'Manual' or 'Hand clippers' or 'Branch shears with extension'");
        }

        this.collecting_method = collecting_method;
    }

    /**
     * Verify the scientific name
     */
    @Override
    public boolean verifyName(String name) {
        if(!(Character.isUpperCase(name.charAt(0)) && name.endsWith("ales"))){
            throw new IllegalArgumentException("Scientific name must start with upper case and end with 'ales'");
        }
        return true;
    }

    /**
     * Getter and setters
     */
    public String getCollecting_method() {
        return collecting_method;
    }

    public void setCollecting_method(String collecting_method) {
        if(!(Arrays.asList("Manual", "Tijeras podadoras de mano", "Tijeras cortarramas con extensión").contains(collecting_method))) {
            throw new IllegalArgumentException("Collecting method must be 'Manual' or 'Tijeras podadoras de mano' or 'Tijeras cortarramas con extensión'");
        }
        this.collecting_method = collecting_method;
    }

    /**
     * Check if the ancestor is valid
     */
    @Override
    public boolean checkAncestor(Taxon taxon) {
        if(!(taxon.getClass().getSimpleName().equals("Class"))) {
            throw new IllegalArgumentException("Ancestor must be of type Class");
        }
        return true;
    }
}
