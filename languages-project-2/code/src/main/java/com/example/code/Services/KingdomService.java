package com.example.code.Services;


import com.example.code.Models.Kingdom;
import com.example.code.repository.KingdomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;

@Service
public class KingdomService {
    @Autowired
    private KingdomRepository kingdomRepository;

    /**
     * Saves the provided Kingdom object to the database.
     *
     * @param kingdom The Kingdom object to be saved.
     * @return The saved Kingdom object.
     */
    public Kingdom save(Kingdom kingdom) {
        return kingdomRepository.save(kingdom);
    }

    /**
     * Deletes a Kingdom entry by its scientific name.
     *
     * @param ScientificName The scientific name of the Kingdom to be deleted.
     */
    public void deleteByScientificName(String ScientificName) {
        kingdomRepository.deleteByScientificName(ScientificName);
    }

    /**
     * Updates a Kingdom entry with the specified parameters.
     *
     * @param id The ID of the Kingdom to be updated.
     * @param scientificName The updated scientific name.
     * @param author The updated author.
     * @param publication_Year The updated publication year.
     * @return The number of rows affected by the update.
     */
    public int update(Integer id, String scientificName, String author, Integer publication_Year) {
        return kingdomRepository.update(scientificName, author, publication_Year, id);
    }

    /**
     * Retrieves a Kingdom entry by its ID.
     *
     * @param id The ID of the Kingdom to be retrieved.
     * @return The Kingdom object with the specified ID, or null if not found.
     */
    public Kingdom findById(int id) {
        Optional<Kingdom> kingdomOptional = Optional.ofNullable(kingdomRepository.findById(id));
        return kingdomOptional.orElse(null);
    }

    /**
     * Retrieves all Kingdom entries from the database.
     *
     * @return A list of all Kingdom objects.
     */
    public List<Kingdom> findAll() {
        return kingdomRepository.findAllKingdoms();
    }
}
