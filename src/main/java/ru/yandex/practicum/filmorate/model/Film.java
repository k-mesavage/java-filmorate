package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.validator.ReleaseDate;

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
    @ReleaseDate
    LocalDate releaseDate;
    @Positive
    Integer duration;

}
