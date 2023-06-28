package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@Builder
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
