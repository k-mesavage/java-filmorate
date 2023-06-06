package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class FilmControllerTest {

    private static final URI url = URI.create("http://localhost:8080/films");
    private final ObjectMapper objectMapper = new ObjectMapper()
            .findAndRegisterModules()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    private static HttpClient client;

    private static Film film;

    @BeforeEach
    void beforeEach() {
        client = HttpClient.newHttpClient();
        film = Film.builder()
                .name("name")
                .description("description")
                .releaseDate(LocalDate.of(1999,8,20))
                .duration(150)
                .build();
    }

    @Test
    void shouldGetAllFilms() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(url)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    void shouldUpdateFilm() throws IOException, InterruptedException {
        String requestBody = objectMapper.writeValueAsString(film);
        HttpRequest request = HttpRequest
                .newBuilder()
                .header("Content-Type", "application/json")
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        film.setId(1);
        film.setName("update");
        String requestBody1 = objectMapper.writeValueAsString(film);
        HttpRequest request1 = HttpRequest
                .newBuilder()
                .header("Content-Type", "application/json")
                .uri(url)
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody1))
                .build();
        HttpResponse<String> response = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    void shouldUpdateEmptyFilm() throws IOException, InterruptedException {
        film.setId(999);
        String requestBody = objectMapper.writeValueAsString(film);
        HttpRequest request = HttpRequest
                .newBuilder()
                .header("Content-Type", "application/json")
                .uri(url)
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(500, response.statusCode());
    }

    @Test
    void shouldAddFilm() throws IOException, InterruptedException {
        String requestBody = objectMapper.writeValueAsString(film);
        HttpRequest request = HttpRequest
                .newBuilder()
                .header("Content-Type", "application/json")
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    void shouldAddFilmWithInvalidReleaseDate() throws IOException, InterruptedException {
        film.setReleaseDate(LocalDate.of(1835,1,1));
        String requestBody = objectMapper.writeValueAsString(film);
        HttpRequest request = HttpRequest
                .newBuilder()
                .header("Content-Type", "application/json")
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }

    @Test
    void shouldAddFilmWithInvalidName() throws IOException, InterruptedException {
        film.setName(" ");
        String requestBody = objectMapper.writeValueAsString(film);
        HttpRequest request = HttpRequest
                .newBuilder()
                .header("Content-Type", "application/json")
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }

    @Test
    void shouldAddFilmWithInvalidDuration() throws IOException, InterruptedException {
        film.setDuration(-150);
        String requestBody = objectMapper.writeValueAsString(film);
        HttpRequest request = HttpRequest
                .newBuilder()
                .header("Content-Type", "application/json")
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }

    @Test
    void shouldAddFilmWithInvalidDescription() throws IOException, InterruptedException {
        film.setDescription("descriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescription" +
                "descriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescription" +
                "descriptiondescriptiondescriptiondescriptiondescriptiondescription");
        String requestBody = objectMapper.writeValueAsString(film);
        HttpRequest request = HttpRequest
                .newBuilder()
                .header("Content-Type", "application/json")
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }
}