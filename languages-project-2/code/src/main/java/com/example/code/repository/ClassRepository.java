package com.example.code.repository;

import com.example.code.Models.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Class, Integer> {
    Class save(Class clase);

    @Transactional
    void deleteByScientificName(String scientificName);

    @Modifying
    @Transactional
    @Query("UPDATE Class c SET c.scientificName = :scientificName, c.author = :author, c.publication_year = :publication_year, c.taxon_ancestor_id =:taxon_ancestor_id WHERE c.id = :id")
    int update(@Param("scientificName") String scientificName, @Param("author") String author, @Param("publication_year") Integer publication_year, @Param("id") Integer id, @Param("taxon_ancestor_id") Integer taxon_ancestor_id);

    Class findById(int id);

    @Query("SELECT c FROM Class c WHERE c.taxonType = 'Class'")
    List<Class> findAllClasses();
}
