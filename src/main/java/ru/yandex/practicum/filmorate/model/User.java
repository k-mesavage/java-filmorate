package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class User {
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
    private final Set<Integer> friends = new HashSet<>();
}
