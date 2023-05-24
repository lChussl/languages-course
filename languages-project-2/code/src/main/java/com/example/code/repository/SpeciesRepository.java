package com.example.code.repository;

import com.example.code.Models.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SpeciesRepository extends JpaRepository<Species, Integer> {
    Species save(Species species);

    @Transactional
    void deleteByScientificName(String scientificName);

    @Transactional
    @Modifying
    @Query("UPDATE Species s SET s.scientificName = :scientificName, s.author = :author, s.publication_year = :publication_year, s.taxon_ancestor_id = :taxon_ancestor_id WHERE s.id = :id")
    int update(@Param("scientificName") String scientificName, @Param("author") String author,
               @Param("publication_year") Integer publication_year, @Param("id") Integer id, @Param("taxon_ancestor_id") Integer taxon_ancestor_id);

    Species findById(int id);

    @Query("SELECT s FROM Species s WHERE s.taxonType = 'Species'")
    List<Species> findAllSpecies();
}
