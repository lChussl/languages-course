package com.example.code.Services;

import com.example.code.Models.Kingdom;
import com.example.code.repository.DivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.code.Models.Division;

import java.util.List;

@Service
public class DivisionService {
    @Autowired
    private DivisionRepository divisionRepository;

    /**
     * Saves the provided Division object to the database.
     *
     * @param division The Division object to be saved.
     * @return The saved Division object.
     */
    public Division save(Division division) {
        return divisionRepository.save(division);
    }

    /**
     * Deletes a Division entry by its scientific name.
     *
     * @param ScientificName The scientific name of the Division to be deleted.
     */
    public void deleteByScientificName(String ScientificName) {
        divisionRepository.deleteByScientificName(ScientificName);
    }

    /**
     * Updates a Division entry with the specified parameters.
     *
     * @param id The ID of the Division to be updated.
     * @param scientificName The updated scientific name.
     * @param author The updated author.
     * @param publicationYear The updated publication year.
     * @param ancestor The updated ancestor.
     * @return The number of rows affected by the update.
     */
    public int update(Integer id, String scientificName, String author, Integer publicationYear, int ancestor) {
        return divisionRepository.update(scientificName, author, publicationYear, id, ancestor);
    }

    /**
     * Retrieves a Division entry by its ID.
     *
     * @param id The ID of the Division to be retrieved.
     * @return The Division object with the specified ID.
     */
    public Division findById(int id) {
        return divisionRepository.findById(id);
    }

    /**
     * Retrieves all Division entries from the database.
     *
     * @return A list of all Division objects.
     */
    public List<Division> findAll() {
        return divisionRepository.findAllDivisions();
    }
}
