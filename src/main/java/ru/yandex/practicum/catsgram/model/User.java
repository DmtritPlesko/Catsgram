package ru.yandex.practicum.catsgram.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.Instant;

@Data
@EqualsAndHashCode(exclude = {"email"})
public class User {

    private Long id;

    private String userName;

    private String email;

    private String password;

    private Instant registrationDate;



}
