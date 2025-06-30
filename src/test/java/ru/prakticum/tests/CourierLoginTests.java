package ru.prakticum.tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.prakticum.steps.CourierSteps;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class CourierLoginTests extends BaseTests{
    CourierSteps courierSteps=new CourierSteps();

    @Before
    public void setUp() {
      courierSteps.createCourier();
    }

    @Test
    @Description("В ответ на успешную авторизацию приходит код 200 и возвращается id курьера")
    public void checkCourierLoginStatusCode200 () {
        int id = courierSteps.getCourierId();

       Response response= courierSteps.courierLogin();
       response.then().assertThat().statusCode(SC_OK)
               .and()
               .body("id",equalTo(id));
    }

  //504
    @Test
    @Description("При попытке авторизации без пароля возвращается ошибка с кодом 400")
    public void checkCourierLoginMissingPasswordStatusCode400 () {
        Response response=courierSteps.courierLoginWithoutPassword();
        response.then().assertThat().statusCode(SC_BAD_REQUEST)
                .and()
                .body("message",equalTo("Недостаточно данных для входа"));
    }

    @Test
    @Description("При попытке авторизации без логина возвращается ошибка с кодом 400")
    public void checkCourierLoginMissingLoginStatusCode400 () {
        Response response=courierSteps.courierLoginWithoutLogin();
        response.then().assertThat().statusCode(SC_BAD_REQUEST)
                .and()
                .body("message",equalTo("Недостаточно данных для входа"));
    }

    @Test
    @Description("Cистема вернёт 404, если неправильно указать логин")
    public void checkCourierLoginNonexistentLoginStatusCode404 () {
        Response response=courierSteps.courierLoginWithNonexistentLogin();
        response.then().assertThat().statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @Description("Cистема вернёт 404, если неправильно указать пароль")
    public void checkCourierLoginNonexistentPasswordStatusCode404 () {
        Response response=courierSteps.courierLoginWithNonexistentPassword();
        response.then().assertThat().statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @Description("Cистема вернёт 404, если если авторизоваться под несуществующим пользователем")
    public void checkCourierLoginNonexistentUserStatusCode404 () {
        Response response=courierSteps.courierLoginWithNonexistentUser();
        response.then().assertThat().statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void tearDown () {
        courierSteps.deleteCourier(courierSteps.getCourierId());
    }

}
