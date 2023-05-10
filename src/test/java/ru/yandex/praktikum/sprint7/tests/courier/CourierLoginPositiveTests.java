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

public class CourierLoginPositiveTests {

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



    @DisplayName("Courier can login")
    @Test
    public void courierCanLoginTest() {

        // создали курьера
        courierClient.create(courier);

        // вызвать метод для логина
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        // проверить статус код и боди
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(200, statusCode);

        courierId = loginResponse.extract().path("id");
        assertNotNull(courierId);
    }
}
