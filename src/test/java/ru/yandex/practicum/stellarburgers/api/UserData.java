package ru.yandex.practicum.stellarburgers.api;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Тестовые данные пользователя. Используется и для регистрации через UI, и для создания через API.
 */
public class UserData {

    private final String name;
    private final String email;
    private final String password;

    public UserData(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static UserData random() {
        return randomWithPassword("Password" + ThreadLocalRandom.current().nextInt(100000, 999999));
    }

    public static UserData randomWithPassword(String password) {
        long uniquePart = System.currentTimeMillis() + ThreadLocalRandom.current().nextInt(1000);
        return new UserData(
                "Test User",
                "stellar_test_" + uniquePart + "@yandex.ru",
                password);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String toJson() {
        return String.format(
                "{\"email\":\"%s\",\"password\":\"%s\",\"name\":\"%s\"}",
                email, password, name);
    }

    public String toLoginJson() {
        return String.format(
                "{\"email\":\"%s\",\"password\":\"%s\"}",
                email, password);
    }

    @Override
    public String toString() {
        return email;
    }
}
