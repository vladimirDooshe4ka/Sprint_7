import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    Order order;
    private OrderClient orderClient;
    private final String[] colorScooter;

    public OrderCreateTest(String[] colorScooter) {
        this.colorScooter = colorScooter;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0}")
    public static Object[] getOrderData() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GRAY"}},
                {new String[]{"BLACK", "GRAY"}},
                {new String[]{""}}
        };
    }

    @Before
    public void setup() {
        order = OrderRandom.getRandomOrderInfo(colorScooter);
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Тест на создание заказа с разными цветами самоката")
    public void orderCreateTest() {
        ValidatableResponse response = orderClient.createOrder(order);
        int statusCode = response.extract().statusCode();
        assertEquals("Некорректный код ответа", SC_CREATED, statusCode);
        assertNotNull("Отстуствует параметр track", response.extract().path("track"));
    }
}