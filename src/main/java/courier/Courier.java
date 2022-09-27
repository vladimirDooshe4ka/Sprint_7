package courier;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import lombok.Data;

@Data
public class Courier {
    private String login;
    private String password;
    private String firstName;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Step("Формирование данных для создания курьера со всеми полями")
    public static Courier getRandomCourier() {
        return new Courier(
                RandomStringUtils.randomAlphanumeric(10) + "@example.com",
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(8)
        );
    }

    @Step("Формирование данных для создания курьера с обязательными полями")
    public static Courier getCourierWithRequiredFields() {
        return new Courier(
                RandomStringUtils.randomAlphanumeric(10) + "@example.com",
                RandomStringUtils.randomAlphabetic(10),
                null
        );
    }

    @Step("Формирование данных для создания курьера без логина")
    public static Courier getCourierWithoutLogin() {
        return new Courier(
                null,
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(8)
        );
    }

    @Step("Формирование данных для создания курьера без пароля")
    public static Courier getCourierWithoutPassword() {
        return new Courier(
                RandomStringUtils.randomAlphanumeric(10) + "@example.com",
                null,
                RandomStringUtils.randomAlphabetic(8)
        );
    }

    @Step("Формирование данных для создания курьера с дублированием логина")
    public static Courier getCourierDuplicateLogin(Courier courier) {
        return new Courier(
                courier.getLogin(),
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(8)
        );
    }
}
