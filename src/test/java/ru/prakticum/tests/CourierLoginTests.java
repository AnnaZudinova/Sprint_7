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

public class CourierLoginTests {
    CourierSteps courierSteps=new CourierSteps();

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        RestAssured.filters(new RequestLoggingFilter(),new ResponseLoggingFilter());
        courierSteps.createCourier();
    }

    @Test
    @DisplayName("В ответ на успешную авторизацию приходит код 200 и возвращается id курьера")
    public void checkCourierLoginStatusCode200 () {
        int id = courierSteps.getCourierId();

       Response response= courierSteps.courierLogin();
       response.then().assertThat().statusCode(200)
               .and()
               .body("id",equalTo(id));
    }

  //504
    @Test
    @DisplayName("При попытке авторизации без пароля возвращается ошибка с кодом 400")
    public void checkCourierLoginMissingPasswordStatusCode400 () {
        Response response=courierSteps.courierLoginWithoutPassword();
        response.then().assertThat().statusCode(400)
                .and()
                .body("message",equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("При попытке авторизации без логина возвращается ошибка с кодом 400")
    public void checkCourierLoginMissingLoginStatusCode400 () {
        Response response=courierSteps.courierLoginWithoutLogin();
        response.then().assertThat().statusCode(400)
                .and()
                .body("message",equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Cистема вернёт 404, если неправильно указать логин")
    public void checkCourierLoginNonexistentLoginStatusCode404 () {
        Response response=courierSteps.courierLoginWithNonexistentLogin();
        response.then().assertThat().statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Cистема вернёт 404, если неправильно указать пароль")
    public void checkCourierLoginNonexistentPasswordStatusCode404 () {
        Response response=courierSteps.courierLoginWithNonexistentPassword();
        response.then().assertThat().statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Cистема вернёт 404, если если авторизоваться под несуществующим пользователем")
    public void checkCourierLoginNonexistentUserStatusCode404 () {
        Response response=courierSteps.courierLoginWithNonexistentUser();
        response.then().assertThat().statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void tearDown () {
        courierSteps.deleteCourier(courierSteps.getCourierId());
    }

}
