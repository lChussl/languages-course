package com.example.code.Models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Entity
@Table(name = "image")
@Getter
@Setter
public class Image implements Utils{
    /**
     * Constructor
     */
    public Image() {
        amount++;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description", nullable = false , length = 500)
    private String description;

    @Column(name = "date")
    private Date date;

    @ElementCollection
    @CollectionTable(name = "image_keywords", joinColumns = @JoinColumn(name = "image_id"))
    @Column(name = "keyword")
    private List<String> keywords = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Owner author;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Owner owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "license")
    private License license;

    @ManyToMany
    @JoinTable(
            name = "image_taxon",
            joinColumns = @JoinColumn(name = "image_id"),
            inverseJoinColumns = @JoinColumn(name = "taxon_id")
    )

    private List<ImageTaxon> taxons = new ArrayList<>();

    @Column(name = "url")
    private String url;

    @Column(name = "amount")
    private static int amount;

    public static int getAmount() {
        return amount;
    }
}
