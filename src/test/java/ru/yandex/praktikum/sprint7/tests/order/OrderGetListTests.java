package ru.yandex.praktikum.sprint7.tests.order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.sprint7.order.OrderClient;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class OrderGetListTests {

    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @DisplayName("Shows order list")
    @Test
    public void orderListCanShowTest() {

        ValidatableResponse orderListResponse = orderClient.orderList();
        List<Object> orders = orderListResponse.extract().path("orders");

        // проверить статус код
        int statusCode = orderListResponse.extract().statusCode();
        assertEquals(200, statusCode);

        // проверить, что список не пустой
        assertFalse(orders.isEmpty());
    }
}
