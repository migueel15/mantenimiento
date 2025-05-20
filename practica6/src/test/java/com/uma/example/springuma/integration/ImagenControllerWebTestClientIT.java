package com.uma.example.springuma.integration;

import java.io.File;
import java.time.Duration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uma.example.springuma.model.Medico;
import com.uma.example.springuma.model.Paciente;

import jakarta.annotation.PostConstruct;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImagenControllerWebTestClientIT {

  @LocalServerPort
  private int port;

  @Autowired
  private ObjectMapper objectMapper;

  private WebTestClient client;

  @PostConstruct
  public void setupClient() {
    client = WebTestClient.bindToServer()
        .baseUrl("http://localhost:" + port)
        .responseTimeout(Duration.ofSeconds(10))
        .build();
  }

  private Medico createMedico(Long id, String dni, String nombre, String especialidad) {
    Medico medico = new Medico();
    medico.setId(id);
    medico.setNombre(nombre);
    medico.setDni(dni);
    medico.setEspecialidad(especialidad);
    return medico;
  }

  private Paciente createPaciente(Long id, String dni, String name, String cita, Medico medico, int edad) {
    Paciente paciente = new Paciente();
    paciente.setId(id);
    paciente.setNombre(name);
    paciente.setEdad(edad);
    paciente.setDni(dni);
    paciente.setCita(cita);
    paciente.setMedico(medico);
    return paciente;
  }

  @Test
  @DisplayName("Subida de imagen funciona correctamente")
  public void subirImagenRetornaEventoCreado201() throws Exception {

    // Creamos un medico
    Medico medico = createMedico(1L, "12345678X", "Josemi", "Neurologo");
    client.post().uri("/medico")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(medico)
        .exchange()
        .expectStatus()
        .isCreated();

    // Creamos un paciente
    Paciente paciente = createPaciente(1L, "87654321Y", "NicoKW", "01/01/25", medico, 36);
    client.post().uri("/paciente")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(paciente)
        .exchange()
        .expectStatus()
        .isCreated();

    // Subimos la imagen
    File imagen = new File("./src/test/resources/healthy.png");
    String pacienteSerializado = objectMapper.writeValueAsString(paciente);
    MultipartBodyBuilder builder = new MultipartBodyBuilder();

    builder.part("image", new FileSystemResource(imagen));
    builder.part("paciente", pacienteSerializado).header("Content-Type", MediaType.APPLICATION_JSON_VALUE);

    client.post().uri("/imagen")
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(BodyInserters.fromMultipartData(builder.build()))
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody()
        .jsonPath("$.response").isEqualTo("file uploaded successfully : healthy.png");

  }

  @Test
  @DisplayName("Solicitud de prediccion sobre imagen")
  public void solicitarPrediccionDeImagenEsperaStatusYScore() throws Exception {
    // Creamos medico
    Medico medico = createMedico(1L, "12345678X", "Josemi", "Neurologo");
    client.post().uri("/medico")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(medico)
        .exchange()
        .expectStatus()
        .isCreated();

    // Creamos paciente
    Paciente paciente = createPaciente(1L, "87654321Y", "NicoKW", "01/01/25", medico, 36);
    client.post().uri("/paciente")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(paciente)
        .exchange()
        .expectStatus()
        .isCreated();

    // Subimos la imagen
    File imagen = new File("./src/test/resources/healthy.png");
    String pacienteSerializado = objectMapper.writeValueAsString(paciente);
    MultipartBodyBuilder builder = new MultipartBodyBuilder();

    builder.part("image", new FileSystemResource(imagen));
    builder.part("paciente", pacienteSerializado).header("Content-Type", MediaType.APPLICATION_JSON_VALUE);

    // Creamos imagen asociada al paciente
    client.post().uri("/imagen")
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(BodyInserters.fromMultipartData(builder.build()))
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody()
        .jsonPath("$.response").isEqualTo("file uploaded successfully : healthy.png");

    // Solicitamos la prediccion de la imagen (id 1)
    Long imagenId = 1L;
    client.get().uri("/imagen/predict/" + imagenId)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody()
        .jsonPath("$.score").isNumber()
        .jsonPath("$.status").value(value -> {
          String status = value.toString();
          if (!status.equals("Cancer") && !status.equals("Not cancer")) {
            throw new AssertionError("Not expected status for prediction");
          }
        });

  }

}
