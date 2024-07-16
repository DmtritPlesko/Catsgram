package ru.yandex.practicum.catsgram.model;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
public class Image {

    private Long id;
    @EqualsAndHashCode.Exclude

    private long postId;

    private String originalFileName;

    private String filePath;
}
