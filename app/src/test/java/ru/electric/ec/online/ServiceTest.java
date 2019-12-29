package ru.electric.ec.online;

import androidx.databinding.ObservableDouble;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import ru.electric.ec.online.models.Count;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServiceTest {

    @Test
    void rub() {
        String expected = "1 000 000,00 руб.";
        ObservableDouble value = new ObservableDouble();
        value.set(1000000.00);
        String actual = Service.rub(value, "%1$d руб.");
        assertEquals(expected, actual);
    }

    @Test
    void getInt() {
        int expected = 100;
        int actual = Service.getInt("100");
        assertEquals(expected, actual);
    }

    @Test
    void getInt_0() {
        int expected = 0;
        int actual = Service.getInt("");
        assertEquals(expected, actual);
    }

    @Test
    void getInt_null() {
        int expected = 0;
        int actual = Service.getInt(null);
        assertEquals(expected, actual);
    }

    @Test
    void getDouble_Dot() {
        double expected = 1000000.0;
        double actual = Service.getDouble("1 000 000.00");
        assertEquals(expected, actual);
    }

    @Test
    void getDouble_Comma() {
        double expected = 1000000.0;
        double actual = Service.getDouble("1 000 000,00");
        assertEquals(expected, actual);
    }

    @Test
    void getDouble_Int() {
        double expected = 1000.0;
        double actual = Service.getDouble("1 000");
        assertEquals(expected, actual);
    }

    @Test
    void getDouble_0() {
        double expected = 0.0;
        double actual = Service.getDouble("");
        assertEquals(expected, actual);
    }

    @Test
    void getDouble_null() {
        double expected = 0.0;
        double actual = Service.getDouble(null);
        assertEquals(expected, actual);
    }

    @Test
    void isEqual_NullOne() {
        assertFalse(Service.isEqual(null, "Тест "));
    }

    @Test
    void isEqual_NullTwo() {
        assertFalse(Service.isEqual("Тест ", null));
    }

    @Test
    void isEqual_False() {
        assertFalse(Service.isEqual("Тест", "Тест "));
    }

    @Test
    void isEqual_True() {
        assertTrue(Service.isEqual("Тест", "тест"));
    }

    @Test
    void isEqual_0() {
        assertTrue(Service.isEqual("", ""));
    }

    @Test
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
    void status_orange() {
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
    void status_green() {
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
    void status_green_500() {
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
    void status_orange_500() {
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
    void log_success() {
        try{
            Service.log(false, "test");
        } catch (Exception e) {
            Assert.assertNotEquals("", e.getMessage());
        }
    }

    @Test
    void log_error() {
        try{
            Service.log(true, "test");
        } catch (Exception e) {
            Assert.assertNotEquals("", e.getMessage());
        }
    }

    @Test
    void getStr_0() {
        assertEquals("", Service.getStr(0));
    }

    @Test
    void getStr_1() {
        assertEquals("1", Service.getStr(0, 1));
    }

    @Test
    void getStr_2() {
        assertEquals("12", Service.getStr(0, 1, 2));
    }
}