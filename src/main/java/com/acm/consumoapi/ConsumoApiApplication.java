package com.acm.consumoapi;

import com.acm.consumoapi.service.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsumoApiApplication  {

    public static void main(String[] args) {
        SpringApplication.run(ConsumoApiApplication.class, args);
    }

}
