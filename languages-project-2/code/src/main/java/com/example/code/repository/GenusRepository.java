package com.example.code.repository;

import com.example.code.Models.Genus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GenusRepository extends JpaRepository<Genus, Integer> {
        Genus save(Genus genus);

        @Transactional
        void deleteByScientificName(String scientificName);

        @Transactional
        @Modifying
        @Query("UPDATE Genus g SET g.scientificName = :scientificName, g.author = :author, g.publication_year = :publication_year, g.taxon_ancestor_id =:taxon_ancestor_id WHERE g.id = :id")
        int update(@Param("scientificName") String scientificName, @Param("author") String author, @Param("publication_year") Integer publication_year, @Param("id") Integer id, @Param("taxon_ancestor_id") Integer taxon_ancestor_id);

        Genus findById(int id);

        @Query("SELECT g FROM Genus g WHERE g.taxonType = 'Genus'")
        List<Genus> findAllGenus();
}
