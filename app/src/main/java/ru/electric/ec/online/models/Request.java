package ru.electric.ec.online.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Класс информации по найденным товарам
 * @author Сергей Лавров
 * @version 0.8
 */
@Entity
public class Request {

    /** Идентификатор для строк таблицы */
    @PrimaryKey
    public int id = 0;

    /** Искомый товар или код товара */
    @ColumnInfo(name = "product")
    public String product;

    /** Запрошеный товар */
    @ColumnInfo(name = "requestProduct")
    public String requestProduct;

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

    /** Необходимо обновить статус с сервера, т.к. изменились параметры на клиенте */
    @ColumnInfo(name = "needUpdate")
    public boolean needUpdate;

    /** Количество вариантов при расширенном поиске */
    @ColumnInfo(name = "variantsCount")
    public int variantsCount;

    /** Вид строки:
     *  1 - флаг,
     *  2 - переключатель,
     *  3 - группа
     *  4 - не найдено,
    */
    @ColumnInfo(name = "itemType")
    public int itemType;

    /** Статус количества */
    @ColumnInfo(name = "status")
    public String status;

    /** Название цвета статуса количества */
    @ColumnInfo(name = "colorName")
    public String colorName;

    /** Цвет статуса количества */
    @ColumnInfo(name = "color")
    public int color;

    /**
     * Создание пустой новой строки
     */
    public Request() {
    }

    /**
     * Создание новой строки с найденным товаром (с ценой)
     * @param product название товара
     * @param requestProduct запрошенный товар
     * @param requestCount запрошенное количество товара
     * @param stockCount количество товара на складе
     * @param multiplicity кратность товара (размер минимальной неделимой партии)
     * @param unit единица измерения товара
     * @param price цена товара
     * @param check установлена ли галочка у товара
     * @param needUpdate необходимо обновить информацию с сервера
     * @param variantsCount количество вариантов при расширенном поиске
     * @param itemType вид строки
     * @param status название цвета статуса количества
     * @param colorName вид строки
     * @param color цвет статуса количества
     */
    @Ignore
    public Request(String product, String requestProduct, int requestCount, int stockCount,
                   int multiplicity, String unit, double price,
                   boolean check, boolean needUpdate, int variantsCount, int itemType,
                   String status, String colorName, int color) {
        init(product, requestProduct, requestCount, stockCount, multiplicity, unit, price,
                check, needUpdate, variantsCount, itemType, status, colorName, color);
    }

    /**
     * Создание новой строки с найденным товаром (без цены)
     * @param product название товара
     * @param requestProduct запрошенный товар
     * @param requestCount запрошенное количество товара
     * @param stockCount количество товара на складе
     * @param multiplicity кратность товара (размер минимальной неделимой партии)
     * @param unit единица измерения товара
     * @param check установлена ли галочка у товара
     * @param variantsCount количество вариантов при расширенном поиске
     * @param itemType вид строки
     * @param status название цвета статуса количества
     * @param colorName вид строки
     * @param color цвет статуса количества
     */
    @Ignore
    public Request(String product, String requestProduct, int requestCount, int stockCount,
                   int multiplicity, String unit,
                   boolean check, boolean needUpdate, int variantsCount, int itemType,
                   String status, String colorName, int color) {
        init(product, requestProduct, requestCount, stockCount, multiplicity, unit, 0,
                check, needUpdate, variantsCount, itemType, status, colorName, color);
    }

    @Ignore
    private void init(String product, String requestProduct, int requestCount, int stockCount,
                      int multiplicity, String unit, double price,
                      boolean check, boolean needUpdate, int variantsCount, int itemType,
                      String status, String colorName, int color){
        this.product = product;
        this.requestProduct = requestProduct;
        this.requestCount = requestCount;
        this.stockCount = stockCount;
        this.multiplicity = multiplicity;
        this.unit = unit;
        this.price = price;
        this.check = check;
        this.variantsCount = variantsCount;
        this.itemType = itemType;
        this.needUpdate = needUpdate;
        this.status = status;
        this.colorName = colorName;
        this.color = color;
        sum = requestCount * price;
    }
}
