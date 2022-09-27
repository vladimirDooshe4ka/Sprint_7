package order;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class OrderRandom {
    public static Order getRandomOrderInfo(String[] colorScooter) {
        return new Order(
                RandomStringUtils.randomAlphanumeric(10),   // Имя
                RandomStringUtils.randomAlphanumeric(10),   // Фамилия
                RandomStringUtils.randomAlphanumeric(10),   // Адрес
                RandomStringUtils.randomNumeric(1),         // Метро
                RandomStringUtils.randomNumeric(10),        // телефон
                new Random().nextInt(300),                  // Время аренды
                "2022-0" + RandomStringUtils.randomNumeric(1) + "-1" + RandomStringUtils.randomNumeric(1), // Дата доставки
                colorScooter,
                RandomStringUtils.randomAlphanumeric(30)     // Комментарий
        );
    }
}