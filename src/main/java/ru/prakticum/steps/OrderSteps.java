package ru.prakticum.steps;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.prakticum.model.Order;
import static io.restassured.RestAssured.given;

public class OrderSteps extends Endpoints {
    private static String track;


    @Step
    @DisplayName("Создать заказ")
    public Response createOrder(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        Order orderData = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);

        Response response= given()
                .contentType(ContentType.JSON)
                .and()
                .body(orderData)
                .post(POST_ORDER);

        Order orderTrack= response.body().as(Order.class);
        track=orderTrack.getTrack();
        return response;
    }

    @Step
    @DisplayName("Отменить заказ")
    public static void cancelOrder(){
        given()
                .pathParams("track",track)
                .put(CANCEL_ORDER);
    }
}
