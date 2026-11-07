package com.example.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de arranque de la aplicación Spring Boot.
 */
@SpringBootApplication
public class DemoApplication {

    /**
     * Constructor por defecto.
     */
    protected DemoApplication() {
    }

    /**
     * Método main.
     * @param args Parámetros de la CLI.
     */
    public static void main(final String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
