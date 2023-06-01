package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {
    private Integer id;
    @Email(message = "Неверный формат Email")
    private String email;
    @NotNull(message = "Логин не может быть пустым")
    @Pattern(regexp = "\\S*", message = "Логин содержит пробелы")
    private String login;
    private String name;
    @PastOrPresent(message = "Некорректно указана дата рождения")
    private LocalDate birthday;
}
