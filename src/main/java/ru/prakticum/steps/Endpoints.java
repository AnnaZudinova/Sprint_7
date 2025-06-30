package ru.prakticum.steps;

public class Endpoints {
    protected final String CREATE_COURIER = "/api/v1/courier";
    protected final String LOGIN_COURIER="/api/v1/courier/login";
    protected final String DELETE_COURIER="/api/v1/courier/{id}";
    protected static final String GET_ORDER_LIST="/api/v1/orders";
    protected static final String GET_ORDER_LIST_BY_ID="/api/v1/orders?courierId={courierId}";
    protected static final String GET_ORDER_LIST_WITH_NEAREST_STATIONS="/api/v1/orders?courierId={courierId}&nearestStation=[\"1\", \"2\"]";
    protected static final String GET_AVAILABLE_ORDER_LIST="/api/v1/orders?limit=10&page=0";
    protected static final String GET_ORDER_LIST_NEAR_METRO="/api/v1/orders?limit=10&page=0&nearestStation=[\"110\"]";
    protected static final String POST_ORDER="/api/v1/orders";
    protected static final String CANCEL_ORDER="/api/v1/orders/cancel?track={track}";
}
