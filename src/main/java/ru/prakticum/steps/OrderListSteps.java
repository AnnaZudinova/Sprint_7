package ru.prakticum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderListSteps {

    private static final String GET_ORDER_LIST="/api/v1/orders";
    private static final String GET_ORDER_LIST_BY_ID="/api/v1/orders?courierId={courierId}";
    private static final String GET_ORDER_LIST_WITH_NEAREST_STATIONS="/api/v1/orders?courierId={courierId}&nearestStation=[\"1\", \"2\"]";
    private static final String GET_AVAILABLE_ORDER_LIST="/api/v1/orders?limit=10&page=0";
    private static final String GET_ORDER_LIST_NEAR_METRO="/api/v1/orders?limit=10&page=0&nearestStation=[\"110\"]";

    @Step
    public Response getOrderList() {
        return given()
                .get(GET_ORDER_LIST);
    }

    @Step
    public Response getCourierOrderList(int courierId) {
        return given()
                .pathParams("courierId",courierId)
                .get(GET_ORDER_LIST_BY_ID);
    }

    @Step
    public Response getCourierOrderListWithNearestStations(int courierId) {
        return given()
                .pathParams("courierId",courierId)
                .get(GET_ORDER_LIST_WITH_NEAREST_STATIONS);
    }

    @Step
    public Response getAvailableOrderList() {
        return given()
                .get(GET_AVAILABLE_ORDER_LIST);
    }

    @Step
    public Response getAvailableOrderListNearMetroStation() {
        return given()
                .get(GET_ORDER_LIST_NEAR_METRO);
    }
}
