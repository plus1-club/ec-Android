package ru.electric.ec.online.models;

/**
 * Класс детальной информации по конкретному товару из счета
 * @author Сергей Лавров
 * @version 0.5
 */
public class Detail {

    /** Название товара */
    public String product;

    /** Количество товаров */
    public int count;

    /** Единица измерения товара */
    public String unit;

    /** Цена товара */
    public double price;

    /** Сумма по товару (цена * количество) */
    public double sum;

    /** Доступность товара на складе */
    public String available;

    /** Срок доставки товаров */
    public String delivery;

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
    public Detail(String product, int count, String unit, double price, double sum,
                  String available, String delivery) {
        init(product, count, unit, price, sum, available, delivery);
    }

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
