package com.example.code.repository;

import com.example.code.Models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    Optional<Image> findById(Integer id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Image I WHERE I.id = :id")

    void deleteById(@Param("id") int id);
}