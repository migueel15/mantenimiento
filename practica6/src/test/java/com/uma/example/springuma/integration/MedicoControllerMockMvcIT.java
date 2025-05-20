package com.uma.example.springuma.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uma.example.springuma.integration.base.AbstractIntegration;
import com.uma.example.springuma.model.Medico;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


public class MedicoControllerMockMvcIT extends AbstractIntegration {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    private Medico createMedico(Long id, String dni, String nombre, String especialidad) {
        Medico medico = new Medico();
        medico.setId(id);
        medico.setDni(dni);
        medico.setNombre(nombre);
        medico.setEspecialidad(especialidad);
        return medico;
    }
    @Test
    @DisplayName("POST /medico GET /medico/{id} - Medico creado y añadido correctamente")
    void testCreateMedico() throws Exception {
        Medico medico = createMedico(1L, "12345678A", "Juan", "Cardiología");
        String json = objectMapper.writeValueAsString(medico);

        mockMvc.perform(post("/medico")
                .contentType("application/json")
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(get("/medico/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.dni").value("12345678A"))
            .andExpect(jsonPath("$.nombre").value("Juan"))
            .andExpect(jsonPath("$.especialidad").value("Cardiología"))
            .andReturn();

    }
    @Test
    @DisplayName("POST /medico PUT /medico - Editar un medico correctamente")
    void testEditMedico() throws Exception {
        Medico medico = createMedico(1L, "12345678A", "Juan", "Cardiología");
        String json = objectMapper.writeValueAsString(medico);

        mockMvc.perform(post("/medico")
                .contentType("application/json")
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated());

        medico.setNombre("Manuel");

        mockMvc.perform(put("/medico")
            .contentType("application/json")
            .content(json))
            .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/medico/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.dni").value("12345678A"))
            .andExpect(jsonPath("$.nombre").value("Juan"))
            .andExpect(jsonPath("$.especialidad").value("Cardiología"))
            .andReturn();

    }
    @Test
    @DisplayName("POST /medico DELETE /medico/{id} - Eliminar un medico correctamente")
    void testDeleteMedico() throws Exception {
        Medico medico = createMedico(1L, "12345678A", "Juan", "Cardiología");
        String json = objectMapper.writeValueAsString(medico);

        mockMvc.perform(post("/medico")
                .contentType("application/json")
                .content(json))
            .andDo(print())
            .andExpect(status().isCreated());
        mockMvc.perform(delete("/medico/1"))
            .andDo(print())
            .andExpect(status().is2xxSuccessful());
        mockMvc.perform(get("/medico/1"))
            .andDo(print())
            .andExpect(status().is5xxServerError());
    }
    @Test
    @DisplayName("POST /medico GET /medico - Obtener un medico concreto (por dni)")
    void testObtenerMedico() throws Exception {
        Medico medico = createMedico(1L, "12345678A", "Juan", "Cardiología");
        String json = objectMapper.writeValueAsString(medico);

        mockMvc.perform(post("/medico")
                .contentType("application/json")
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated());
        mockMvc.perform(get("/medico/dni/12345678A"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.dni").value("12345678A"))
            .andExpect(jsonPath("$.nombre").value("Juan"))
            .andExpect(jsonPath("$.especialidad").value("Cardiología"))
            .andReturn();
    }


}