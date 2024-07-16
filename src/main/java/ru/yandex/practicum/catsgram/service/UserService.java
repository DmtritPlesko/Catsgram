package ru.yandex.practicum.catsgram.service;

import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.DuplicatedDataException;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.model.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final Map<Long, User> users = new HashMap<>();

    public Collection<User> allUsers() {
        return users.values();
    }

    public User createNewUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new ConditionsNotMetException("Имейл должен быть указан");
        } else if (containsUserByEmail(user.getEmail())) {
            throw new DuplicatedDataException("Этот имейл уже используется");
        }

        user.setRegistrationDate(Instant.now());
        user.setId(getNextId());
        users.put(user.getId(), user);

        return user;
    }

    public User update(User user) {
        if (user.getUserName() == null
                && user.getEmail() == null
                && user.getId() == null) {
            return user;
        } else if (user.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }

        if (users.containsKey(user.getId())) {
            String oldEmail = users.get(user.getId()).getEmail();
            String userEmail = user.getEmail();

            if (!(oldEmail.equals(userEmail))) {
                throw new DuplicatedDataException("Этот имейл уже используется");
            }

            Instant instant = users.get(user.getId()).getRegistrationDate();
            user.setRegistrationDate(instant);

            users.put(user.getId(), user);
            return user;
        }
        throw new NotFoundException("Пользователь не найден");
    }

    public User getUserById(long id) {
        if (!fiendById(id).isEmpty()) {
            return users.get(id);
        }
        throw new NotFoundException("Пользователь с идентификатором " + id + " не найден");
    }

    public Optional<User> fiendById(long id) {
        return Optional.ofNullable(users.get(id));
    }

    private long getNextId() {
        long maxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++maxId;
    }

    private boolean containsUserByEmail(String email) {
        var flag = users.values().stream()
                .map(temp -> temp.getEmail())
                .filter(temp -> !temp.equals(email))
                .toList();

        if (flag.size() == users.values().size()) {
            return false;
        }
        return true;
    }
}
