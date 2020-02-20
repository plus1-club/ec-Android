package ru.electric.ec.online.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Класс информации по найденным товарам
 * @author Сергей Лавров
 * @version 0.6
 */
@Entity
public class Request {

    /** Идентификатор для строк таблицы */
    @PrimaryKey
    public int id = 0;

    /** Искомый товар или код товара */
    @ColumnInfo(name = "product")
    public String product;

    /** Запрошеное количетсов товара */
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

    /**
     * Создание пустой новой строки
     */
    public Request() {
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
     */
    @Ignore
    public Request(String product, int requestCount, int stockCount,
                   int multiplicity, String unit, double price, boolean check) {
        init(product, requestCount, stockCount, multiplicity, unit, price, check);
    }

    /**
     * Создание новой строки с найденным товаром (без цены)
     * @param product название товара
     * @param requestCount запрошенное количество товара
     * @param stockCount количество товара на складе
     * @param multiplicity кратность товара (размер минимальной неделимой партии)
     * @param unit единица измерения товара
     * @param check установлена ли галочка у товара
     */
    @Ignore
    public Request(String product, int requestCount, int stockCount,
                   int multiplicity, String unit, boolean check) {
        init(product, requestCount, stockCount, multiplicity, unit, 0, check);
    }

    @Ignore
    private void init(String product, int requestCount, int stockCount,
                      int multiplicity, String unit, double price, boolean check){
        this.product = product;
        this.requestCount = requestCount;
        this.stockCount = stockCount;
        this.multiplicity = multiplicity;
        this.unit = unit;
        this.price = price;
        this.check = check;
        sum = requestCount * price;
    }
}
