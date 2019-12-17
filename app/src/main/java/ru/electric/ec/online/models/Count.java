package ru.electric.ec.online.models;

public class Count {
    public int count;
    private int stockCount;
    public String status;
    public int color;

    public Count(int count, int stockCount, String status, int color){
        this.count = count;
        this.stockCount = stockCount;
        this.color = color;
        this.status = status;
    }
}
