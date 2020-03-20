package ru.electric.ec.online.models;

public interface Request {

    /** Искомый товар или код товара */
    String product = "";

    /** Запрошеный товар */
    String requestProduct = "";

    /** Запрошеное количетсов товара */
    int requestCount = 0;

    /** Количество товара на складе */
    int stockCount = 0;

    /** Кратность товара (размер минимальной неделимой партии) */
    int multiplicity = 1;

    /** Единица измерения количества товара */
    String unit = "";

    /** Установлена ли галочка у товара */
    boolean check = false;

    /** Необходимо обновить статус с сервера, т.к. изменились параметры на клиенте */
    boolean needUpdate = false;

    /** Количество вариантов при расширенном поиске */
    int variantsCount = 0;

    /** Вид строки:
     *  0 - неопределено,
     *  1 - флаг,
     *  2 - переключатель,
     *  3 - группа
     *  4 - не найдено,
     */
    int itemType = 0;

    /** Статус количества */
    String status = "";

    /** Название цвета статуса количества */
    String colorName = "";

    /** Цвет статуса количества */
    int color = 0;

}
