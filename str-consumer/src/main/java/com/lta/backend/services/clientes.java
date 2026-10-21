package com.lta.backend.services;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
@Slf4j
public class clientes {
    
    public String ordenar(String message){

        String msg = message.toLowerCase();
        String response;

        if(msg.contains("vegan")){
            response = "Vegan menu: tofu bowl, quinoa salad, green smoothie";
        } 
        else if(msg.contains("vegetarian")){
            response = "Vegetarian menu: pesto pasta, margherita pizza, caprese salad";
        } 
        else if(msg.contains("meat") || msg.contains("chicken") || msg.contains("beef")){
            response = "Meat menu: burger, beef tacos, roasted chicken";
        } 
        else {
            response = "General menu: pasta, salad, soup";
        }

        log.info("Generated response: {}", response);

        return response;
    }

    public String consultar(String id) {
        Map<String, String> datos = Map.of(
            "1", "Cliente 1: Ana López, ana@correo.com",
            "2", "Cliente 2: Luis Pérez, luis@correo.com",
            "3", "Cliente 3: María Ruiz, maria@correo.com"
        );
        String resultado = datos.getOrDefault(id.trim(), "Cliente no encontrado: " + id);
        log.info("Consulta cliente {} -> {}", id, resultado);
        return resultado;
    }

}
