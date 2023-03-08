package Taxones;

public class Family extends Order{
    public Family(int id, String scientificName, String author, int publication_year, Taxon taxon_ancestor_id) {
        super(id, scientificName, author, publication_year, taxon_ancestor_id, taxon_ancestor_id.getCollecting_method());
    }

    /**
     * Verify the scientific name
     */
    @Override
    public boolean verifyName(String name) {
        if(!(Character.isUpperCase(name.charAt(0)) && name.endsWith("eae"))){
            throw new IllegalArgumentException("Scientific name must start with upper case and end with 'eae'");
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
        if(!(taxon.getClass().getSimpleName().equals("Order"))) {
            throw new IllegalArgumentException("Ancestor must be of type Order");
        }
        return true;
    }
}
