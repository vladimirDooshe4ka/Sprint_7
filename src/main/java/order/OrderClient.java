package order;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class OrderClient extends BaseClient{
    private final String ORDER = "/orders";

    @Step("Создание заказа")
    public ValidatableResponse createOrder(Order order) {
        return getSpec()
                .body(order)
                .when()
                .post(ORDER)
                .then().log().all()
                .assertThat();
    }

    @Step("Получение списка заказа")
    public ValidatableResponse getOrderList(OrderList orderList){
        return getSpec()
                .body(orderList)
                .when()
                .get(ORDER)
                .then().log().all()
                .assertThat();
    }

}
