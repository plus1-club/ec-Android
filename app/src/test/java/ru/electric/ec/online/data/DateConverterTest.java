package ru.electric.ec.online.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("data.DateConverter")
class DateConverterTest {

    private Date date;

    @BeforeEach
    void setUp() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0);
        date = new Date(calendar.getTimeInMillis());
    }

    @Test
    @DisplayName("toDate(): число = null - возвращается null")
    void toDate_null() {
        assertNull(DateConverter.toDate(null));
    }

    @Test
    @DisplayName("toDate(): передано число - возвращается дата")
    void toDate_ok() {
        assertEquals(date, DateConverter.toDate(0L));
    }

    @Test
    @DisplayName("fromDate(): дата = null - возвращается null")
    void fromDate_null() {
        assertNull(DateConverter.fromDate(null));
    }

    @Test
    @DisplayName("fromDate(): передана дата - возвращается число")
    void fromDate_ok() {
        assertEquals(0L, DateConverter.fromDate(date));
    }
}