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

    /** Сообщение от сервера */
    @ColumnInfo(name = "message")
    public String message;

    /** Вид строки:
     *  1 - обычный,
     *  2 - сообщение от сервера
     */
    @ColumnInfo(name = "itemType")
    public int itemType;

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
    public Invoice(int number, String date, double sum, String status, String message, int itemType) {
        init(number, date, sum, status, 0, message, itemType);
    }

    /**
     * Создание нового счета (только для счетов со статусом "отгрузка")
     * @param number номер счета
     * @param date дата счета
     * @param status статус счета (См. {@link Invoice#status})
     * @param waybill номер накладной
     */
    @Ignore
    public Invoice(int number, String date, String status, int waybill, String message, int itemType) {
        init(number, date, 0.0, status, waybill, message, itemType);
    }

    @Ignore
    private void init(int number, String date, double sum, String status, int waybill, String message, int itemType){
        this.number = number;
        this.date = date;
        this.status = status;
        this.sum = sum;
        this.waybill = waybill;
        this.message = message;
        this.itemType = itemType;
        this.details = new ArrayList<>();
    }
}
