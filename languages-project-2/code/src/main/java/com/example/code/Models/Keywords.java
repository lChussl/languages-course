package com.example.code.Models;

public enum Keywords {
    DOMAIN("Domain"),
    KINGDOM("Kingdom"),
    PHYLUM("Phylum"),
    CLASS("Class"),
    ORDER("Order"),
    FAMILY("Family"),
    GENUS("Genus"),
    SPECIES("Species");

    private final String displayName;

    Keywords(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
