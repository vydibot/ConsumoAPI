package com.acm.consumoapi.service;

import com.acm.consumoapi.logic.PersonajeDTO;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private Logger logger = LoggerFactory.getLogger(PersonajeService.class);

    private RestTemplate restTemplate;
    private HttpClient client;

    public PersonajeService(@Autowired HttpClient client, @Autowired RestTemplate restTemplate) {
        this.client = client;
        this.restTemplate = restTemplate;
    }

//    public void getPersonajeByNameUsinRest(String nombre){
//        StringBuilder str = new StringBuilder();
//
//        str.append("https://rickandmortyapi.com/api/character/");
//        str.append("?name="+nombre);
//        PersonajeDTO p=restTemplate.getForObject(URI.create(str.toString()), PersonajeDTO.class);
//        logger.info(p.toString());
//    }

    public PersonajeDTO getPersonajeByName(String nombre) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDefaultPrettyPrinter(new DefaultPrettyPrinter());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        StringBuilder str = new StringBuilder();
        str.append("https://rickandmortyapi.com/api/character/");
        str.append("?name=" + nombre);
        logger.info(str.toString());
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(str.toString())).GET().build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info(response.body());

            JsonElement jsonElement = gson.fromJson(response.body(), JsonElement.class);

            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                if (jsonObject.has("results")) {
                    JsonArray resultsArray = jsonObject.getAsJsonArray("results");

                    if (resultsArray.size() > 0) {
                        JsonObject firstResult = resultsArray.get(0).getAsJsonObject();

                        return gson.fromJson(firstResult, PersonajeDTO.class);
                    }
                }
            }
            logger.warn("No se encontraron personajes para el nombre: {}", nombre);
            return null;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}



