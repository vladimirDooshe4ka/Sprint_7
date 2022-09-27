import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import courier.*;

public class CourierLoginTest {
    Courier courier;
    CourierClient courierClient;
    CourierCredentials courierCredentials;
    CourierCredentials courierCredentialsCorrect;

    @Before
    public void setUp() {

        courier = Courier.getRandomCourier();
        courierClient = new CourierClient();
        courierClient.createCourier(courier).statusCode(201);
    }

    @After
    public void tearDown() {

        courierCredentialsCorrect = CourierCredentials.getCredentials(courier);
        int courierId = courierClient.loginCourier(courierCredentialsCorrect).extract().path("id");
        courierClient.deleteCourier(courierId).statusCode(200);
    }


    @Test
    @DisplayName("Тест успешной авторизации курьера при передаче валидных логина и пароля")
    public void testLoginCourierWithRequiredFieldsSuccess() {

        courierCredentials = CourierCredentials.getCredentials(courier);
        courierClient.loginCourier(courierCredentials)
                .statusCode(200)
                .body("id", notNullValue());
    }


    @Test
    @DisplayName("Тест авторизации без передачи логина")
    public void testLoginCourierWithoutLoginFailure() {

        courierCredentials = CourierCredentials.getCredentialsWithoutLogin(courier);
        String message = courierClient.loginCourier(courierCredentials)
                .statusCode(400)
                .extract().path("message");

        assertEquals("Некорректное сообщение об ошибке", "Недостаточно данных для входа", message);
    }


    @Test
    @DisplayName("Тест авторизации без передачи пароля")
    public void testLoginCourierWithoutPasswordFailure() {

        courierCredentials = CourierCredentials.getCredentialsWithoutPassword(courier);
        String message = courierClient.loginCourier(courierCredentials)
                .statusCode(400)
                .extract().path("message");

        assertEquals("Некорректное сообщение об ошибке", "Недостаточно данных для входа", message);
    }


    @Test
    @DisplayName("Тест авторизации при передаче невалидного логина")
    public void testLoginCourierWithWrongLoginFailure() {

        courierCredentials = CourierCredentials.getCredentialsWithWrongLogin(courier);
        String message = courierClient.loginCourier(courierCredentials)
                .statusCode(404)
                .extract().path("message");

        assertEquals("Некорректное сообщение об ошибке", "Учетная запись не найдена", message);
    }


    @Test
    @DisplayName("Тест авторизации при передаче невалидного пароля")
    public void testLoginCourierWithWrongPasswordFailure() {

        courierCredentials = CourierCredentials.getCredentialsWithWrongPassword(courier);
        String message = courierClient.loginCourier(courierCredentials)
                .statusCode(404)
                .extract().path("message");

        assertEquals("Некорректное сообщение об ошибке", "Учетная запись не найдена", message);
    }


    @Test
    @DisplayName("Тест авторизации при передачи невалидных логина и пароля")
    public void testLoginNonexistentCourierFailure() {

        courierCredentials = CourierCredentials.getCredentialsWithWrongPassAndLog(courier);
        String message = courierClient.loginCourier(courierCredentials)
                .statusCode(404)
                .extract().path("message");

        assertEquals("Некорректное сообщение об ошибке", "Учетная запись не найдена", message);
    }
}
