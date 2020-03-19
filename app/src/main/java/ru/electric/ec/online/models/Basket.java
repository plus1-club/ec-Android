package ru.electric.ec.online.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Класс для хранения содержимого корзины
 * @author Сергей Лавров
 * @version 0.6
 */
@Entity
public class Basket {

    /** Идентификатор для строк таблицы */
    @PrimaryKey
    public int id = 0;

    /** Товар или код товара */
    @ColumnInfo(name = "product")
    public String product;

    /** Запрошеное количество товара */
    @ColumnInfo(name = "requestCount")
    public int requestCount;

    /** Количество товара на складе */
    @ColumnInfo(name = "stockCount")
    public int stockCount;

    /** Цена товара */
    @ColumnInfo(name = "price")
    public double price;

    /** Сумма по товару (цена * запрошенное количество) */
    @ColumnInfo(name = "sum")
    public double sum;

    /** Кратность товара (размер минимальной неделимой партии) */
    @ColumnInfo(name = "multiplicity")
    public int multiplicity;

    /** Единица измерения количества товара */
    @ColumnInfo(name = "unit")
    public String unit;

    /** Установлена ли галочка у товара */
    @ColumnInfo(name = "check")
    public boolean check;

    /** Статус количества */
    @ColumnInfo(name = "textStatus")
    public String textStatus;

    /** Название цвета статуса количества */
    @ColumnInfo(name = "colorName")
    public String colorName;

    /** Цвет статуса количества */
    @ColumnInfo(name = "color")
    public int color;
}
