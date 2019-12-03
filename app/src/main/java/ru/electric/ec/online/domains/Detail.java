package ru.electric.ec.online.domains;

public class Detail {

    public String product;
    public int count;
    public double price;
    public double sum;
    public String available;
    public String delivery;

    public Detail(String product, int count, double price, double sum,
                  String available, String delivery) {
        this.product = product;
        this.count = count;
        this.price = price;
        this.sum = sum;
        this.available = available;
        this.delivery = delivery;
    }
}
