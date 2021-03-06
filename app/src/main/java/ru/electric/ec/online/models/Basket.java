package ru.electric.ec.online.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Класс для хранения содержимого корзины
 * @author Сергей Лавров
 * @version 0.6
 */
@Entity
public class Basket implements Request{

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
    @ColumnInfo(name = "status")
    public String status;

    /** Название цвета статуса количества */
    @ColumnInfo(name = "colorName")
    public String colorName;

    /** Цвет статуса количества */
    @ColumnInfo(name = "color")
    public int color;

    /** Необходимо обновить статус с сервера, т.к. изменились параметры на клиенте */
    @ColumnInfo(name = "needUpdate")
    public boolean needUpdate;

    /**
     * Создание пустой новой строки
     */
    public Basket() {
    }

    /**
     * Создание новой строки с найденным товаром (с ценой)
     * @param product название товара
     * @param requestCount запрошенное количество товара
     * @param stockCount количество товара на складе
     * @param multiplicity кратность товара (размер минимальной неделимой партии)
     * @param unit единица измерения товара
     * @param price цена товара
     * @param check установлена ли галочка у товара
     * @param needUpdate необходимо обновить информацию с сервера
     * @param status название цвета статуса количества
     * @param colorName вид строки
     * @param color цвет статуса количества
     */
    @Ignore
    public Basket(String product, int requestCount, int stockCount,
                  int multiplicity, String unit, double price,
                  boolean check, boolean needUpdate,
                  String status, String colorName, int color) {
        init(product, requestCount, stockCount,
                multiplicity, unit, price,
                check, needUpdate,
                status, colorName, color);
    }

    @Ignore
    private void init(String product, int requestCount, int stockCount,
                      int multiplicity, String unit, double price,
                      boolean check, boolean needUpdate,
                      String status, String colorName, int color){
        this.product = product;
        this.requestCount = requestCount;
        this.stockCount = stockCount;
        this.multiplicity = multiplicity;
        this.unit = unit;
        this.price = price;
        this.check = check;
        this.needUpdate = needUpdate;
        this.status = status;
        this.colorName = colorName;
        this.color = color;
        sum = requestCount * price;
    }

    @Ignore
    public static Basket searchToBasket(Search s){
        return new Basket(s.product, s.requestCount, s.stockCount,
                s.multiplicity, s.unit, 0,
                true, false,
                s.status, s.colorName, s.color);
    }

}
