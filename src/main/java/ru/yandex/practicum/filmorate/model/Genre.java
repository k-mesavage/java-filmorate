package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
public class Genre {
   private final int id;
   private final String name;
}