package com.example.code.Services;

import com.example.code.Models.Family;
import com.example.code.Models.Kingdom;
import com.example.code.repository.FamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamilyService {
    @Autowired
    private FamilyRepository familyRepository;

    /**
     * Retrieves all Family entries from the database.
     *
     * @return A list of all Family objects.
     */
    public List<Family> findAll() {
        return familyRepository.findAllFamilies();
    }

    /**
     * Saves the provided Family object to the database.
     *
     * @param family The Family object to be saved.
     * @return The saved Family object.
     */
    public Family save(Family family) {
        return familyRepository.save(family);
    }

    /**
     * Deletes a Family entry by its scientific name.
     *
     * @param ScientificName The scientific name of the Family to be deleted.
     */
    public void deleteByScientificName(String ScientificName) {
        familyRepository.deleteByScientificName(ScientificName);
    }

    /**
     * Updates a Family entry with the specified parameters.
     *
     * @param id The ID of the Family to be updated.
     * @param scientificName The updated scientific name.
     * @param author The updated author.
     * @param publicationYear The updated publication year.
     * @param ancestor The updated ancestor.
     * @return The number of rows affected by the update.
     */
    public int update(int id, String scientificName, String author, Integer publicationYear, int ancestor) {
        return familyRepository.update(scientificName, author, publicationYear, id, ancestor);
    }

    /**
     * Retrieves a Family entry by its ID.
     *
     * @param id The ID of the Family to be retrieved.
     * @return The Family object with the specified ID.
     */
    public Family findById(int id) {
        return familyRepository.findById(id);
    }
}
