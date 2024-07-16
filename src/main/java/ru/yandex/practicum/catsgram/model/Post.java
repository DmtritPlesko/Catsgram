package ru.yandex.practicum.catsgram.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Data
public class Post {

    private Long id;
    @EqualsAndHashCode.Exclude

    private long authorId;

    private String description;

    private Instant postDate;
}
