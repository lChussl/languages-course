package com.example.code.Controllers;

import com.example.code.Models.*;
import com.example.code.Services.ImageService;
import com.example.code.Services.ImageTaxonService;
import com.example.code.Services.OwnerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ImageController {
    /**
     * This controller sends the necessary data to the images view and provides functionalities to the view such as create, update or delete images
     */
    private final ImageService imageService;
    private final OwnerService ownerService;
    private final ImageTaxonService imageTaxonService;

    /**
     * Constructor
     */
    @Autowired
    public ImageController(ImageService imageService, OwnerService ownerService, ImageTaxonService imageTaxonService) {
        this.imageService = imageService;
        this.ownerService = ownerService;
        this.imageTaxonService = imageTaxonService;
    }

    /**
     * Retrieves all images.
     *
     * @return Map<String, Object> with the data required.
     */
    @GetMapping("/images")
    public @ResponseBody Map<String, Object> images() {
        Map<String, Object> response = new HashMap<>();

        response.put("images", imageService.findAll());
        response.put("owner", ownerService.findAllOwners());

        return response;
    }

    /**
     * Retrieves a single image with the specified ID.
     *
     * @param id The ID of the image to retrieve.
     * @return Map<String, Object> with the data required.
     */
    @GetMapping("/images/{id}")
    public @ResponseBody Map<String, Object> singleImage(@PathVariable("id") int id) {
        Map<String, Object> response = new HashMap<>();

        response.put("image", imageService.findById(id));
        response.put("owner", ownerService.findAllOwners());

        return response;
    }

    /**
     * Adds a new image with the provided data.
     *
     * @param requestData The data for the image to be added.
     * @return Map<String, String> with the status message.
     */
    @PostMapping(value = "/image/add")
    public @ResponseBody Map<String, String> addImage(@RequestBody Map<String, Object> requestData) {
        Map<String, String> response = new HashMap<>();
        try {
            Image image = new Image();
            image.setDescription((String) requestData.get("description"));
            image.setUrl((String) requestData.get("url"));

            LocalDate localDate = LocalDate.parse((String) requestData.get("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            image.setDate(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

            Owner author = ownerService.findSpecificOwnerById((Integer) requestData.get("authorId"));
            Owner owner = ownerService.findSpecificOwnerById((Integer) requestData.get("ownerId"));

            image.setAuthor((Person) author);
            image.setOwner(owner);

            List<String> keywordsList = Arrays.asList(((String) requestData.get("keywords")).split(","));
            image.setKeywords(keywordsList);

            License licenseEnum = License.valueOf((String) requestData.get("license"));
            image.setLicense(licenseEnum);

            List<Integer> taxonIdList = Arrays.stream(((String) requestData.get("taxonIds")).split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            List<ImageTaxon> imageTaxons = new ArrayList<>();
            for (Integer taxonId : taxonIdList) {
                List<ImageTaxon> imageTaxonsForTaxon = imageTaxonService.findImageTaxonsByTaxonId(taxonId);
                if (imageTaxonsForTaxon != null) {
                    imageTaxons.addAll(imageTaxonsForTaxon);
                }
            }

            image.setTaxons(imageTaxons);

            imageService.save(image);

            response.put("message", "Taxon deleted");

            return ResponseEntity.ok(response).getBody();
        } catch (Exception e) {
            response.put("message", "Something went wrong while adding the image");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response).getBody();
        }
    }

    /**
     * Updates an existing image with the provided data.
     *
     * @param requestData The data for the image to be updated.
     * @return Map<String, String> with the status message.
     */
    @PostMapping(value = "/image/update")
    public @ResponseBody Map<String, String> updateImage(@RequestBody Map<String, Object> requestData) {
        Map<String, String> response = new HashMap<>();
        try {
            Image image = new Image();
            image.setId((Integer) requestData.get("id"));

            image.setDescription((String) requestData.get("description"));
            image.setUrl((String) requestData.get("url"));

            LocalDate localDate = LocalDate.parse((String) requestData.get("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            image.setDate(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

            Owner author = ownerService.findSpecificOwnerById((Integer) requestData.get("authorId"));
            Owner owner = ownerService.findSpecificOwnerById((Integer) requestData.get("ownerId"));
            image.setAuthor(author);
            image.setOwner(owner);

            List<String> keywordsList = Arrays.asList(((String) requestData.get("keywords")).split(","));
            image.setKeywords(keywordsList);

            License licenseEnum = License.valueOf((String) requestData.get("license"));
            image.setLicense(licenseEnum);

            List<Integer> taxonIdList = Arrays.stream(((String) requestData.get("taxonIds")).split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            List<ImageTaxon> imageTaxons = new ArrayList<>();
            for (Integer taxonId : taxonIdList) {
                List<ImageTaxon> imageTaxonsForTaxon = imageTaxonService.findImageTaxonsByTaxonId(taxonId);
                if (imageTaxonsForTaxon != null) {
                    imageTaxons.addAll(imageTaxonsForTaxon);
                }
            }
            image.setTaxons(imageTaxons);
            imageService.update(image);

            response.put("message", "Image updated");

            return ResponseEntity.ok(response).getBody();
        } catch (Exception e) {
            response.put("message", "Something went wrong while updating the image");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response).getBody();
        }
    }

    /**
     * Deletes an existing image with the provided ID.
     *
     * @param requestData The data containing the ID of the image to be deleted.
     * @param request The HttpServletRequest object associated with the request.
     * @return Map<String, String> with the status message.
     */
    @PostMapping(value = "/image/delete")
    public @ResponseBody Map<String, String> deleteImage(@RequestBody Map<String, Object> requestData, HttpServletRequest request) {
        Map<String, String> response = new HashMap<>();
        try {
            Integer imageId = (Integer) requestData.get("id");

            Optional<Image> imageOptional = imageService.findById(imageId);
            if (imageOptional.isPresent()) {
                Image image = imageOptional.get();
                String imageUrl = image.getUrl();

                String serverPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
                String filePath = imageUrl.replace(serverPath, "");
                response.put("filePath", filePath);


                File currentDirectory = new File(".");
                String projectFolderPath = currentDirectory.getCanonicalPath();
                String realPath = projectFolderPath + filePath;
                Path absolutePath = Paths.get(realPath);

                try {
                    boolean deleted = Files.deleteIfExists(absolutePath);
                    if (deleted) {
                        System.out.println("File successfully deleted.");
                    } else {
                        System.out.println("File not found or not deleted.");
                    }
                } catch (IOException e) {
                    System.out.println("Error while deleting the file: " + e.getMessage());
                    response.put("message", "Something went wrong while deleting the image file from local storage");

                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response).getBody();
                }
                imageService.deleteById(imageId);
                response.put("message", "Image deleted");

                return ResponseEntity.ok(response).getBody();
            } else {
                response.put("message", "Image not found");

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response).getBody();
            }
        } catch (Exception e) {
            response.put("message", "Something went wrong while deleting the image");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response).getBody();
        }
    }
}
