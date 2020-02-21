package ru.electric.ec.online.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс информации по счету
 * @author Сергей Лавров
 * @version 0.6
 */
@Entity
public class Invoice {

    /** Номер счета */
    @PrimaryKey
    public int number = 0;

    /** Дата счета */
    @ColumnInfo(name = "date")
    public String date;

    /** Итоговая сумма по счету */
    @ColumnInfo(name = "sum")
    public double sum;

    /** Статус счета
     * - неподтвержденный резерв,
     * - резерв,
     * - заказ,
     * - аннулированный счет,
     * - просроченнй счето
     * - отгрузка) */
    @ColumnInfo(name = "status")
    public String status;

    /** Номер накладной */
    @ColumnInfo(name = "waybill")
    public int waybill;

    /** Список детальной информации по товарам в счете */
    @Ignore
    public List<Detail> details;

    /**
     * Создание пустого нового счета
     */
    public Invoice() {
    }

    /**
     * Создание нового счета (кроме счетов со статусом "отгрузка")
     * @param number номер счета
     * @param date дата счета
     * @param sum итоговая сумма счета
     * @param status статус счета (См. {@link Invoice#status})
     */
    @Ignore
    public Invoice(int number, String date, double sum, String status) {
        init(number, date, sum, status, 0);
    }

    /**
     * Создание нового счета (только для счетов со статусом "отгрузка")
     * @param number номер счета
     * @param date дата счета
     * @param status статус счета (См. {@link Invoice#status})
     * @param waybill номер накладной
     */
    @Ignore
    public Invoice(int number, String date, String status, int waybill) {
        init(number, date, 0.0, status, waybill);
    }

    @Ignore
    private void init(int number, String date, double sum, String status, int waybill){
        this.number = number;
        this.date = date;
        this.status = status;
        this.sum = sum;
        this.waybill = waybill;
        this.details = new ArrayList<>();
    }
}
