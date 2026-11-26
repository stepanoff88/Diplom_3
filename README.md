# Diplom_3 — автотесты Stellar Burgers

UI-автотесты для [Stellar Burgers](https://qa-stellarburgers.education-services.ru/) на Java 11.
Тесты запускаются в Google Chrome и Яндекс.Браузере, шаги и результаты собираются в Allure-отчёт.

## Стек

- Java 11
- Selenium WebDriver 4
- JUnit 4
- REST Assured (создание и удаление тестовых пользователей через API)
- Allure (аннотации `@Step`, `@Epic`, `@Feature`, `@Story`, `@Description`)
- Maven

## Структура проекта

```
src/test/java/ru/yandex/practicum/stellarburgers
├── api
│   ├── UserApiClient.java      — создание, удаление и проверка пользователя через API
│   └── UserData.java           — тестовые данные пользователя
├── config
│   └── TestConfig.java         — адрес стенда
├── driver
│   └── WebDriverFactory.java   — фабрика драйверов (Chrome / Яндекс.Браузер)
├── pages                       — Page Object для каждой страницы
│   ├── BasePage.java           — общая шапка и вспомогательные методы
│   ├── MainPage.java           — главная страница, конструктор
│   ├── LoginPage.java          — страница входа
│   ├── RegistrationPage.java   — страница регистрации
│   ├── PasswordRecoveryPage.java — страница восстановления пароля
│   └── ProfilePage.java        — личный кабинет
└── tests
    ├── BaseTest.java           — драйвер, удаление тестовых данных, метка браузера
    ├── RegistrationTest.java   — регистрация
    ├── LoginTest.java          — четыре способа входа
    ├── ProfileTest.java        — переход в личный кабинет и обратно в конструктор
    ├── LogoutTest.java         — выход из аккаунта
    └── ConstructorTest.java    — разделы «Булки», «Соусы», «Начинки»
```

## Что проверяют тесты

**RegistrationTest**
- успешная регистрация переводит на страницу входа;
- регистрация с паролем короче шести символов не выполняется, пользователь не создаётся.

**LoginTest**
- вход по кнопке «Войти в аккаунт» на главной;
- вход через кнопку «Личный Кабинет» в шапке;
- вход через кнопку «Войти» в форме регистрации;
- вход через кнопку «Войти» в форме восстановления пароля.

**ProfileTest**
- переход в личный кабинет по кнопке «Личный Кабинет»;
- переход из личного кабинета в конструктор по ссылке «Конструктор»;
- переход из личного кабинета в конструктор по логотипу Stellar Burgers.

**LogoutTest**
- выход из аккаунта по кнопке «Выход» в личном кабинете.

**ConstructorTest**
- переход к разделу «Булки»;
- переход к разделу «Соусы»;
- переход к разделу «Начинки».

Тестовые пользователи для тестов входа, личного кабинета и выхода создаются через API
перед тестом и удаляются через API после него. Пользователь из теста регистрации
удаляется в `@After` того же теста.

## Запуск тестов

Chrome:

```
mvn clean test -Dbrowser=chrome
```

Яндекс.Браузер:

```
mvn test -Dbrowser=yandex
```

По умолчанию используется Chrome.

Запуск из IntelliJ IDEA: в Run Configuration добавьте VM option `-Dbrowser=yandex`,
чтобы прогнать тесты в Яндекс.Браузере.

Если Яндекс.Браузер установлен не в `C:\Program Files\Yandex\YandexBrowser\Application\browser.exe`,
путь можно переопределить:

```
mvn test -Dbrowser=yandex -Dyandex.browser.path="D:\Yandex\YandexBrowser\Application\browser.exe"
```

## Allure-отчёт

Результаты прогона складываются в `target/allure-results`.

Собрать отчёт:

```
mvn allure:report
```

Готовый отчёт открывается из папки `allure-report` (файл `allure-report/index.html`).
Посмотреть отчёт локально без сохранения:

```
mvn allure:serve
```

Готовый отчёт для проверки лежит в папке `allure-report`.

## Замечание о проверке некорректного пароля

На текущей версии стенда форма регистрации не показывает текстовое сообщение об ошибке
для пароля короче шести символов: при отправке формы регистрация просто не выполняется,
пользователь остаётся на странице `/register`, запрос в API не уходит. Поэтому тест
`registrationWithShortPasswordIsRejected` проверяет именно это поведение: пользователь
не покидает страницу регистрации и не создаётся (дополнительно проверяется через API).
