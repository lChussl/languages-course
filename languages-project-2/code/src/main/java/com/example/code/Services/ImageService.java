package com.example.code.Services;

import com.example.code.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.code.Models.Image;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    /**
     * Retrieves all Image entries from the database.
     *
     * @return A list of all Image objects.
     */
    public List<Image> findAll() {
        return imageRepository.findAll();
    }

    /**
     * Saves the provided Image object to the database.
     *
     * @param image The Image object to be saved.
     * @return The saved Image object.
     */
    public Image save(Image image) {
        return imageRepository.save(image);
    }

    /**
     * Updates an Image entry with the specified Image object.
     *
     * @param updatedImage The updated Image object.
     * @return The updated Image object.
     */
    public Image update(Image updatedImage) {
        return imageRepository.save(updatedImage);
    }

    /**
     * Retrieves an Image entry by its ID.
     *
     * @param id The ID of the Image to be retrieved.
     * @return An Optional containing the Image object with the specified ID, or empty if not found.
     */
    public Optional<Image> findById(int id) {
        return imageRepository.findById(id);
    }

    /**
     * Deletes an Image entry by its ID.
     *
     * @param id The ID of the Image to be deleted.
     */
    public void deleteById(int id) {
        imageRepository.deleteById(id);
    }
}
