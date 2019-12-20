package ru.electric.ec.online.models;

import java.util.ArrayList;
import java.util.List;

public class Invoice {

    public int number;
    public String date;
    public double sum;
    public String status;
    public int waybill;

    public List<Detail> details;

    public Invoice(int number, String date, double sum, String status) {
        init(number, date, sum, status, 0);
    }

    public Invoice(int number, String date, String status, int waybill) {
        init(number, date, 0.0, status, waybill);
    }

    private void init(int number, String date, double sum, String status, int waybill){
        this.number = number;
        this.date = date;
        this.status = status;
        this.sum = sum;
        this.waybill = waybill;
        this.details = new ArrayList<>();
    }
}
