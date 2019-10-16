package club.plus1.ec_online.model;

import java.util.ArrayList;
import java.util.List;

public class Invoice {

    public int number;
    public String date;
    public double sum;
    public String status;
    public int waybill;

    private List<Detail> details;

    Invoice(int number, String date, double sum, String status, int waybill, List<Detail> details) {
        this.number = number;
        this.date = date;
        this.sum = sum;
        this.status = status;
        this.waybill = waybill;
        this.details = new ArrayList<>();
        this.details.addAll(details);
    }

    Invoice(int number, String date, double sum, String status, List<Detail> details) {
        this.number = number;
        this.date = date;
        this.sum = sum;
        this.status = status;
        waybill = 0;
        this.details = new ArrayList<>();
        this.details.addAll(details);
    }

    public Invoice(int number, String date, String status, int waybill, List<Detail> details) {
        this.number = number;
        this.date = date;
        sum = 0.0;
        this.status = status;
        this.waybill = waybill;
        this.details = new ArrayList<>();
        this.details.addAll(details);
    }
}
