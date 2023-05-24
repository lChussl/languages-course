package com.example.code.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RouterController {
    /**
     * Controller to redirect some test views inside the backend
     */
    @Autowired
    public RouterController() { }

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
