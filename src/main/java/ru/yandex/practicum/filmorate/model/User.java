package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {
    Integer id;
    @Pattern(regexp = "\\S*")
    @NotBlank
    String login;
    String name;
    @NotBlank
    @Email
    String email;
    @PastOrPresent
    LocalDate birthday;
}
