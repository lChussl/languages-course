package com.example.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class CodeApplication implements CommandLineRunner {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(CodeApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            System.out.println("La conexión a la base de datos es correcta.");
        } catch (Exception e) {
            System.out.println("Error al conectar a la base de datos:");
            e.printStackTrace();
        }
    }
}

