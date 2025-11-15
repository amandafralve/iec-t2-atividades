package com.amanda.iec_t2.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/plants", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlantsController {

    
    private final Map<Integer, Map<String, String>> plants = new HashMap<>(Map.of(
        1, Map.of("popular", "Comigo Ninguém Pode", "cientifico", "Dieffenbachia seguine"),
        2, Map.of("popular", "Espada de São Jorge", "cientifico", "Dracaena trifasciata"),
        3, Map.of("popular", "Costela de Adão", "cientifico", "Monstera adansonii")
    ));

    // GET ALL
    @GetMapping
    public ResponseEntity<Map<Integer, Map<String, String>>> getPlants() {
        return ResponseEntity.ok(plants);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, String>> getPlantById(@PathVariable Integer id) {

        Map<String, String> plant = plants.get(id);

        if (plant == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                    "message", "Planta não encontrada",
                    "id", id.toString()
                ));
        }

        return ResponseEntity.ok(
            Map.of(
                "id", id.toString(),
                "popular", plant.get("popular"),
                "cientifico", plant.get("cientifico")
            )
        );
    }

    // POST - ADD PLANT
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> addPlant(
            @RequestBody Map<String, String> requestPlant) {

        Integer cod = Integer.parseInt(requestPlant.get("id"));
        String pop = requestPlant.get("popular");
        String cient = requestPlant.get("cientifico");

        plants.put(cod, Map.of(
            "popular", pop,
            "cientifico", cient
        ));

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Map.of(
                "id", cod,
                "popular", pop,
                "cientifico", cient,
                "message", "Planta adicionada com sucesso"
            ));
    }
}
