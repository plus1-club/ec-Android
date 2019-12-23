package ru.electric.ec.online.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс информации по счету
 * @author Сергей Лавров
 * @version 0.5
 */
public class Invoice {

    /** Номер счета */
    public int number;

    /** Дата счета */
    public String date;

    /** Итоговая сумма по счету */
    public double sum;

    /** Статус счета
     * - неподтвержденный резерв,
     * - резерв,
     * - заказ,
     * - аннулированный счет,
     * - просроченнй счето
     * - отгрузка) */
    public String status;

    /** Номер накладной */
    public int waybill;

    /** Список детальной информации по товарам в счете */
    public List<Detail> details;

    /**
     * Создание нового счета (кроме счетов со статусом "отгрузка")
     * @param number номер счета
     * @param date дата счета
     * @param sum итоговая сумма счета
     * @param status статус счета (См. {@see Invoice#status})
     */
    public Invoice(int number, String date, double sum, String status) {
        init(number, date, sum, status, 0);
    }

    /**
     * Создание нового счета (только для счетов со статусом "отгрузка")
     * @param number номер счета
     * @param date дата счета
     * @param status статус счета (См. {@see Invoice#status})
     * @param waybill номер накладной
     */
    public Invoice(int number, String date, String status, int waybill) {
        init(number, date, 0.0, status, waybill);
    }

    private void init(int number, String date, double sum, String status, int waybill){
        this.number = number;
        this.date = date;
        this.status = status;
        this.sum = sum;
        this.waybill = waybill;
        this.details = new ArrayList<>();
    }
}
