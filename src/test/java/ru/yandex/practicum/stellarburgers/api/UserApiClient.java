package ru.yandex.practicum.stellarburgers.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.practicum.stellarburgers.config.TestConfig;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

/**
 * Клиент API Stellar Burgers для подготовки и удаления тестовых данных.
 */
public final class UserApiClient {

    private static final String REGISTER_PATH = "/api/auth/register";
    private static final String LOGIN_PATH = "/api/auth/login";
    private static final String USER_PATH = "/api/auth/user";

    private UserApiClient() {
    }

    @Step("Создать тестового пользователя через API: {user}")
    public static String createUser(UserData user) {
        return given()
                .baseUri(TestConfig.BASE_URL)
                .contentType("application/json")
                .body(user.toJson())
                .when()
                .post(REGISTER_PATH)
                .then()
                .statusCode(200)
                .extract()
                .path("accessToken");
    }

    @Step("Удалить тестового пользователя через API: {user}")
    public static void deleteUser(UserData user) {
        try {
            String accessToken = login(user);
            if (accessToken == null) {
                return;
            }
            given()
                    .baseUri(TestConfig.BASE_URL)
                    .header("Authorization", accessToken)
                    .when()
                    .delete(USER_PATH)
                    .then()
                    .statusCode(anyOf(is(200), is(202)));
        } catch (Exception exception) {
            System.out.println("Не удалось удалить пользователя " + user + ": " + exception.getMessage());
        }
    }

    @Step("Проверить через API, что пользователь {user} не создан")
    public static boolean isUserRegistered(UserData user) {
        return login(user) != null;
    }

    private static String login(UserData user) {
        Response response = given()
                .baseUri(TestConfig.BASE_URL)
                .contentType("application/json")
                .body(user.toLoginJson())
                .when()
                .post(LOGIN_PATH);

        if (response.statusCode() != 200) {
            return null;
        }
        return response.path("accessToken");
    }
}
