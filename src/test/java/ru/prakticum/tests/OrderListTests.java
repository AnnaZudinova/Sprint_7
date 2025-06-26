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
import ru.prakticum.steps.OrderListSteps;
import static org.hamcrest.Matchers.*;

public class OrderListTests {
    OrderListSteps orderListSteps=new OrderListSteps();
    CourierSteps courierSteps=new CourierSteps();

    private int courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        RestAssured.filters(new RequestLoggingFilter(),new ResponseLoggingFilter());
        courierSteps.createCourier();
        courierId=courierSteps.getCourierId();
    }

    @Test
    @DisplayName("При запросе списка заказов без параметров в тело ответа возвращается список заказов")
    public void checkOrderListResponseMessageContainsOrders() {
        Response response = orderListSteps.getOrderList();
        response.then().statusCode(200)
                .and()
                .body("orders",notNullValue())
                .and()
                .body("pageInfo",notNullValue())
                .and()
                .body("availableStations",notNullValue());
    }

    @Test
    @DisplayName("При запросе списка заказов курьера в тело ответа возвращается список заказов")
    public void checkCourierOrderListResponseMessageContainsOrders() {
        Response response = orderListSteps.getCourierOrderList(courierId);
        response.then().statusCode(200)
                .and()
                .body("orders",notNullValue())
                .and()
                .body("pageInfo",notNullValue())
                .and()
                .body("availableStations",notNullValue());
        }

    @Test
    @DisplayName("При запросе списка заказов курьера с фильтром по станциям в тело ответа возвращается список заказов")
    public void checkCourierOrderListOnMetroStationsResponseMessageContainsOrders() {
        Response response = orderListSteps.getCourierOrderListWithNearestStations(courierId);
        response.then().statusCode(200)
                .and()
                .body("orders",notNullValue())
                .and()
                .body("pageInfo",notNullValue())
                .and()
                .body("availableStations",notNullValue());
    }

    @Test
    @DisplayName("При запросе списка заказов, доступных для принятия, в тело ответа возвращается список заказов")
    public void checkAvailableOrderList() {
        Response response =orderListSteps.getAvailableOrderList();
        response.then().statusCode(200)
                .and()
                .body("orders",notNullValue())
                .and()
                .body("pageInfo",notNullValue())
                .and()
                .body("availableStations",notNullValue());
    }

    @Test
    @DisplayName("При запросе списка заказов, доступных для курьера возле определенной станции метро, в тело ответа возвращается список заказов")
    public void checkAvailableOrderListNearMetroStation() {
        Response response = orderListSteps.getAvailableOrderListNearMetroStation();
        response.then().statusCode(200)
                .and()
                .body("orders",notNullValue())
                .and()
                .body("pageInfo",notNullValue())
                .and()
                .body("availableStations",notNullValue());
    }

    @After
    public void tearDown(){
        courierSteps.deleteCourier(courierId);
    }

}
