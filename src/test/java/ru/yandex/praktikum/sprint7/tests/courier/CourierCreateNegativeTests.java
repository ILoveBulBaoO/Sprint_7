package ru.yandex.praktikum.sprint7.tests.courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.sprint7.courier.Courier;
import ru.yandex.praktikum.sprint7.courier.CourierClient;
import ru.yandex.praktikum.sprint7.courier.CourierCredentials;
import ru.yandex.praktikum.sprint7.courier.CourierGenerator;

import static org.junit.Assert.assertEquals;

public class CourierCreateNegativeTests {

    private CourierClient courierClient;
    private Courier courierWithoutPassword;
    private Courier courierWithoutLogin;
    private Courier courierDefault;
    private Courier courierWithExistLogin;
    private Integer courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courierWithoutPassword = CourierGenerator.getRandomWithoutPassword();
        courierWithoutLogin = CourierGenerator.getRandomWithoutLogin();
        courierDefault = CourierGenerator.getDefaultCourier();
        courierWithExistLogin = CourierGenerator.getExistLoginCourier();
    }

    @After
    public void cleanUp() {
        if (courierId != null) {
            courierClient.delete(courierId);
        }
    }

    @Test
    @DisplayName("Courier can not be created without password")
    public void courierCanNotBeCreatedWithoutPasswordTest() {

        // вызвать метод создание курьера
        ValidatableResponse createResponse = courierClient.create(courierWithoutPassword);

        // проверить, что статус код 400 и боди
        int statusCode = createResponse.extract().statusCode();
        assertEquals(400, statusCode);

        String actualMessage = createResponse.extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", actualMessage);
    }

    @Test
    @DisplayName("Courier can not be created without login")
    public void courierCanNotBeCreatedWithoutLoginTest() {

        // вызвать метод создание курьера
        ValidatableResponse createResponse = courierClient.create(courierWithoutLogin);

        // проверить, что статус код 400 и боди
        int statusCode = createResponse.extract().statusCode();
        assertEquals(400, statusCode);

        String actualMessage = createResponse.extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", actualMessage);
    }

    @Test
    @DisplayName("Courier can not be created with existing courier")
    public void courierCanNotBeCreatedWithExistingCourierTest() {

        // создать курьера
        courierClient.create(courierDefault);
        // вызвать метод создания курьера
        ValidatableResponse createResponse = courierClient.create(courierDefault);

        // авторизуемся созданным аккаунтом и получаем id для дальнейшего удаления в @After
        courierId = courierClient.login(CourierCredentials.from(courierDefault)).extract().path("id");

        // проверить, что статус 409 и боди
        int statusCode = createResponse.extract().statusCode();
        assertEquals(409, statusCode);

        String actualMessage = createResponse.extract().path("message");
        assertEquals("Этот логин уже используется. Попробуйте другой.", actualMessage);
    }

    @Test
    @DisplayName("Courier can not be created with existing login")
    public void courierCanNotBeCreatedWithExistingLoginTest() {

        // создать курьера
        courierClient.create(courierWithExistLogin);
        // вызвать метод создания курьера
        ValidatableResponse createResponse = courierClient.create(courierWithExistLogin);

        // авторизуемся созданным аккаунтом и получаем id для дальнейшего удаления в @After
        courierId = courierClient.login(CourierCredentials.from(courierWithExistLogin)).extract().path("id");

        // проверить, что статус 409 и боди
        // в ТЗ ошибка: Этот логин уже используется, но на самом деле выводит: Этот логин уже используется. Попробуйте другой.
        int statusCode = createResponse.extract().statusCode();
        assertEquals(409, statusCode);

        String actualMessage = createResponse.extract().path("message");
        assertEquals("Этот логин уже используется. Попробуйте другой.", actualMessage);
    }
}
