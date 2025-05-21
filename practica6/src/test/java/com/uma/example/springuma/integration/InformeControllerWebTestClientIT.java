package com.uma.example.springuma.integration;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Calendar;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uma.example.springuma.model.Imagen;
import com.uma.example.springuma.model.Informe;
import com.uma.example.springuma.model.Medico;
import com.uma.example.springuma.model.Paciente;

import jakarta.annotation.PostConstruct;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class InformeControllerWebTestClientIT {

  @LocalServerPort
  private int port;

  private WebTestClient client;
  private final ObjectMapper objectMapper = new ObjectMapper();

  @PostConstruct
  public void init() {
    client = WebTestClient.bindToServer()
        .baseUrl("http://localhost:" + port)
        .responseTimeout(Duration.ofSeconds(15))
        .build();
  }

  private Medico createMedico(Long id, String dni, String nombre, String especialidad) {
    Medico medico = new Medico();
    medico.setId(id);
    medico.setDni(dni);
    medico.setEspecialidad(especialidad);
    medico.setNombre(nombre);
    return medico;
  }

  private Imagen createImagen(Long id, Calendar fecha, Paciente paciente, byte[] file_content) {
    Imagen imagen = new Imagen();
    imagen.setId(id);
    imagen.setFecha(fecha);
    imagen.setNombre("Imagen " + paciente);
    imagen.setPaciente(paciente);
    imagen.setFile_content(file_content);
    return imagen;
  }

  private Paciente createPaciente(Long id, String dni, String name, String cita, Medico medico, int edad) {
    Paciente paciente = new Paciente();
    paciente.setId(id);
    paciente.setDni(dni);
    paciente.setNombre(name);
    paciente.setCita(cita);
    paciente.setEdad(edad);
    paciente.setMedico(medico);
    return paciente;
  }

  @Test
  @DisplayName("Informe se crea con exito")
  public void crearInformeEsperaStatusCreated() throws Exception {

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

    // Subimos la imagen asociada al paciente
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

    // Solicitamos la prediccion de la imagen (id 1)
    Long imagenId = 1L;
    byte[] predictionBytes = client.get().uri("/imagen/predict/" + imagenId)
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
        })
        .returnResult().getResponseBody();

    // Deserializo la cadena de bytes a string con el resultado de la prediccion
    String prediction = new String(predictionBytes, StandardCharsets.UTF_8);

    Imagen imagenResponse = client.get().uri("/imagen/info/1")
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(Imagen.class)
        .returnResult()
        .getResponseBody();

    // Creamos el informe
    Informe informe = new Informe();
    informe.setPrediccion(prediction);
    informe.setImagen(imagenResponse);
    informe.setContenido("Informe de la imagen " + imagenResponse.getId());

    // Enviamos el informe al servidor
    client.post().uri("/informe")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(informe)
        .exchange()
        .expectStatus()
        .isCreated();
  }

  @Test
  @DisplayName("Informe se borra con exito")
  public void informeBorradoEsperaNoContent() throws Exception {

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

    // Subimos la imagen asociada al paciente
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

    // Solicitamos la prediccion de la imagen (id 1)
    Long imagenId = 1L;
    byte[] predictionBytes = client.get().uri("/imagen/predict/" + imagenId)
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
        })
        .returnResult().getResponseBody();

    // Deserializo la cadena de bytes a string con el resultado de la prediccion
    String prediction = new String(predictionBytes, StandardCharsets.UTF_8);

    Imagen imagenResponse = client.get().uri("/imagen/info/1")
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(Imagen.class)
        .returnResult()
        .getResponseBody();

    // Creamos el informe
    Informe informe = new Informe();
    informe.setPrediccion(prediction);
    informe.setImagen(imagenResponse);
    informe.setContenido("Informe de la imagen " + imagenResponse.getId());

    // Enviamos el informe al servidor
    client.post().uri("/informe")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(informe)
        .exchange()
        .expectStatus()
        .isCreated();

    // Confirmamos que el informe exista en la base de datos
    client.get().uri("/informe/1")
        .exchange()
        .expectBody()
        .jsonPath("$.id").isEqualTo(1);

    // Borramos el informe
    client.delete().uri("/informe/1")
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectStatus()
        .isNoContent();
  }
}
