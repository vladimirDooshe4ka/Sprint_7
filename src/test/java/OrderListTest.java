import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import order.OrderList;
import order.OrderClient;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListTest {

    OrderList orderList;
    OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Тест получения списка заказов без параметров")
    public void testGetListOfOrdersWithoutParametersSuccess() {

        orderList = OrderList.getOrderListWithoutParams();
        orderClient.getOrderList(orderList)
                .statusCode(200)
                .body("orders", notNullValue());
    }
}
