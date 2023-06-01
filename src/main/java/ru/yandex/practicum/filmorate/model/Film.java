package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class Film {
    private Integer id;
    @NotNull(message = "Название не может быть пустым")
    @NotBlank(message = "Название не может быть пустым")
    private String name;
    @NotNull(message = "Описание не может быть пустым")
    @Size(message = "Описание не может быть больше 200 знаков", max = 200)
    private String description;

    @NotNull(message = "Дата релиза не может быть пустой")
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность не может быть оттрицательной")
    private Integer duration;

}
