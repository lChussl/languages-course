package com.example.code.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "owner_type")
@NoArgsConstructor
@Getter
@Setter
public abstract class Owner implements Utils {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "owner_type", insertable = false, updatable = false)
    protected String ownerType;

    /**
     * Constructor
     */
    public Owner(int id, String name, String country, String phone, String email) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.phone = phone;
        this.email = email;
    }
}




