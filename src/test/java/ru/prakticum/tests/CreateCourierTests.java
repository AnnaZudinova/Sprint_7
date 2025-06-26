package ru.prakticum.tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.prakticum.steps.CourierSteps;

import static org.hamcrest.Matchers.*;

public class CreateCourierTests {
    CourierSteps courierSteps=new CourierSteps();

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        RestAssured.filters(new RequestLoggingFilter(),new ResponseLoggingFilter());
    }

    @Test
    @DisplayName("При успешном создании курьера возвращается код 201 и сообщение ok: true")
    public void checkCourierCreationWith201StatusCode () {
        Response response= courierSteps.createCourier();
        response.then().statusCode(201)
                .and()
                .body("ok", equalTo(true));
    }

    //Тест падает, т.к. сообщение об ошибке отличается от требований.
    @Test
    @DisplayName("При при попытке создать одинаковых курьеров возвращается ошибка")
    public void checkSameCouriersCreationError() {
        Response firstCourier=courierSteps.createCourier();
        Response secondCourier=courierSteps.createCourier();

        secondCourier.then().statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется"));
    }

    //Тест падает, т.к. сообщение об ошибке отличается от требований.
    @Test
    @DisplayName("При при попытке создать осоздать пользователя с логином, который уже есть, возвращается ошибка")
    public void checkSameLoginCreationError() {
        Response firstCourier=courierSteps.createCourier();
        Response secondCourier=courierSteps.createCourierWithSameLogin();
        secondCourier.then().statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Eсли одного из обязательных полей нет, запрос возвращает 400")
    public void checkCourierCreationWhenRequiredFieldMissingReturnsError() {
        Response response=courierSteps.createCourierWithoutLogin();
        response.then().statusCode(400)
                .and()
                .body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void tearDown () {
    courierSteps.deleteCourier(courierSteps.getCourierId());
    }
}
