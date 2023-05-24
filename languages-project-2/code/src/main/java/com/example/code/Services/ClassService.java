package com.example.code.Services;

import com.example.code.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.code.Models.Class;

import java.util.List;

@Service
public class ClassService {
    @Autowired
    private ClassRepository classRepository;

    /**
     * Saves the provided Class object to the database.
     *
     * @param clase The Class object to be saved.
     * @return The saved Class object.
     */
    public Class save(Class clase) {
        return classRepository.save(clase);
    }

    /**
     * Deletes a Class entry by its scientific name.
     *
     * @param ScientificName The scientific name of the Class to be deleted.
     */
    public void deleteByScientificName(String ScientificName) {
        classRepository.deleteByScientificName(ScientificName);
    }

    /**
     * Updates a Class entry with the specified parameters.
     *
     * @param id The ID of the Class to be updated.
     * @param scientificName The updated scientific name.
     * @param author The updated author.
     * @param publicationYear The updated publication year.
     * @param ancestor The updated ancestor.
     *
     * @return The number of rows affected by the update.
     */
    public int update(Integer id, String scientificName, String author, Integer publicationYear, int ancestor) {
        return classRepository.update(scientificName, author, publicationYear, id, ancestor);
    }

    /**
     * Retrieves all Class entries from the database.
     *
     * @return A list of all Class objects.
     */
    public List<Class> findAll() {
        return classRepository.findAllClasses();
    }
}
