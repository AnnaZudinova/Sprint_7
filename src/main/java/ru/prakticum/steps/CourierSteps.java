package ru.prakticum.steps;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import ru.prakticum.model.Courier;

import static io.restassured.RestAssured.given;

public class CourierSteps extends Endpoints {
    protected String login=RandomStringUtils.randomAlphabetic(10);
    protected String password=RandomStringUtils.randomAlphabetic(10);
    protected String firstName=RandomStringUtils.randomAlphabetic(10);
    protected int id=0;
    Courier courier=new Courier(login, password, firstName);

    @Step
    @DisplayName("Создать курьера")
    public Response createCourier() {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .post(CREATE_COURIER);
    }

    @Step
    @DisplayName("Создать двух одинаковых курьеров")
    public Response createCourierWithSameLogin() {
        courier.setPassword(RandomStringUtils.randomAlphabetic(7));
        courier.setFirstName(RandomStringUtils.randomAlphabetic(7));

        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .post(CREATE_COURIER);
    }

    @Step
    @DisplayName("Создать курьера без логина")
    public Response createCourierWithoutLogin() {
        courier.setLogin(null);
        courier.setPassword(password);
        courier.setFirstName(firstName);

        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
            .post(CREATE_COURIER);
    }

    @Step
    @DisplayName("Создать курьера без пароля")
    public Response createCourierWithoutPassword() {
        courier.setLogin(login);
        courier.setPassword(null);
        courier.setFirstName(firstName);

        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .post(CREATE_COURIER);
    }

    @Step
    @DisplayName("Создать курьера без имени")
    public Response createCourierWithoutFirstName() {
        courier.setLogin(login);
        courier.setPassword(password);
        courier.setFirstName(null);

        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .post(CREATE_COURIER);
    }

    @Step
    @DisplayName("Залогинить курьера")
    public Response courierLogin () {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .post(LOGIN_COURIER);
    }

    @Step
    @DisplayName("Залогинить курьера без пароля")
    public Response courierLoginWithoutPassword () {
        courier.setPassword(null);

        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .post(LOGIN_COURIER);
    }

    @Step
    @DisplayName("Залогинить курьера без логина")
    public Response courierLoginWithoutLogin () {
        courier.setLogin(null);

        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .post(LOGIN_COURIER);
    }

    @Step
    @DisplayName("Залогинить курьера с несуществующим логином")
    public Response courierLoginWithNonexistentLogin () {
        courier.setLogin(RandomStringUtils.randomAlphabetic(7));

        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .post(LOGIN_COURIER);
    }

    @Step
    @DisplayName("Залогинить курьера с несуществующим паролем")
    public Response courierLoginWithNonexistentPassword () {
        courier.setPassword(RandomStringUtils.randomAlphabetic(7));

        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .post(LOGIN_COURIER);
    }

    @Step
    @DisplayName("Залогинить курьера с несуществующим логином и паролем")
    public Response courierLoginWithNonexistentUser () {
        courier.setLogin(RandomStringUtils.randomAlphabetic(7));
        courier.setPassword(RandomStringUtils.randomAlphabetic(7));

        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .post(LOGIN_COURIER);
    }

    @Step
    @DisplayName("Получить id курьера")
    public int getCourierId() {
        try {
            Courier responseId = given()
                    .contentType(ContentType.JSON)
                    .and()
                    .body(courier)
                    .post(LOGIN_COURIER)
                    .body()
                    .as(Courier.class);

            int id=responseId.getId();
            return id;}
        catch (Exception e) {
            System.out.println(e);
            return 0;}
    }

    @Step
    @DisplayName("Удалить курьера")
    public void deleteCourier(int id) {
        given()
                .pathParam("id",id)
                .delete(DELETE_COURIER);
    }

}
