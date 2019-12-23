package ru.electric.ec.online.models;

/**
 * Класс информации по найденным товарам
 * @author Сергей Лавров
 * @version 0.5
 */
public class Request {

    /** Искомый товар или код товара */
    public String product;

    /** Запрошеное количетсов товара */
    public int requestCount;

    /** Количество товара на складе */
    public int stockCount;

    /** Цена товара */
    public double price;

    /** Сумма по товару (цена * запрошенное количетство) */
    public double sum;

    /** Кратность товара (размер минимальной неделимой партии) */
    public int multiplicity;

    /** Единица измерения количества товара */
    public String unit;

    /** Установлена ли галочка у товара */
    public boolean check;

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
    public Request(String product, int requestCount, int stockCount,
                   int multiplicity, String unit, boolean check) {
        init(product, requestCount, stockCount, multiplicity, unit, 0, check);
    }

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
