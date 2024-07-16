package ru.yandex.practicum.catsgram.service;

import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.model.Post;


import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

// Указываем, что класс PostService - является бином и его
// нужно добавить в контекст приложения
@Service
public class PostService {
    private final Map<Long, Post> posts = new HashMap<>();
    private UserService userService;

    public Collection<Post> findAll(int from, int size, String sort) {
        if (size <= 0) {
            throw new ConditionsNotMetException("Параметр" + size + " должен быть больше 0");
        }
        if (from < 0) {
            throw new ConditionsNotMetException("Параметр from должен быть больше или равено 0");
        }
        if (!(SortOrder.from(sort).equals(SortOrder.ASCENDING) || SortOrder.from(sort).equals(SortOrder.DESCENDING))) {
            throw new ConditionsNotMetException(sort + "не может быть параметром сортировки, задайте asc или desc");
        }
        return posts.values()
                .stream()
                .sorted((p0, p1) -> {
                    int comp = p0.getPostDate().compareTo(p1.getPostDate());
                    if (SortOrder.from(sort).equals(SortOrder.DESCENDING)) {
                        comp = -1 * comp;
                    }
                    return comp;
                }).skip(from).limit(size).collect(Collectors.toList());
    }

    public Post create(Post post) {

        if (userService.fiendById(post.getAuthorId()).isEmpty()) {
            throw new ConditionsNotMetException("Автор с идентификатором " + post.getAuthorId() + " не найден");
        }

        if (post.getDescription() == null || post.getDescription().isBlank()) {
            throw new ConditionsNotMetException("Описание не может быть пустым");
        }

        post.setId(getNextId());
        post.setPostDate(Instant.now());
        posts.put(post.getId(), post);
        return post;
    }

    public Post update(Post newPost) {
        if (newPost.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }
        if (posts.containsKey(newPost.getId())) {
            Post oldPost = posts.get(newPost.getId());
            if (newPost.getDescription() == null || newPost.getDescription().isBlank()) {
                throw new ConditionsNotMetException("Описание не может быть пустым");
            }
            oldPost.setDescription(newPost.getDescription());
            return oldPost;
        }
        throw new NotFoundException("Пост с id = " + newPost.getId() + " не найден");
    }

    public Post getPostById(Long id) {
        if (checkContainsPost(id)) {
            return posts.get(id);
        }
        throw new NotFoundException("Пость с идентификатором " + id + " не найден");
    }

    private long getNextId() {
        long currentMaxId = posts.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    private boolean checkContainsPost(Long id) {
        if (posts.containsKey(id)) {
            return true;
        }
        return false;
    }
}
