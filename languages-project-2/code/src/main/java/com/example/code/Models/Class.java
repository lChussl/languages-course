package com.example.code.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "Class")
public class Class extends Division {
    /**
     * Constructor
     */
    public Class(String scientificName, String author, Integer publicationYear, Taxon taxonAncestorId) {
        super(scientificName, author, publicationYear, taxonAncestorId);
    }

    /**
     * Verify the scientific name
     */
    public boolean verifyName(String name) {
        if(!(Character.isUpperCase(name.charAt(0)) && name.endsWith("opsida"))) {
            throw new IllegalArgumentException("Scientific name must start with upper case and end with 'opsida'");
        }
        return true;
    }

    /**
     * Check if the ancestor is valid
     */
    @Override
    public boolean checkAncestor(Taxon taxon) {
        if(!(taxon.getClass().getSimpleName().equals("Division"))) {
            throw new IllegalArgumentException("Ancestor must be of type Division");
        }
        return true;
    }
}
