package com.acm.consumoapi.presentacion.controller;

import com.acm.consumoapi.logic.PersonajeDTO;
import com.acm.consumoapi.service.PersonajeService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private PersonajeService personajeService;
    private  PersonajeDTO envioPersonajeDTO;

    private Logger logger = LoggerFactory.getLogger(IndexController.class);

    //    @RequestMapping(path = "home", method = RequestMethod.GET)
    @GetMapping("/holo")
    public String indexController(Model model) {
//        Map<String, Object> map = new HashMap<>();
        return "index";
    }

    @GetMapping("/envio")
    public String envio(@RequestParam String personaje, Model model) {

        envioPersonajeDTO = personajeService.getPersonajeByName(personaje);
        model.addAttribute("personaje", envioPersonajeDTO);
        logger.info(envioPersonajeDTO.toString());
        return "mostrar";
    }
}
