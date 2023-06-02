package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
public class Film {
    Integer id;
    @NotBlank
    String name;
    @Length(max = 200)
    String description;
    LocalDate releaseDate;
    @Positive
    Integer duration;

}
