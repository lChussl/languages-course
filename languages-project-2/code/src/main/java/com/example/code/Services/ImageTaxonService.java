package com.example.code.Services;

import com.example.code.Models.ImageTaxon;
import com.example.code.Models.Kingdom;
import com.example.code.repository.ClassRepository;
import com.example.code.repository.ImageTaxonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ImageTaxonService {
    @Autowired
    private ImageTaxonRepository imageTaxonRepository;

    /**
     * Retrieves all ImageTaxon entries associated with the specified taxon ID.
     *
     * @param taxonId The taxon ID for which the ImageTaxons are to be retrieved.
     * @return A list of ImageTaxon objects associated with the specified taxon ID.
     */
    public List<ImageTaxon> findImageTaxonsByTaxonId(Integer taxonId) {
        return imageTaxonRepository.findByTaxonId(taxonId);
    }

    /**
     * Retrieves all Kingdom entries with the specified taxon type.
     *
     * @param type The taxon type for which the Kingdoms are to be retrieved.
     * @return A list of Kingdom objects with the specified taxon type.
     */
    public List<Kingdom> findAll(String type) {
        return imageTaxonRepository.findAllByTaxonType(type);
    }
}
