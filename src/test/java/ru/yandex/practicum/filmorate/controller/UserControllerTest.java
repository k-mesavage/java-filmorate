package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class UserControllerTest {

    private static final URI url = URI.create("http://localhost:8080/users");
    private final ObjectMapper objectMapper = new ObjectMapper()
            .findAndRegisterModules()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void ShouldGetAllUsers() throws IOException, InterruptedException {
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
    void ShouldAddUser() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        User user = User.builder()
                .login("login")
                .name("name")
                .email("email@email.com")
                .birthday(LocalDate.of(1946,8,20))
                .build();
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
    void ShouldAddUserWithInvalidEmail() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        User user = User.builder()
                .login("login")
                .name("name")
                .email("adkfjsd")
                .birthday(LocalDate.of(1946,8,20))
                .build();
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
    void ShouldAddUserWithEmptyLogin() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        User user = User.builder()
                .login(" ")
                .name("name")
                .email("email@email.com")
                .birthday(LocalDate.of(1946,8,20))
                .build();
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
    void ShouldAddUserWithInvalidBirthday() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        User user = User.builder()
                .login("login")
                .name("name")
                .email("email@email.com")
                .birthday(LocalDate.now().plusDays(1))
                .build();
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
    void ShouldAddUserWithEmptyName() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        User user = User.builder()
                .login("login")
                .name("")
                .email("email@email.com")
                .birthday(LocalDate.of(1946, 8, 20))
                .build();
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
        User user = User.builder()
                .login("login")
                .name("name")
                .email("email@email.com")
                .birthday(LocalDate.of(1946,8,20))
                .build();
        String requestBody = objectMapper.writeValueAsString(user);
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(url)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        User update = User.builder()
                .id(1)
                .login("update")
                .name("name")
                .email("email@email.com")
                .birthday(LocalDate.of(1946,8,20))
                .build();
        String requestBody1 = objectMapper.writeValueAsString(update);
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
        User user = User.builder()
                .login("login")
                .name("name")
                .email("email@email.com")
                .birthday(LocalDate.of(1946,8,20))
                .build();
        String requestBody = objectMapper.writeValueAsString(user);
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(url)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        User update = User.builder()
                .login("update")
                .name("name")
                .email("email@email.com")
                .birthday(LocalDate.of(1946,8,20))
                .build();
        String requestBody1 = objectMapper.writeValueAsString(update);
        HttpRequest request1 = HttpRequest
                .newBuilder()
                .uri(url)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody1))
                .build();
        HttpResponse<String> response = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertEquals(500, response.statusCode());
    }
}