package ru.prakticum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import ru.prakticum.model.Courier;

import static io.restassured.RestAssured.given;

public class CourierSteps{
    protected String login=RandomStringUtils.randomAlphabetic(10);
    protected String password=RandomStringUtils.randomAlphabetic(10);
    protected String firstName=RandomStringUtils.randomAlphabetic(10);
    Courier courier=new Courier(login, password, firstName);

    private static final String CREATE_COURIER = "/api/v1/courier";
    private static final String LOGIN_COURIER="/api/v1/courier/login";
    private static final String DELETE_COURIER="/api/v1/courier/{id}";

    @Step
    public Response createCourier() {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .post(CREATE_COURIER);
    }

    @Step
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
    public Response courierLogin () {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .post(LOGIN_COURIER);
    }

    @Step
    public Response courierLoginWithoutPassword () {
        courier.setPassword(null);

        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .post(LOGIN_COURIER);
    }

    @Step
    public Response courierLoginWithoutLogin () {
        courier.setLogin(null);

        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .post(LOGIN_COURIER);
    }

    @Step
    public Response courierLoginWithNonexistentLogin () {
        courier.setLogin(RandomStringUtils.randomAlphabetic(7));

        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .post(LOGIN_COURIER);
    }

    @Step
    public Response courierLoginWithNonexistentPassword () {
        courier.setPassword(RandomStringUtils.randomAlphabetic(7));

        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .post(LOGIN_COURIER);
    }

    @Step
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
    public void deleteCourier(int id) {
        given()
                .pathParam("id",id)
                .delete(DELETE_COURIER);
    }

}
