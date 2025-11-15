package com.amanda.iec_t2.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc

public class PlantsControllerTest {
    @Autowired
    private MockMvc mockMvc;


    // get /plants
    @Test
    void shouldReturnAllPlants() throws Exception{
        mockMvc.perform(get("/plants"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.1.popular").value("Comigo Ninguém Pode"))
            .andExpect(jsonPath("$.2.cientifico").value("Dracaena trifasciata"))
            .andExpect(jsonPath("$.3.popular").value("Costela de Adão"));
    }

    // get plants/id
    @Test
    void shouldReturnPlantById() throws Exception{
        mockMvc.perform(get("/plants/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.popular").value("Comigo Ninguém Pode"))
            .andExpect(jsonPath("$.cientifico").value("Dieffenbachia seguine"));
    }

    // post
    @Test
    void shouldPostPlant() throws Exception{
        String newPlantJson =  "{ \"id\": \"4\", \"popular\": \"Jiboia\", \"cientifico\": \"Epipremnum aureum\" }";
            mockMvc.perform(post("/plants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newPlantJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.popular").value("Jiboia"))
                .andExpect(jsonPath("$.cientifico").value("Epipremnum aureum"))
                .andExpect(jsonPath("$.message").value("Planta adicionada com sucesso"));
            
    }

}
