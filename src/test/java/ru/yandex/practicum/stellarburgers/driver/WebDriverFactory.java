package ru.yandex.practicum.stellarburgers.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.manager.SeleniumManager;
import org.openqa.selenium.manager.SeleniumManagerOutput;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Locale;

/**
 * Фабрика драйверов. Браузер выбирается параметром {@code -Dbrowser=chrome|yandex}.
 */
public final class WebDriverFactory {

    private static final String CHROME = "chrome";
    private static final String YANDEX = "yandex";
    private static final String DEFAULT_YANDEX_BINARY =
            "C:\\Program Files\\Yandex\\YandexBrowser\\Application\\browser.exe";

    private WebDriverFactory() {
    }

    public static WebDriver createDriver() {
        String browser = System.getProperty("browser", CHROME).trim().toLowerCase(Locale.ROOT);
        switch (browser) {
            case CHROME:
                return new ChromeDriver(chromeOptions());
            case YANDEX:
                return createYandexDriver();
            default:
                throw new IllegalArgumentException(
                        "Неподдерживаемый браузер: " + browser + ". Доступны: chrome, yandex.");
        }
    }

    private static ChromeOptions chromeOptions() {
        ChromeOptions options = baseOptions();
        String browserVersion = System.getProperty("chrome.browser.version");
        if (browserVersion != null && !browserVersion.isBlank()) {
            options.setBrowserVersion(browserVersion);
        }
        return options;
    }

    private static WebDriver createYandexDriver() {
        ChromeOptions options = baseOptions();
        options.setBinary(yandexBinary());

        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(resolveChromeDriverPath()))
                .withBuildCheckDisabled(true)
                .build();
        return new ChromeDriver(service, options);
    }

    private static String resolveChromeDriverPath() {
        SeleniumManagerOutput.Result result = SeleniumManager.getInstance().getBinaryPaths(
                Arrays.asList("--browser", CHROME));
        String driverPath = result.getDriverPath();
        if (driverPath == null || driverPath.isBlank()) {
            throw new IllegalStateException(
                    "Не удалось получить chromedriver для Яндекс.Браузера: " + result.getMessage());
        }
        return driverPath;
    }

    private static ChromeOptions baseOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--lang=ru-RU");
        return options;
    }

    private static String yandexBinary() {
        String configuredPath = System.getProperty("yandex.browser.path", DEFAULT_YANDEX_BINARY);
        Path binary = Paths.get(configuredPath);
        if (!Files.exists(binary)) {
            throw new IllegalStateException(
                    "Не найден Яндекс.Браузер по пути " + configuredPath
                            + ". Укажите актуальный путь в параметре -Dyandex.browser.path.");
        }
        return binary.toString();
    }
}
