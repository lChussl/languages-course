package com.example.code.Models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public abstract class Taxon implements Utils{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "scientific_name")
    private String scientificName;

    @Column(name = "author")
    private String author;

    @Column(name = "publication_year")
    private int publication_year;

    @Column(name = "taxon_ancestor_id")
    private int taxon_ancestor_id;

    /**
     * Constructors
     */
    public Taxon(String scientificName, String author, Integer publicationYear) {
        this.scientificName = scientificName;
        this.author = author;
        this.publication_year = publicationYear;

        verifyName(scientificName);
    }

    public Taxon(String scientificName, String author, int publication_year, Taxon taxon_ancestor_id) {
        this.scientificName = scientificName;
        this.author = author;
        this.publication_year = publication_year;
        this.taxon_ancestor_id = taxon_ancestor_id == null ? 0 : taxon_ancestor_id.getId();

        verifyName(scientificName);
        checkAncestor(taxon_ancestor_id);
    }

    /**
     * Verify the scientific name
     */
    public abstract boolean verifyName(String name);

    /**
     * Check if the ancestor is valid
     */
    public abstract boolean checkAncestor(Taxon taxon);

    /**
     * Checks if the ancestor id is valid
     *
     * @param taxon_ancestor_id of type taxon to get the id
     */
    public void setTaxon_ancestor_id(Taxon taxon_ancestor_id) {
        checkAncestor(taxon_ancestor_id);

        this.taxon_ancestor_id = taxon_ancestor_id.getId();
    }

    /**
     * Methods for order level
     *
     * @return String
     */
    public String getCollecting_method() {
        return null;
    }

    public void setCollecting_method(String collectingMethod) {  }
}