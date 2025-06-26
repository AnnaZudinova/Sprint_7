package ru.prakticum.tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.prakticum.steps.OrderSteps;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderParameterizedTests {
    OrderSteps orderSteps=new OrderSteps();

    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

    private static final String[] BLACK = {"BLACK"};
    private static final String[] GREY = {"GREY"};
    private static final String[] BOTH_COLORS = {"BLACK", "GREY"};
    private static final String[] NO_COLOR = {""};

    public CreateOrderParameterizedTests(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters(name = "Тестовые данные:{0},{1},{2},{3},{4},{5},{6},{7},{8}")
    public static Object[][] getParameters() {
        return new Object[][]{
               {"Anton", "Ivanov", "High st, 4", "3", "88009007766", 1, "2023-09-11", "ASAP", BLACK},
               {"Иван", "Иванов", "Верхняя,4 ", "Нахабино", "+78009007766", 2, "2024-09-11", " ", GREY},
               {"Ольга", "Иванова", "Березовая,25 ", "Митино", "+7-800-900-77-66", 3, "2025-07-20", "Два самоката", BOTH_COLORS},
               {"Гарри", "Поттер", "Хогвартс", "Библиотека им. Ленина", "8 555 333 66 22", 5, "1992-09-13", "Цвет не важен", NO_COLOR},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        RestAssured.filters(new RequestLoggingFilter(),new ResponseLoggingFilter());
    }

    @Test
    @DisplayName("Проверка создания заказа с разными вариантами выбора цвета самоката")
    public void checkOrderCreationWithDiffColors() {
        Response response = orderSteps.createOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        response.then().statusCode(201)
                .and()
                .body("track",notNullValue());
        }

    @After
    public void tearDown(){
       OrderSteps.cancelOrder();
    }

}

