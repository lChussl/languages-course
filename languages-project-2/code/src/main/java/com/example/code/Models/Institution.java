package com.example.code.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@DiscriminatorValue("institution")
@Getter
@Setter
public class Institution extends Owner{
    @Column(name = "website")
    private String website;

    /**
     * Constructor
     */
    public Institution(int id, String name, String country, String phone, String email, String website) {
        super(id, name, country, phone, email);
        this.website = website;
    }
}
