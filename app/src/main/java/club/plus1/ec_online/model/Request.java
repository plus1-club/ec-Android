package club.plus1.ec_online.model;

public class Request {
    public String product;
    public int requestCount;
    public int stockCount;
    public double price;
    public double sum;

    Request(String product, int requestCount, int stockCount, double price) {
        this.product = product;
        this.requestCount = requestCount;
        this.stockCount = stockCount;
        this.price = price;
        this.sum = requestCount * price;
    }
}
