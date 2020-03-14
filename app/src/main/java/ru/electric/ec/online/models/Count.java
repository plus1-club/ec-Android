package ru.electric.ec.online.models;

/**
 * Класс статуса количеста товара
 * @author Сергей Лавров
 * @version 0.5
 */
public class Count {

    /** Запрошенное количество товаров */
    public int count;

    /** Количество товаров на складе */
    public int stockCount;

    /** Запрошенный товар */
    public String requestProduct;

    /** Текст статуса товара */
    public String status;

    /** Идентификатор цвета статуса количества */
    public int color;

    /** Название цвета */
    public String colorName;

    /**
     * Создание нового статуса количества
     * @param count нужное количество товаров
     * @param stockCount количество товаров на складе
     * @param status строка со статусом товара, которая показывается пользователю
     * @param color идентификатор цвета статуса количества из ресурсов
     * @param colorName название цвета (для удобства отладки)
     */
    public Count(int count, int stockCount, String requestProduct, String status, int color, String colorName){
        init(count, stockCount, requestProduct, status, color, colorName);
    }

    private void init(int count, int stockCount, String requestProduct, String status, int color, String colorName){
        this.count = count;
        this.stockCount = stockCount;
        this.requestProduct = requestProduct;
        this.status = status;
        this.color = color;
        this.colorName = colorName;
    }
}
