package club.plus1.ec_online.domain;

public class Request {
    public String product;
    public int requestCount;
    public int stockCount;
    public double price;
    public double sum;

    public Request(String product, int requestCount, int stockCount, double price) {
        this.product = product;
        this.requestCount = requestCount;
        this.stockCount = stockCount;
        this.price = price;
        sum = requestCount * price;
    }

    public Request(String product, int requestCount, int stockCount) {
        this.product = product;
        this.requestCount = requestCount;
        this.stockCount = stockCount;
        price = 0;
        sum = 0;
    }

    public void setPrice(double price) {
        this.price = price;
        sum = requestCount * price;
    }

    public void setCount(int requestCount) {
        this.requestCount = requestCount;
        sum = this.requestCount * price;
    }
}
