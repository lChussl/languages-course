package com.example.code.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "species")
@NoArgsConstructor
public class Species extends Genus{
    /**
     * Constructor
     */
    public Species(String scientificName, String author, int publication_year, Taxon taxon_ancestor_id) {
        super(scientificName, author, publication_year, taxon_ancestor_id);
    }

    /**
     * Verify the scientific name
     */
    @Override
    public boolean verifyName(String name) {
        if(!Character.isLowerCase(name.charAt(0))){
            throw new IllegalArgumentException("Scientific name must start with lower case");
        }
        return true;
    }

    /**
     * Check if the ancestor is valid
     */
    @Override
    public boolean checkAncestor(Taxon taxon) {
        if(!(taxon.getClass().getSimpleName().equals("Genus"))) {
            throw new IllegalArgumentException("Ancestor must be of type Genus");
        }
        return true;
    }
}
