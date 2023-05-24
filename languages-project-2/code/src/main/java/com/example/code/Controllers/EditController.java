package com.example.code.Controllers;

import com.example.code.Models.*;
import com.example.code.Services.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
public class EditController {
    /**
     * This controller is made to provide data to the views in Angular.
     */
    private final KingdomService kingdomService;
    private final DivisionService divisionService;
    private final ClassService classService;
    private final OrderService orderService;
    private final FamilyService familyService;
    private final GenusService genusService;
    private final SpeciesService speciesService;
    private final ImageService imageService;
    private final OwnerService ownerService;

    /**
     * Constructor
     */
    @Autowired
    public EditController(KingdomService kingdomService, DivisionService divisionService, ClassService classService,
                          OrderService orderService, FamilyService familyService, GenusService genusService, SpeciesService speciesService,
                          ImageService imageService, OwnerService ownerService) {
        this.kingdomService = kingdomService;
        this.divisionService = divisionService;
        this.classService = classService;
        this.orderService = orderService;
        this.familyService = familyService;
        this.genusService = genusService;
        this.speciesService = speciesService;
        this.imageService = imageService;
        this.ownerService = ownerService;
    }

    /**
     * Provides all the taxons separated by their type to the view.
     *
     * @return Map<String, Object> with the data required.
     */
    @GetMapping("/edit")
    public @ResponseBody Map<String, Object> edit() {
        Map<String, Object> response = new HashMap<>();

        response.put("kingdoms", kingdomService.findAll());
        response.put("divisions", divisionService.findAll());
        response.put("classes", classService.findAll());
        response.put("orders", orderService.findAll());
        response.put("families", familyService.findAll());
        response.put("genuses", genusService.findAll());
        response.put("species", speciesService.findAll());

        return response;
    }

    /**
     * Provides the keywords, authors, owners, images already created, and licenses to the view.
     *
     * @return Map<String, Object> with the data required.
     */
    @GetMapping("/edit/image")
    public @ResponseBody Map<String, Object> image() {
        Map<String, Object> response = new HashMap<>();

        response.put("keywords", Arrays.asList(Keywords.values()));
        response.put("licenses", Arrays.asList(License.values()));
        response.put("images", imageService.findAll());
        response.put("owners", ownerService.findAllOwners());

        return response;
    }

    /**
     * Method to upload the image into /static/public folder.
     *
     * @param file The uploaded image file.
     * @param request The HttpServletRequest object associated with the request.
     * @return Map<String, String> with the status, OK if image uploaded, BAD_REQUEST if failed.
     */
    @PostMapping("/edit/addImage")
    public @ResponseBody Map<String, String> handleFileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        Map<String, String> response = new HashMap<>();

        if (file.isEmpty()) {
            response.put("message", "Something went wrong upload image");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response).getBody();
        }

        String uploadFolder = "src/main/resources/static/public/";

        File folder = new File(uploadFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadFolder + file.getOriginalFilename());
            Files.write(path, bytes);

            String serverPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            String filePath = serverPath + "/public/" + file.getOriginalFilename();

            response.put("filePath", filePath);

            return ResponseEntity.ok(response).getBody();
        } catch (IOException e) {
            response.put("message", "Something went wrong upload image");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response).getBody();
        }
    }
}
