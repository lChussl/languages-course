package com.example.code.Services;

import com.example.code.Models.Institution;
import com.example.code.Models.Owner;
import com.example.code.Models.Person;

import com.example.code.repository.OwnerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class OwnerService {
    @Autowired
    private OwnerRepository ownerRepository;

    /**
     * Retrieves a specific Owner entry by its ID. The owner can be either a Person or an Institution.
     *
     * @param id The ID of the Owner to be retrieved.
     * @return The Owner object with the specified ID, or null if not found.
     */
    public Owner findSpecificOwnerById(int id) {
        Optional<Person> personOpt = ownerRepository.findPersonById(id);
        if (personOpt.isPresent()) {
            return personOpt.get();
        }

        Optional<Institution> orgOpt = ownerRepository.findInstitutionById(id);
        if (orgOpt.isPresent()) {
            return orgOpt.get();
        }

        return null;
    }

    /**
     * Retrieves all Owner entries from the database, combining both Person and Institution types.
     *
     * @return A list of all Owner objects.
     */
    public List<Owner> findAllOwners() {
        return Stream.concat(ownerRepository.findAllInstitution().stream(), ownerRepository.findAllPerson().stream()).toList();
    }
}





