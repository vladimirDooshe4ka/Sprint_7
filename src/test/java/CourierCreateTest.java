
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import courier.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertTrue;


public class CourierCreateTest {

    Courier courier;
    CourierClient courierClient;
    private boolean createSuccess;


    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        if (createSuccess){
            CourierCredentials courierCredentials = CourierCredentials.getCredentials(courier);
            int courierId = courierClient.loginCourier(courierCredentials).extract().path("id");
            courierClient.deleteCourier(courierId).statusCode(200);
        }
    }

    @Test
    @DisplayName("Тест успешного создания курьера при передаче всех полей")
    public void testCreatingCourierSuccess() {

        courier = Courier.getRandomCourier();
        createSuccess = courierClient.createCourier(courier)
                .statusCode(201)
                .extract().path("ok");

        assertTrue(createSuccess);
    }


    @Test
    @DisplayName("Тест успешного создания курьера при передаче только обязательных полей")
    public void testCreatingCourierWithRequiredFieldsSuccess() {

        courier = Courier.getCourierWithRequiredFields();
        createSuccess = courierClient.createCourier(courier)
                .statusCode(201)
                .extract().path("ok");

        assertTrue(createSuccess);
    }


    @Test
    @DisplayName("Тест создания курьера без передачи логина")
    public void testCreatingCourierWithoutLoginFailure() {

        courier = Courier.getCourierWithoutLogin();
        courierClient.createCourier(courier)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }


    @Test
    @DisplayName("Тест создания курьера без передачи пароля")
    public void testCreatingCourierWithoutPasswordFailure() {

        courier = Courier.getCourierWithoutPassword();
        courierClient.createCourier(courier)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }


    @Test
    @DisplayName("Тест создания курьера при передачи уже существующего логина")
    public void testCreatingCourierWithDuplicateLoginFailure() {

        courier = Courier.getRandomCourier();
        createSuccess = courierClient.createCourier(courier)
                .statusCode(201)
                .extract().path("ok");
        assertTrue(createSuccess);

        Courier duplicateCourier = Courier.getCourierDuplicateLogin(courier);
        courierClient.createCourier(duplicateCourier)
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

}
