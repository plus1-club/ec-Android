package club.plus1.ec_electric.model;

public class Request {
    public String product;
    public int count;
    public double price;
    public double sum;

    public Request(String product, int count, double price){
        this.product = product;
        this.count = count;
        this.price = price;
        this.sum = count * price;
    }
}
