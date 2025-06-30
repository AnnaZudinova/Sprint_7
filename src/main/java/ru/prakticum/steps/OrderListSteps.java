package ru.prakticum.steps;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderListSteps extends Endpoints {

    @Step
    @DisplayName("Получить список заказов")
    public Response getOrderList() {
        return given()
                .get(GET_ORDER_LIST);
    }

    @Step
    @DisplayName("Получить список заказов по id курьера")
    public Response getCourierOrderList(int courierId) {
        return given()
                .pathParams("courierId",courierId)
                .get(GET_ORDER_LIST_BY_ID);
    }

    @Step
    @DisplayName("Получить список заказов на ближайших станциях")
    public Response getCourierOrderListWithNearestStations(int courierId) {
        return given()
                .pathParams("courierId",courierId)
                .get(GET_ORDER_LIST_WITH_NEAREST_STATIONS);
    }

    @Step
    @DisplayName("Получить список доступных для принятия курьером заказов")
    public Response getAvailableOrderList() {
        return given()
                .get(GET_AVAILABLE_ORDER_LIST);
    }

    @Step
    @DisplayName("Получить список доступных заказов около станции метро")
    public Response getAvailableOrderListNearMetroStation() {
        return given()
                .get(GET_ORDER_LIST_NEAR_METRO);
    }
}
