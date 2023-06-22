package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Component
public class User {
    public Set<Integer> friends = new HashSet<>();
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

