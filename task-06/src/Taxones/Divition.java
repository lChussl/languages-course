package Taxones;

import java.util.Date;

public class Divition extends Kingdom{
    public Divition(int id, String scientificName, String author, int publication_year, Taxon taxon_ancestor_id) {
        super(id, scientificName, author, publication_year, taxon_ancestor_id);
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

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": " + getScientificName();
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
