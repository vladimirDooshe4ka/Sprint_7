package courier;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import order.BaseClient;


public class CourierClient extends BaseClient {
    private final String ROOT = "/courier";
    private final String DELETE = ROOT + "/{id}";
    private final String LOGIN = ROOT + "/login";

    @Step("Создание курьера")
    public ValidatableResponse createCourier(Courier courier) {

        return getSpec()
                .body(courier)
                .when()
                .post(ROOT)
                .then().log().all()
                .assertThat();
    }

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(int courierId){
        return getSpec()
                .pathParam("id", courierId)
                .when()
                .delete(DELETE)
                .then().log().all()
                .assertThat();
    }

    @Step("Авторизация курьера")
    public ValidatableResponse loginCourier(CourierCredentials courierCredentials){

        return getSpec()
                .body(courierCredentials)
                .when()
                .post(LOGIN)
                .then().log().all()
                .assertThat();
    }


}
