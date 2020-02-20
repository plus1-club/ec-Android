package ru.electric.ec.online.common;

import androidx.databinding.ObservableDouble;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.electric.ec.online.R;
import ru.electric.ec.online.models.Count;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("common.Service")
class ServiceTest {

    @Test
    @DisplayName("rub(): Превращение цифры 1000000.00 в 1 000 000,00 руб.")
    void rub_1000000() {
        String expected = "1 000 000,00 руб.";
        ObservableDouble value = new ObservableDouble();
        value.set(1000000.00);
        String actual = Service.rub(value, "%1$d руб.");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("getInt(): Превращение строки 100 в целое число 100")
    void getInt_100() {
        int expected = 100;
        int actual = Service.getInt("100");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("getInt(): Превращение пустой строки в целое число 0")
    void getInt_0() {
        int expected = 0;
        int actual = Service.getInt("");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("getInt(): Превращение null в целое число 0")
    void getInt_null() {
        int expected = 0;
        int actual = Service.getInt(null);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("getDouble(): Превращение строки 1 000 000.00 с точкой в дробное число 1000000.0")
    void getDouble_1000000_Dot() {
        double expected = 1000000.0;
        double actual = Service.getDouble("1 000 000.00");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("getDouble(): Превращение строки 1 000 000.00 с запятой в дробное число 1000000.0")
    void getDouble_1000000_Comma() {
        double expected = 1000000.0;
        double actual = Service.getDouble("1 000 000,00");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("getDouble(): Превращение строки 1 000 без дробйно части в дробное число 1000.0")
    void getDouble_1000_Without_Dot() {
        double expected = 1000.0;
        double actual = Service.getDouble("1 000");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("getDouble(): Превращение пустой строки в дробное число 0.0")
    void getDouble_0() {
        double expected = 0.0;
        double actual = Service.getDouble("");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("getDouble(): Превращение null в дробное число 0.0")
    void getDouble_null() {
        double expected = 0.0;
        double actual = Service.getDouble(null);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("isEqual(): Сравнение null и строки Тест - даст ложь")
    void isEqual_null_text() {
        assertFalse(Service.isEqual(null, "Тест "));
    }

    @Test
    @DisplayName("isEqual(): Сравнение строки Тест и null - даст ложь")
    void isEqual_text_null() {
        assertFalse(Service.isEqual("Тест ", null));
    }

    @Test
    @DisplayName("isEqual(): Сравнение строки Тест и Тест с пробелом - даст ложь")
    void isEqual_isFalse() {
        assertFalse(Service.isEqual("Тест", "Тест "));
    }

    @Test
    @DisplayName("isEqual(): Сравнение строки Тест и тест - даст истину")
    void isEqual_isTrue() {
        assertTrue(Service.isEqual("Тест", "тест"));
    }

    @Test
    @DisplayName("isEqual(): Сравнение двух пустых строк - даст истину")
    void isEqual_empty_empty() {
        assertTrue(Service.isEqual("", ""));
    }

    @Test
    @DisplayName("status(): Просят 0 - статус черный(Не известно)")
    void status_black() {
        String status = Service.getStr(R.string.status_black);
        Count expected = new Count(0, 100, status, R.color.black, "black");
        Count actual = Service.status(0, 100, false);
        assertEquals(expected.count, actual.count);
        assertEquals(expected.stockCount, actual.stockCount);
        assertEquals(expected.status, actual.status);
        assertEquals(expected.colorName, actual.colorName);
        assertEquals(expected.color, actual.color);
    }

    @Test
    @DisplayName("status(): Слишком часто просят (на складе = -2) - статус фиолетовый(Превышено...)")
    void status_violet() {
        String status = Service.getStr(R.string.status_violet);
        Count expected = new Count(1, -2, status, R.color.violet, "violet");
        Count actual = Service.status(1, -2, false);
        assertEquals(expected.count, actual.count);
        assertEquals(expected.stockCount, actual.stockCount);
        assertEquals(expected.status, actual.status);
        assertEquals(expected.colorName, actual.colorName);
        assertEquals(expected.color, actual.color);
    }

    @Test
    @DisplayName("status(): Требуется обновление количества - статус синий(Проверить наличие)")
    void status_blue() {
        String status = Service.getStr(R.string.status_blue);
        Count expected = new Count(1, 100, status, R.color.blue, "blue");
        Count actual = Service.status(1, 100, true);
        assertEquals(expected.count, actual.count);
        assertEquals(expected.stockCount, actual.stockCount);
        assertEquals(expected.status, actual.status);
        assertEquals(expected.colorName, actual.colorName);
        assertEquals(expected.color, actual.color);
    }

    @Test
    @DisplayName("status(): На складе 0 - статус красный(Нет)")
    void status_red() {
        String status = Service.getStr(R.string.status_red);
        Count expected = new Count(1, 0, status, R.color.red, "red");
        Count actual = Service.status(1, 0, false);
        assertEquals(expected.count, actual.count);
        assertEquals(expected.stockCount, actual.stockCount);
        assertEquals(expected.status, actual.status);
        assertEquals(expected.colorName, actual.colorName);
        assertEquals(expected.color, actual.color);
    }

    @Test
    @DisplayName("status(): На складе больше, чем заказали(<500) - статус зеленый(В наличии)")
    void status_green_less_500() {
        String status = Service.getStr(R.string.status_green);
        Count expected = new Count(1, 10, status, R.color.green, "green");
        Count actual = Service.status(1, 10, false);
        assertEquals(expected.count, actual.count);
        assertEquals(expected.stockCount, actual.stockCount);
        assertEquals(expected.status, actual.status);
        assertEquals(expected.colorName, actual.colorName);
        assertEquals(expected.color, actual.color);
    }

    @Test
    @DisplayName("status(): На складе больше, чем заказали(>500) - статус зеленый(В наличии)")
    void status_green_more_500() {
        String status = Service.getStr(R.string.status_green);
        Count expected = new Count(600, 999, status, R.color.green, "green");
        Count actual = Service.status(600, 999, false);
        assertEquals(expected.count, actual.count);
        assertEquals(expected.stockCount, actual.stockCount);
        assertEquals(expected.status, actual.status);
        assertEquals(expected.colorName, actual.colorName);
        assertEquals(expected.color, actual.color);
    }

    @Test
    @DisplayName("status(): Просят, но не все есть(на складе = -1) - статус оранжевый(Частично доступно)")
    void status_orange_less_500() {
        String status = Service.getStr(R.string.status_orange);
        Count expected = new Count(1, -1, status, R.color.orange, "orange");
        Count actual = Service.status(1, -1, false);
        assertEquals(expected.count, actual.count);
        assertEquals(expected.stockCount, actual.stockCount);
        assertEquals(expected.status, actual.status);
        assertEquals(expected.colorName, actual.colorName);
        assertEquals(expected.color, actual.color);
    }

    @Test
    @DisplayName("status(): На складе больше, чем заказали(>500) - статус оранжевый(Частично доступно)")
    void status_orange_more_500() {
        String status = Service.getStr(R.string.status_orange);
        Count expected = new Count(600, 555, status, R.color.orange, "orange");
        Count actual = Service.status(600, 555, false);
        assertEquals(expected.count, actual.count);
        assertEquals(expected.stockCount, actual.stockCount);
        assertEquals(expected.status, actual.status);
        assertEquals(expected.colorName, actual.colorName);
        assertEquals(expected.color, actual.color);
    }

    @Test
    @DisplayName("status(): Просят немного, но не все есть - статус желтый(Доступно ...)")
    void status_yellow() {
        String status = Service.getStr(R.string.status_yellow, 10);
        Count expected = new Count(20, 10, status, R.color.yellow, "yellow");
        Count actual = Service.status(20, 10, false);
        assertEquals(expected.count, actual.count);
        assertEquals(expected.stockCount, actual.stockCount);
        assertEquals(expected.status, actual.status);
        assertEquals(expected.colorName, actual.colorName);
        assertEquals(expected.color, actual.color);
    }

    @Test
    @DisplayName("log(): Вывод успешного сообщения")
    void log_success() {
        try{
            Service.log(false, "test");
        } catch (Exception e) {
            Assert.assertNotEquals("", e.getMessage());
        }
    }

    @Test
    @DisplayName("log(): Вывод ошибки")
    void log_error() {
        try{
            Service.log(true, "test");
        } catch (Exception e) {
            Assert.assertNotEquals("", e.getMessage());
        }
    }

    @Test
    @DisplayName("getStr(): Получение строки из ресурсов без параметров")
    void getStr_0() {
        assertEquals("", Service.getStr(0));
    }

    @Test
    @DisplayName("getStr(): Получение строки из ресурсов с 1 параметром")
    void getStr_1() {
        assertEquals("1", Service.getStr(0, 1));
    }

    @Test
    @DisplayName("getStr(): Получение строки из ресурсов с 2 параметрами")
    void getStr_2() {
        assertEquals("12", Service.getStr(0, 1, 2));
    }
}