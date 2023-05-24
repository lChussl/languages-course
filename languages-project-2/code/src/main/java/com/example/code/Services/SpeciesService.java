package com.example.code.Services;

import com.example.code.Models.Species;
import com.example.code.repository.SpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class SpeciesService {
    @Autowired
    SpeciesRepository speciesRepository;

    /**
     * Retrieves all Species entries from the database.
     *
     * @return A list of all Species objects.
     */
    public List<Species> findAll() {
        return speciesRepository.findAllSpecies();
    }

    /**
     * Saves the provided Species object to the database.
     *
     * @param species The Species object to be saved.
     * @return The saved Species object.
     */
    public Species save(Species species) {
        return speciesRepository.save(species);
    }

    /**
     * Deletes a Species entry by its scientific name.
     *
     * @param ScientificName The scientific name of the Species to be deleted.
     */
    public void deleteByScientificName(String ScientificName) {
        speciesRepository.deleteByScientificName(ScientificName);
    }

    /**
     * Updates a Species entry with the specified parameters.
     *
     * @param id The ID of the Species to be updated.
     * @param scientificName The updated scientific name.
     * @param author The updated author.
     * @param publicationYear The updated publication year.
     * @param ancestor The updated ancestor.
     * @return The number of rows affected by the update.
     */
    public int update(Integer id, String scientificName, String author, Integer publicationYear, int ancestor) {
        return speciesRepository.update(scientificName, author, publicationYear, id, ancestor);
    }

    /**
     * Retrieves a Species entry by its ID.
     *
     * @param id The ID of the Species to be retrieved.
     * @return The Species object with the specified ID.
     */
    public Species findById(int id) {
        return speciesRepository.findById(id);
    }
}