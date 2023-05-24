package Taxones;

import java.util.Date;

public abstract class Taxon {
    private int id;
    private String scientificName;
    private String author;
    private int publication_year;
    private int taxon_ancestor_id;

    public Taxon(int id, String scientificName, String author, int publication_year, Taxon taxon_ancestor_id) {
        this.id = id;
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
     * Getter and setters
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        verifyName(scientificName);
        this.scientificName = scientificName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublication_year() {
        return publication_year;
    }

    public void setPublication_year(int publication_year) {
        this.publication_year = publication_year;
    }

    public int getTaxon_ancestor_id() {
        return taxon_ancestor_id;
    }

    public void setTaxon_ancestor_id(Taxon taxon_ancestor_id) {
        checkAncestor(taxon_ancestor_id);

        this.taxon_ancestor_id = taxon_ancestor_id.getId();
    }

    public String getCollecting_method() {
        return null;
    }
}