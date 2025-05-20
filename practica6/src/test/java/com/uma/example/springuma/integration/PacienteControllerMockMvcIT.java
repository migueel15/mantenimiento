package com.uma.example.springuma.integration;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uma.example.springuma.integration.base.AbstractIntegration;
import com.uma.example.springuma.model.Medico;
import com.uma.example.springuma.model.Paciente;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PacienteControllerMockMvcIT extends AbstractIntegration {
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

  private Paciente createPaciente(Long id, String dni, String nombre, String cita, Medico medico, int edad) {
    Paciente paciente = new Paciente();
    paciente.setId(id);
    paciente.setDni(dni);
    paciente.setNombre(nombre);
    paciente.setCita(cita);
    paciente.setMedico(medico);
    paciente.setEdad(edad);
    return paciente;
  }

  @Test
  @DisplayName("POST /medico POST /paciente - Creacion de paciente con medico asociado correcta")
  void testCreatePaciente() throws Exception {
    Medico medico = createMedico(1L, "12345678A", "Juan", "Cardiología");
    Paciente paciente = createPaciente(2L, "12345678B", "Migue", "10/01/2025", medico, 22);
    String jsonM = objectMapper.writeValueAsString(medico);
    String jsonP = objectMapper.writeValueAsString(paciente);

    mockMvc.perform(post("/medico")
        .contentType("application/json")
        .content(jsonM))
        .andDo(print())
        .andExpect(status().isCreated());
    mockMvc.perform(post("/paciente")
        .contentType("application/json")
        .content(jsonP))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(status().is2xxSuccessful());
    mockMvc.perform(get("/paciente/medico/1")
        .contentType("application/json"))
        .andDo(print())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0]").value(paciente));
  }

  @Test
  @DisplayName("POST /medico POST /paciente GET /paciente/{id} - Asociar un paciente a un medico correctamente")
  void testAsociarPaciente() throws Exception {
    Medico medico = createMedico(1L, "12345678A", "Juan", "Cardiología");
    Paciente paciente = createPaciente(1L, "12345678B", "Migue", "10/01/2025", medico, 22);
    String jsonM = objectMapper.writeValueAsString(medico);
    String jsonP = objectMapper.writeValueAsString(paciente);

    mockMvc.perform(post("/medico")
        .contentType("application/json")
        .content(jsonM))
        .andDo(print())
        .andExpect(status().isCreated());
    mockMvc.perform(post("/paciente")
        .contentType("application/json")
        .content(jsonP))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(status().is2xxSuccessful());
    mockMvc.perform(get("/paciente/1"))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$").value(paciente))
        .andExpect(jsonPath("$.medico.dni").value(medico.getDni()))
        .andExpect(jsonPath("$.medico.nombre").value(medico.getNombre()))
        .andExpect(jsonPath("$.medico.especialidad").value(medico.getEspecialidad()));
  }

  @Test
  @DisplayName("Editar el medico de un paciente correctamente")
  void testEditarPaciente() throws Exception {
    Medico medico1 = createMedico(1L, "12345678A", "Juan", "Cardiología");
    mockMvc.perform(post("/medico")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(medico1)))
        .andExpect(status().isCreated())
        .andExpect(status().is2xxSuccessful());

    Paciente paciente = createPaciente(1L, "12345678B", "Migue", "10/01/2025", medico1, 22);
    mockMvc.perform(post("/paciente")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(paciente)))
        .andExpect(status().isCreated())
        .andExpect(status().is2xxSuccessful());

    Medico medico2 = createMedico(2L, "12345678N", "Salma", "Podología");
    mockMvc.perform(post("/medico")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(medico2)))
        .andExpect(status().isCreated())
        .andExpect(status().is2xxSuccessful());

    paciente.setMedico(medico2);
    mockMvc.perform(put("/paciente")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(paciente)));

    mockMvc.perform(get("/paciente/medico/2")
        .contentType("application/json"))
        .andDo(print())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0]").value(paciente));
  }
}
