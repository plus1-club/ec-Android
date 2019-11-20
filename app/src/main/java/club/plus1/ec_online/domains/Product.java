package club.plus1.ec_online.domains;

import androidx.annotation.NonNull;

public class Product {

    public String product;
    public int count;

    private String producer;
    private String code;
    private String name;
    private double price;

    public Product(String producer, String code, String name, int count) {
        this.producer = producer;
        this.code = code;
        this.name = name;
        this.count = count;
        this.price = 0.0;
        this.product = producer + " " + code + " " + name;
    }

    public Product(String producer, String code, String name) {
        this.producer = producer;
        this.code = code;
        this.name = name;
        this.count = 0;
        this.price = 0.0;
        this.product = producer + " " + code + " " + name;
    }

    @NonNull
    @Override
    public String toString() {
        return product;
    }

    public String getProducer() {
        return producer;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
