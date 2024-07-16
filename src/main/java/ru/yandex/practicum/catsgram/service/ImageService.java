package ru.yandex.practicum.catsgram.service;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.catsgram.exception.ImageFileException;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.model.ImageData;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final PostService postService;

    private final Map<Long, Image> images = new HashMap<>();

    // директория для хранения изображений
    private final String imageDirectory = "C:/....";

    public List<Image> saveImages(Long id, List<MultipartFile> list) {

        return new ArrayList<>();
    }

    // загружаем данные указанного изображения с диска
    public ImageData getImageData(long imageId) throws ImageFileException {
        if (!images.containsKey(imageId)) {
            throw new NotFoundException("Изображение с id = " + imageId + " не найдено");
        }
        Image image = images.get(imageId);
        // загрузка файла с диска
        byte[] data = loadFile(image);

        return new ImageData(data, image.getOriginalFileName());
    }

    private byte[] loadFile(Image image) throws ImageFileException {
        Path path = Paths.get(image.getFilePath());
        if (Files.exists(path)) {
            try {
                return Files.readAllBytes(path);
            } catch (IOException e) {
                String error = "Ошибка чтения файла.  Id: " + image.getId()
                        + ", name: " + image.getOriginalFileName();
                throw new ImageFileException(error);
            }
        } else {
            throw new ImageFileException("Файл не найден. Id: " + image.getId()
                    + ", name: " + image.getOriginalFileName());
        }
    }
}