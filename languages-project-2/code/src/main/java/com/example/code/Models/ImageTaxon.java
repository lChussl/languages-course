package com.example.code.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "concrete_taxon")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "taxon_type", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor
@Getter
@Setter
public abstract class ImageTaxon extends Taxon {
    /**
     * Constructors
     */
    public ImageTaxon(String scientificName, String author, int publication_year, Taxon taxon_ancestor_id) {
        super(scientificName, author, publication_year, taxon_ancestor_id);
    }

    public ImageTaxon(String scientificName, String author, Integer publicationYear) {
        super(scientificName, author, publicationYear);
    }

    @Column(name = "image")
    private String image;

    @Column(name="taxon_type", insertable = false, updatable = false)
    protected String taxonType;

    /**
     * Maps the taxon name to className: scientificName
     *
     * @return String
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": " + getScientificName();
    }

    /**
     * Check if the ancestor is valid
     */
    @Override
    public boolean checkAncestor(Taxon taxon) {
        return true;
    }

}
