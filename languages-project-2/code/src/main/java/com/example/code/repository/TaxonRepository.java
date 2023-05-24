package com.example.code.repository;

import com.example.code.Models.Taxon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxonRepository extends JpaRepository<Taxon, Integer> {
    @Query("SELECT it from ImageTaxon it where it.id = :id")
    Taxon findById(int id);
}