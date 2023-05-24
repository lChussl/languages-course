package com.example.code.repository;

import com.example.code.Models.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Integer> {
    Family save (Family family);

    @Transactional
    void deleteByScientificName(String scientificName);

    @Modifying
    @Transactional
    @Query("UPDATE Family f SET f.scientificName = :scientificName, f.author = :author, f.publication_year = :publication_year, f.taxon_ancestor_id =:taxon_ancestor_id WHERE f.id = :id")
    int update(@Param("scientificName") String scientificName, @Param("author") String author, @Param("publication_year") Integer publication_year, @Param("id") Integer id, @Param("taxon_ancestor_id") Integer taxon_ancestor_id);

    Family findById(int id);

    @Query("SELECT f FROM Family f WHERE f.taxonType = 'Family'")
    List<Family> findAllFamilies();
}
