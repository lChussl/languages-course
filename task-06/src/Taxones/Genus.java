package Taxones;

import java.util.Date;

public class Genus extends Family{
    public Genus(int id, String scientificName, String author, int publication_year, Taxon taxon_ancestor_id) {
        super(id, scientificName, author, publication_year, taxon_ancestor_id);
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

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": " + getScientificName();
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
