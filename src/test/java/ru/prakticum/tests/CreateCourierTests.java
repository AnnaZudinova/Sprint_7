package ru.prakticum.tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import ru.prakticum.steps.CourierSteps;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class CreateCourierTests extends BaseTests {
    CourierSteps courierSteps=new CourierSteps();

    @Test
    @Description("При успешном создании курьера возвращается код 201 и сообщение ok: true")
    public void checkCourierCreationWith201StatusCode () {
        Response response= courierSteps.createCourier();
        response.then().statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));
    }

    //Тест падает, т.к. сообщение об ошибке отличается от требований.
    @Test
    @Description("При при попытке создать одинаковых курьеров возвращается ошибка")
    public void checkSameCouriersCreationError() {
        Response firstCourier=courierSteps.createCourier();
        Response secondCourier=courierSteps.createCourier();

        secondCourier.then().statusCode(SC_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется"));
    }

    //Тест падает, т.к. сообщение об ошибке отличается от требований.
    @Test
    @Description("При при попытке создать осоздать пользователя с логином, который уже есть, возвращается ошибка")
    public void checkSameLoginCreationError() {
        Response firstCourier=courierSteps.createCourier();
        Response secondCourier=courierSteps.createCourierWithSameLogin();
        secondCourier.then().statusCode(SC_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @Description("Eсли не задан логин, запрос возвращает 400")
    public void checkCourierCreationWhenLoginMissingReturnsError() {
        Response response=courierSteps.createCourierWithoutLogin();
        response.then().statusCode(SC_BAD_REQUEST)
                .and()
                .body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Description("Eсли не задан пароль, запрос возвращает 400")
    public void checkCourierCreationWhenPasswordMissingReturnsError() {
        Response response=courierSteps.createCourierWithoutPassword();
        response.then().statusCode(SC_BAD_REQUEST)
                .and()
                .body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Description("Курьера можно создать без имени")
    public void checkCourierCreationWhenFirstNameMissing() {
        Response response=courierSteps.createCourierWithoutFirstName();
        response.then().statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));
    }

    @After
    public void tearDown () {
    courierSteps.deleteCourier(courierSteps.getCourierId());
    }
}
