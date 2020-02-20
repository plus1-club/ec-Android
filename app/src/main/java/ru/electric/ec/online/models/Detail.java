package ru.electric.ec.online.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Класс детальной информации по конкретному товару из счета
 * @author Сергей Лавров
 * @version 0.6
 */
@Entity
public class Detail {

    /** Идентификатор для строк таблицы */
    @PrimaryKey
    public int id = 0;

    /** Номер счета */
    @ColumnInfo(name = "number")
    public int number;

    /** Название товара */
    @ColumnInfo(name = "product")
    public String product;

    /** Количество товаров */
    @ColumnInfo(name = "count")
    public int count;

    /** Единица измерения товара */
    @ColumnInfo(name = "unit")
    public String unit;

    /** Цена товара */
    @ColumnInfo(name = "price")
    public double price;

    /** Сумма по товару (цена * количество) */
    @ColumnInfo(name = "sum")
    public double sum;

    /** Доступность товара на складе */
    @ColumnInfo(name = "available")
    public String available;

    /** Срок доставки товаров */
    @ColumnInfo(name = "delivery")
    public String delivery;

    /**
     * Создание пустой детальной информации по счету
     */
    public Detail() {
    }

    /**
     * Создание новой детальной информации по счету
     * @param product название товара
     * @param count количество товара
     * @param unit единица измерения товара
     * @param price цена товара
     * @param sum сумма по товару
     * @param available доступность товара на складе
     * @param delivery срок доставки товара
     */
    @Ignore
    public Detail(String product, int count, String unit, double price, double sum,
                  String available, String delivery) {
        init(product, count, unit, price, sum, available, delivery);
    }

    @Ignore
    private void init(String product, int count, String unit, double price, double sum,
              String available, String delivery){
        this.product = product;
        this.count = count;
        this.unit = unit;
        this.price = price;
        this.sum = sum;
        this.available = available;
        this.delivery = (delivery == null) ? "" : delivery;
    }
}
