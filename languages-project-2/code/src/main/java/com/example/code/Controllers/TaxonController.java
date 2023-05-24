package com.example.code.Controllers;

import com.example.code.Models.*;
import com.example.code.Models.Class;
import com.example.code.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TaxonController {
    /**
     * Controller made to provide functionalities to the view, such as create, delete or update taxons
     */
    private final KingdomService kingdomService;
    private final TaxonService taxonService;
    private final DivisionService divisionService;
    private final ClassService classService;
    private final OrderService orderService;
    private final FamilyService familyService;
    private final GenusService genusService;
    private final SpeciesService speciesService;

    /**
     * Constructor
     */
    @Autowired
    public TaxonController(KingdomService kingdomService, TaxonService taxonService, DivisionService divisionService,
                            ClassService classService, OrderService orderService, FamilyService familyService,
                            GenusService genusService, SpeciesService speciesService) {
        this.kingdomService = kingdomService;
        this.taxonService = taxonService;
        this.divisionService = divisionService;
        this.classService = classService;
        this.orderService = orderService;
        this.familyService = familyService;
        this.genusService = genusService;
        this.speciesService = speciesService;
    }

    /**
     * Adds a new taxon with the provided data.
     *
     * @param requestData The data for the taxon to be added.
     * @return Map<String, String> with the status message.
     */
    @PostMapping(value = "/taxon/add")
    public @ResponseBody Map<String, String> addTaxon(@RequestBody Map<String, Object> requestData) {
        String type = (String) requestData.get("type");

        Taxon savedTaxon = null;
        Taxon taxon = buildTaxon(requestData);

        switch (type) {
            case "kingdom" -> savedTaxon = kingdomService.save((Kingdom) taxon);
            case "division" -> savedTaxon = divisionService.save((Division) taxon);
            case "class" -> savedTaxon = classService.save((Class) taxon);
            case "order" -> savedTaxon = orderService.save((Order) taxon);
            case "family" -> savedTaxon = familyService.save((Family) taxon);
            case "genus" -> savedTaxon = genusService.save((Genus) taxon);
            case "species" -> savedTaxon = speciesService.save((Species) taxon);
        }

        Map<String, String> response = new HashMap<>();

        if (savedTaxon != null) {
            response.put("message", "Taxon created successfully");

            return ResponseEntity.ok(response).getBody();
        } else {
            response.put("message", "Something went wrong while creating the taxon");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response).getBody();
        }
    }

    /**
     * Updates an existing taxon with the provided data.
     *
     * @param requestData The data for the taxon to be updated.
     * @return Map<String, String> with the status message.
     */
    @PostMapping(value = "/taxon/update")
    public @ResponseBody Map<String, String> updateTaxon(@RequestBody Map<String, Object> requestData) {
        String type = (String) requestData.get("type");
        Integer id = (Integer) requestData.get("id");

        Taxon taxon = buildTaxon(requestData);

        int updatedRowCount = -1;

        switch (type) {
            case "kingdom" -> {
                assert taxon != null;
                updatedRowCount = kingdomService.update(id, taxon.getScientificName(), taxon.getAuthor(), taxon.getPublication_year());
            }
            case "division" -> {
                assert taxon != null;
                updatedRowCount = divisionService.update(id, taxon.getScientificName(), taxon.getAuthor(), taxon.getPublication_year(), taxon.getTaxon_ancestor_id());
            }
            case "class" -> {
                assert taxon != null;
                updatedRowCount = classService.update(id, taxon.getScientificName(), taxon.getAuthor(), taxon.getPublication_year(), taxon.getTaxon_ancestor_id());
            }
            case "order" -> {
                assert taxon != null;
                updatedRowCount = orderService.update(id, taxon.getScientificName(), taxon.getAuthor(), taxon.getPublication_year(), taxon.getTaxon_ancestor_id(), taxon.getCollecting_method());
            }
            case "family" -> {
                assert taxon != null;
                updatedRowCount = familyService.update(id, taxon.getScientificName(), taxon.getAuthor(), taxon.getPublication_year(), taxon.getTaxon_ancestor_id());
            }
            case "genus" -> {
                assert taxon != null;
                updatedRowCount = genusService.update(id, taxon.getScientificName(), taxon.getAuthor(), taxon.getPublication_year(), taxon.getTaxon_ancestor_id());
            }
            case "species" -> {
                assert taxon != null;
                updatedRowCount = speciesService.update(id, taxon.getScientificName(), taxon.getAuthor(), taxon.getPublication_year(), taxon.getTaxon_ancestor_id());
            }
        }

        Map<String, String> response = new HashMap<>();

        if (updatedRowCount > 0) {
            response.put("message", "Taxon updated successfully");

            return ResponseEntity.ok(response).getBody();
        } else {
            response.put("message", "Something went wrong while updating the taxon");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response).getBody();
        }
    }

    /**
     * Deletes an existing taxon with the provided scientific name.
     *
     * @param requestData The data containing the scientific name of the taxon to be deleted.
     * @return Map<String, String> with the status message.
     */
    @PostMapping(value = "/taxon/delete")
    public @ResponseBody Map<String, String> deleteTaxon(@RequestBody Map<String, Object> requestData) {
        Map<String, String> response = new HashMap<>();
        String type = (String) requestData.get("type");
        String scientificName = (String) requestData.get("scientificName");

        try {
            switch (type) {
                case "kingdom" -> kingdomService.deleteByScientificName(scientificName);
                case "division" -> divisionService.deleteByScientificName(scientificName);
                case "class" -> classService.deleteByScientificName(scientificName);
                case "order" -> orderService.deleteByScientificName(scientificName);
                case "family" -> familyService.deleteByScientificName(scientificName);
                case "genus" -> genusService.deleteByScientificName(scientificName);
                case "species" -> speciesService.deleteByScientificName(scientificName);
            }

            response.put("message", "Taxon deleted");

            return ResponseEntity.ok(response).getBody();
        } catch (Exception e) {
            response.put("message", "Something went wrong while deleting the taxon");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response).getBody();
        }
    }

    /**
     * Helper method for building a taxon object based on the provided data.
     *
     * @param requestData The data containing the required fields to build a taxon object.
     * @return A taxon object of the specified type, or null if the type is not recognized.
     */
    private Taxon buildTaxon(Map<String, Object> requestData) {
        String type = (String) requestData.get("type");
        String scientificName = (String) requestData.get("scientificName");
        String author = (String) requestData.get("author");
        Integer publicationYear = ((Number) requestData.get("publication_year")).intValue();
        Integer ancestorId = (requestData.get("ancestorId") instanceof Number)
                ? ((Number) requestData.get("ancestorId")).intValue()
                : null;
        String collectingMethod = (String) requestData.get("collectingMethod");

        Taxon ancestor = null;
        if(ancestorId != null) {
            ancestor = taxonService.findById(ancestorId);
        }

        switch (type) {
            case "kingdom":
                return new Kingdom(scientificName, author, publicationYear);
            case "division":
                return new Division(scientificName, author, publicationYear, ancestor);
            case "class":
                return new Class(scientificName, author, publicationYear, ancestor);
            case "order":
                return new Order(scientificName, author, publicationYear, ancestor, collectingMethod);
            case "family":
                return new Family(scientificName, author, publicationYear, ancestor);
            case "genus":
                return new Genus(scientificName, author, publicationYear, ancestor);
            case "species":
                return new Species(scientificName, author, publicationYear, ancestor);
            default:
                return null;
        }
    }
}
