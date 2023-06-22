package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class UserControllerTest {

    private static final URI url = URI.create("http://localhost:8080/users");
    private static User user;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .findAndRegisterModules()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @BeforeEach
    void beforeEach() {
        user = new User(
                "login"
                ,"name"
                ,"email@email.com",
                LocalDate.of(1946, 8,20));
    }

    @Test
    void shouldGetAllUsers() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(url)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    void shouldAddUser() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String requestBody = objectMapper.writeValueAsString(user);
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(url)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    void shouldAddUserWithInvalidEmail() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        user.setEmail("ssdfsf");
        String requestBody = objectMapper.writeValueAsString(user);
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(url)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }

    @Test
    void shouldAddUserWithEmptyLogin() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        user.setLogin(" ");
        String requestBody = objectMapper.writeValueAsString(user);
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(url)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }

    @Test
    void shouldAddUserWithInvalidBirthday() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        user.setBirthday(LocalDate.now().plusDays(1));
        String requestBody = objectMapper.writeValueAsString(user);
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(url)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }

    @Test
    void shouldAddUserWithEmptyName() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        user.setName("");
        String requestBody = objectMapper.writeValueAsString(user);
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(url)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    void shouldUpdateUser() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String requestBody = objectMapper.writeValueAsString(user);
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(url)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        user.setId(1);
        user.setName("update");
        String requestBody1 = objectMapper.writeValueAsString(user);
        HttpRequest request1 = HttpRequest
                .newBuilder()
                .uri(url)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody1))
                .build();
        HttpResponse<String> response = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    void shouldUpdateEmptyUser() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String requestBody = objectMapper.writeValueAsString(user);
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(url)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        User update = new User(
                "update"
        ,"name"
        ,"email@email.com"
        ,LocalDate.of(1992,11,1));
        String requestBody1 = objectMapper.writeValueAsString(update);
        HttpRequest request1 = HttpRequest
                .newBuilder()
                .uri(url)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody1))
                .build();
        HttpResponse<String> response = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, response.statusCode());
    }
}