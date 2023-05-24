package com.example.code.repository;

import com.example.code.Models.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Integer> {
    Division save(Division division);

    @Transactional
    void deleteByScientificName(String ScientificName);

    @Modifying
    @Transactional
    @Query("UPDATE Division d SET d.scientificName = :scientificName, d.author = :author, d.publication_year = :publication_year, d.taxon_ancestor_id =:taxon_ancestor_id WHERE d.id = :id")
    int update(@Param("scientificName") String scientificName, @Param("author") String author, @Param("publication_year") Integer publication_year, @Param("id") Integer id, @Param("taxon_ancestor_id") Integer taxon_ancestor_id);

    @Query("SELECT d FROM Division d WHERE d.taxonType = 'Division'")
    List<Division> findAllDivisions();

    Division findById(int id);
}
