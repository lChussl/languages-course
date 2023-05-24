package com.example.code.repository;

import com.example.code.Models.Institution;
import com.example.code.Models.Owner;
import com.example.code.Models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
        @Query("SELECT p FROM Person p WHERE p.id = :id")
        Optional<Person> findPersonById(int id);

        @Query("SELECT i FROM Institution i WHERE i.id = :id")
        Optional<Institution> findInstitutionById(int id);

        @Query("SELECT p FROM Person p WHERE p.ownerType = 'person'")
        List<Owner> findAllPerson();

        @Query("SELECT i FROM Institution i WHERE i.ownerType = 'institution'")
        List<Owner> findAllInstitution();
}