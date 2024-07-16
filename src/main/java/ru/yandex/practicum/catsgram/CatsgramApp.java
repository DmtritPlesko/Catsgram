package ru.yandex.practicum.catsgram;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CatsgramApp {
    public static void main(final String[] args) {
        SpringApplication.run(CatsgramApp.class, args);
//        final Gson gson = new Gson();
//        final Scanner scanner = new Scanner(System.in);
//        System.out.print("Введите JSON => ");
//        final String input = scanner.nextLine();
//        try {
//            gson.fromJson(input, Map.class);
//            System.out.println("Был введен корректный JSON");
//        } catch (JsonSyntaxException exception) {
//            System.out.println("Был введен некорректный JSON");
//        }
    }
}