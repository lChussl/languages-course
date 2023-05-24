package com.example.code.repository;

import com.example.code.Models.ImageTaxon;
import com.example.code.Models.Kingdom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageTaxonRepository extends JpaRepository<ImageTaxon, Integer> {
    @Query("SELECT it FROM ImageTaxon it WHERE it.id = :taxonId")
    List<ImageTaxon> findByTaxonId(@Param("taxonId") Integer taxonId);

    List<Kingdom> findAllByTaxonType(String type);
}