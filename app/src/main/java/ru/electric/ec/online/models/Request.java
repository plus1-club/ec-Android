package ru.electric.ec.online.models;

public class Request {
    public String product;
    public int requestCount;
    public int stockCount;
    public double price;
    public double sum;
    public int multiplicity;
    public String unit;
    public boolean check;

    public Request(String product, int requestCount, int stockCount,
                   int multiplicity, String unit, double price, boolean check) {
        init(product, requestCount, stockCount, multiplicity, unit, price, check);
    }

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
