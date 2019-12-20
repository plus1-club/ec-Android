package ru.electric.ec.online.models;

public class Count {
    public int count;
    public int stockCount;
    public String status;
    public int color;

    public Count(int count, int stockCount, String status, int color){
        init(count, stockCount, status, color);
    }

    private void init(int count, int stockCount, String status, int color){
        this.count = count;
        this.stockCount = stockCount;
        this.status = status;
        this.color = color;
    }
}
