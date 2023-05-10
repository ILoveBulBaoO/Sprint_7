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

import static org.junit.Assert.*;

public class CourierCreatePositiveTests {

    private CourierClient courierClient;
    private Integer courierId;
    private Courier courier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandomCourier();
    }

    @After
    public void cleanUp() {
        if (courierId != null) {
            courierClient.delete(courierId);
        }
    }

    // можно создать курьера, заполнив все обязательные поля
    @DisplayName("Courier can be created with all required fields")
    @Test
    public void courierCanBeCreatedTest() {

        // вызвать метод создания курьера
        ValidatableResponse createResponse = courierClient.create(courier);

        // проверить статус код, проверить боди
        int statusCode = createResponse.extract().statusCode();
        assertEquals(201, statusCode);

        boolean isCourierCreated = createResponse.extract().path("ok");
        assertTrue(isCourierCreated);

        // авторизуемся созданным аккаунтом и получаем id для дальнейшего удаления в @After
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");
    }
}
