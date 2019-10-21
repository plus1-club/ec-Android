package club.plus1.ec_online.domain;

import java.util.ArrayList;
import java.util.List;

public class Invoice {

    public int number;
    public String date;
    public double sum;
    public String status;
    public int waybill;

    public List<Detail> details;

    public Invoice(int number, String date, String status, int waybill, List<Detail> details) {
        this.number = number;
        this.date = date;
        this.status = status;
        this.waybill = waybill;
        this.details = new ArrayList<>();
        this.details.addAll(details);
        sum = 0.0;
        for (Detail item : details) {
            sum = sum + item.sum;
        }
    }

    public Invoice(int number, String date, String status, List<Detail> details) {
        this.number = number;
        this.date = date;
        this.status = status;
        waybill = 0;
        this.details = new ArrayList<>();
        this.details.addAll(details);
        sum = 0.0;
        for (Detail item : details) {
            sum = sum + item.sum;
        }
    }
}
