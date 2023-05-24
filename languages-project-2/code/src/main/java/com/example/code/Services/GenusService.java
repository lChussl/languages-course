package com.example.code.Services;

import com.example.code.repository.GenusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.code.Models.Genus;

import java.util.List;

@Service
public class GenusService {
    @Autowired
    private GenusRepository genusRepository;

    /**
     * Retrieves all Genus entries from the database.
     *
     * @return A list of all Genus objects.
     */
    public List<Genus> findAll() {
        return genusRepository.findAllGenus();
    }

    /**
     * Saves the provided Genus object to the database.
     *
     * @param genus The Genus object to be saved.
     * @return The saved Genus object.
     */
    public Genus save(Genus genus) {
        return genusRepository.save(genus);
    }

    /**
     * Deletes a Genus entry by its scientific name.
     *
     * @param ScientificName The scientific name of the Genus to be deleted.
     */
    public void deleteByScientificName(String ScientificName) {
        genusRepository.deleteByScientificName(ScientificName);
    }

    /**
     * Updates a Genus entry with the specified parameters.
     *
     * @param id The ID of the Genus to be updated.
     * @param scientificName The updated scientific name.
     * @param author The updated author.
     * @param publicationYear The updated publication year.
     * @param ancestor The updated ancestor.
     * @return The number of rows affected by the update.
     */
    public int update(Integer id, String scientificName, String author, Integer publicationYear, int ancestor) {
        return genusRepository.update(scientificName, author, publicationYear, id, ancestor);
    }

    /**
     * Retrieves a Genus entry by its ID.
     *
     * @param id The ID of the Genus to be retrieved.
     * @return The Genus object with the specified ID.
     */
    public Genus findById(int id) {
        return genusRepository.findById(id);
    }
}
