package com.example.code.Models;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "division")
@NoArgsConstructor
public class Division extends Kingdom{
    /**
     * Constructor
     */
    public Division(String scientificName, String author, Integer publicationYear, Taxon taxonAncestorId) {
        super(scientificName, author, publicationYear, taxonAncestorId);
    }

    /**
     * Verify the scientific name
     */
    @Override
    public boolean verifyName(String name) {
        if(!(Character.isUpperCase(name.charAt(0)) && name.endsWith("phyta"))){
            throw new IllegalArgumentException("Scientific name must start with upper case and end with 'phyta'");
        }
        return true;
    }

    /**
     * Check if the ancestor is valid
     */
    @Override
    public boolean checkAncestor(Taxon taxon) {
        if(!(taxon.getClass().getSimpleName().equals("Kingdom"))) {
            throw new IllegalArgumentException("Ancestor must be of type kingdom");
        }
        return true;
    }
}
