package ru.prakticum.tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.prakticum.steps.OrderSteps;
import static org.hamcrest.Matchers.notNullValue;

public class CreateOrderMessageTests {
    OrderSteps orderSteps=new OrderSteps();
    private static final String[] NO_COLOR = {""};

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        RestAssured.filters(new RequestLoggingFilter(),new ResponseLoggingFilter());
    }

    @Test
    @DisplayName("Тело ответа содержит трек")
    public void checkOrderCreationResponseMessageContainsTrack() {
        Response response = orderSteps.createOrder("Гарри", "Поттер", "Хогвартс", "Библиотека им. Ленина", "8 555 333 66 22", 5, "1992-09-13", "Цвет не важен", NO_COLOR);
        response.then().statusCode(201)
                .and()
                .body("track", notNullValue());
        }

    @After
    public void tearDown(){
       orderSteps.cancelOrder();
    }

}

