package ru.electric.ec.online.models;

public class Count {
    public int count;
    public int stockCount;
    public String status;
    public int color;
    String colorName;

    Count(int count, int stockCount, String status, int color, String text){
        init(count, stockCount, status, color, text);
    }

    private void init(int count, int stockCount, String status, int color, String colorName){
        this.count = count;
        this.stockCount = stockCount;
        this.status = status;
        this.color = color;
        this.colorName = colorName;
    }
}
