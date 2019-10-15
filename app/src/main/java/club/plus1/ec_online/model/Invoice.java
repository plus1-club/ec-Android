package club.plus1.ec_online.model;

public class Invoice {

    public int number;
    public String date;
    public double sum;
    public String status;
    public int waybill;

    Invoice(int number, String date, double sum, String status, int waybill) {
        this.number = number;
        this.date = date;
        this.sum = sum;
        this.status = status;
        this.waybill = waybill;
    }

    Invoice(int number, String date, double sum, String status) {
        this.number = number;
        this.date = date;
        this.sum = sum;
        this.status = status;
        waybill = 0;
    }

    public Invoice(int number, String date, String status, int waybill) {
        this.number = number;
        this.date = date;
        sum = 0.0;
        this.status = status;
        this.waybill = waybill;
    }
}
