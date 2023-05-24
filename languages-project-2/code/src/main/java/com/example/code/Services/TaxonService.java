package com.example.code.Services;

import com.example.code.Models.Taxon;
import com.example.code.repository.TaxonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaxonService {
    @Autowired
    private TaxonRepository taxonRepository;

    /**
     * Gets all the taxons
     *
     * @return List of taxons
     */
    public List findAll() {
        return taxonRepository.findAll();
    }

    /**
     * Save taxon
     *
     * @param taxon Taxon
     * @return Taxon saved taxon
     */
    public Taxon save(Taxon taxon) {
        return taxonRepository.save(taxon);
    }

    /**
     * Update taxon
     *
     * @param taxon Taxon
     * @return Taxon saved taxon
     */
    public Taxon update(Taxon taxon) {
        return taxonRepository.save(taxon);
    }

    /**
     * Find taxon by id
     *
     * @param id int
     * @return Taxon
     */
    public Taxon findById(int id) {
        return taxonRepository.findById(id);
    }
}
