package Taxones;

import java.util.Date;

public class Species extends Genus{
    private String specimen_location_rack;
    private String specimen_location_drawer;

    /**
     * Verify the scientific name
     */
    public Species(int id, String scientificName, String author, int publication_year, Taxon taxon_ancestor_id, String specimen_location_rack, String specimen_location_drawer) {
        super(id, scientificName, author, publication_year, taxon_ancestor_id);
        this.specimen_location_rack = specimen_location_rack;
        this.specimen_location_drawer = specimen_location_drawer;
    }

    @Override
    public boolean verifyName(String name) {
        if(!Character.isLowerCase(name.charAt(0))){
            throw new IllegalArgumentException("Scientific name must start with lower case");
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": " + getScientificName();
    }

    /**
     * Getter and setters
     */
    public String getSpecimen_location_rack() {
        return specimen_location_rack;
    }

    public void setSpecimen_location_rack(String specimen_location_rack) {
        this.specimen_location_rack = specimen_location_rack;
    }

    public String getSpecimen_location_drawer() {
        return specimen_location_drawer;
    }

    public void setSpecimen_location_drawer(String specimen_location_drawer) {
        this.specimen_location_drawer = specimen_location_drawer;
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
