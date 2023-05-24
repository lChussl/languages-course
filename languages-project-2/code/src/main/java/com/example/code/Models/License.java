package com.example.code.Models;

public enum License{
    BY("by"),
    BY_NC("by-nc"),
    BY_NC_SA("by-nc-sa"),
    BY_NC_ND("by-nc-nd"),
    BY_SA("by-sa"),
    BY_ND("by-nd");

    private final String name;

    License(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
