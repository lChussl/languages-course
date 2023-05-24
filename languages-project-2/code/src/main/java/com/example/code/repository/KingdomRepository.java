package com.example.code.repository;

import com.example.code.Models.Kingdom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface KingdomRepository extends JpaRepository<Kingdom, Integer> {
    @Transactional
    void deleteByScientificName(String scientificName);

    @Modifying
    @Transactional
    @Query("UPDATE Kingdom k SET k.scientificName = :scientificName, k.author = :author, k.publication_year = :publication_year WHERE k.id = :id")
    int update(@Param("scientificName") String scientificName, @Param("author") String author, @Param("publication_year") Integer publication_year, @Param("id") Integer id);

    Kingdom findById(int id);

    @Query("SELECT k FROM Kingdom k WHERE k.taxonType = 'Kingdom'")
    List<Kingdom> findAllKingdoms();
}