package ru.electric.ec.online.domains;

public class Request {
    public String product;
    public int requestCount;
    public int stockCount;
    public double price;
    public double sum;
    public boolean check;

    public Request(String product, int requestCount, int stockCount, double price, boolean check) {
        this.product = product;
        this.requestCount = requestCount;
        this.stockCount = stockCount;
        this.price = price;
        this.check = check;
        sum = requestCount * price;
    }

    public Request(String product, int requestCount, int stockCount, boolean check) {
        this.product = product;
        this.requestCount = requestCount;
        this.stockCount = stockCount;
        this.check = check;
        price = 0;
        sum = 0;
    }

    /*
    public void setPrice(double price) {
        this.price = price;
        sum = requestCount * price;
    }
    */

    public void setCount(int requestCount, boolean check) {
        this.requestCount = requestCount;
        this.check = check;
        sum = this.requestCount * price;
    }
}
