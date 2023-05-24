package com.example.code.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "family")
@NoArgsConstructor
public class Family extends Order {
    /**
     * Constructor
     */
    public Family(String scientificName, String author, int publication_year, Taxon taxon_ancestor_id) {
        super(scientificName, author, publication_year, taxon_ancestor_id, taxon_ancestor_id.getCollecting_method());
    }

    /**
     * Verify the scientific name
     */
    @Override
    public boolean verifyName(String name) {
        if(!(Character.isUpperCase(name.charAt(0)) && name.endsWith("eae"))){
            throw new IllegalArgumentException("Scientific name must start with upper case and end with 'eae'");
        }
        return true;
    }

    /**
     * Check if the ancestor is valid
     */
    @Override
    public boolean checkAncestor(Taxon taxon) {
        if(!(taxon.getClass().getSimpleName().equals("Order"))) {
            throw new IllegalArgumentException("Ancestor must be of type Order");
        }
        return true;
    }
}
