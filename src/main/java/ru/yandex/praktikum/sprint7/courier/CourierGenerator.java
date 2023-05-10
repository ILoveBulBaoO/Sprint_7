package ru.yandex.praktikum.sprint7.courier;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {

    public static Courier getRandomCourier() {
        final String login = RandomStringUtils.randomAlphabetic(6);
        final String password = RandomStringUtils.randomAlphabetic(6);
        final String firstName = RandomStringUtils.randomAlphabetic(6);
        return new Courier(login, password, firstName);
    }

    public static Courier getDefaultCourier() {
        final String login = "ninjak";
        final String password = "12345";
        final String firstName = "saskesqw";
        return new Courier(login, password, firstName);
    }

    public static Courier getRandomWithoutPassword() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, null, firstName);
    }

    public static Courier getRandomWithoutLogin() {
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(null, password, firstName);
    }

    public static Courier getExistLoginCourier() {
        final String login = "ninjaT";
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password, firstName);
    }
}
