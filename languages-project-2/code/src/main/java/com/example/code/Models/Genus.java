package com.example.code.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "genus")
@NoArgsConstructor
public class Genus extends Family {
    /**
     * Constructor
     */
    public Genus(String scientificName, String author, int publication_year, Taxon taxon_ancestor_id) {
        super(scientificName, author, publication_year, taxon_ancestor_id);
    }

    /**
     * Verify the scientific name
     */
    @Override
    public boolean verifyName(String name) {
        if(!Character.isUpperCase(name.charAt(0))){
            throw new IllegalArgumentException("Scientific name must start with upper case");
        }
        return true;
    }

    /**
     * Check if the ancestor is valid
     */
    @Override
    public boolean checkAncestor(Taxon taxon) {
        if(!(taxon.getClass().getSimpleName().equals("Family"))) {
            throw new IllegalArgumentException("Ancestor must be of type Family");
        }
        return true;
    }
}
