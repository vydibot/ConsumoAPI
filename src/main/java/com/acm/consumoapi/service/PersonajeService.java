package com.acm.consumoapi.service;

import com.acm.consumoapi.logic.PersonajeDTO;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acm.consumoapi.persistencia.entity.Personaje;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class PersonajeService {

    private  Logger logger = LoggerFactory.getLogger(PersonajeService.class);

    private  RestTemplate restTemplate;

    private  HttpClient client;

    public PersonajeService(@Autowired HttpClient client, @Autowired RestTemplate restTemplate) {
        this.client = client;
        this.restTemplate = restTemplate;
    }

    public void getPersonajeByNameUsinRest(String nombre){
        StringBuilder str = new StringBuilder();

        str.append("https://rickandmortyapi.com/api/character/");
        str.append("?name="+nombre);
        PersonajeDTO p=restTemplate.getForObject(URI.create(str.toString()), PersonajeDTO.class);
        logger.info(p.toString());
    }

    public void getPersonajeByName(String nombre){
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDefaultPrettyPrinter(new DefaultPrettyPrinter());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        StringBuilder str = new StringBuilder();
        str.append("https://rickandmortyapi.com/api/character/");
        str.append("?name="+nombre);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(str.toString())).GET().build();

        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info(response.body());
            PersonajeDTO p = gson.fromJson(response.body(), PersonajeDTO.class);
            mapper.writeValue(new File("personaje.json"), p);
            logger.info(p.toString());
        }catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
    }


}
