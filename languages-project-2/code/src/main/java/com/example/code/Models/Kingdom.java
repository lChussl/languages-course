package com.example.code.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Kingdom")
@Getter
@Setter
@NoArgsConstructor
public class Kingdom extends ImageTaxon {
    /**
     * Constructor
     */
    public Kingdom(String scientificName, String author, Integer publicationYear) {
        super(scientificName, author, publicationYear);
    }

    public Kingdom(String scientificName, String author, Integer publicationYear, Taxon taxonAncestorId) {
        super(scientificName, author, publicationYear, taxonAncestorId);
    }

    /**
     * Verify the scientific name
     */
    public boolean verifyName(String name) {
        if(!Character.isUpperCase(name.charAt(0))){
            throw new IllegalArgumentException("Name must start with upper case");
        }
        return true;
    }
}

