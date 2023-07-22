package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private Set<Integer> friends = new HashSet<>();
    private Integer id;
    @Pattern(regexp = "\\S*")
    @NotBlank
    private String login;
    private String name;
    @NotBlank
    @Email
    private String email;
    @PastOrPresent
    private LocalDate birthday;

    public User(String login, String name, String email, LocalDate birthday) {
        this.login = login;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
    }
}

