package club.plus1.ec_online.domains;

import java.util.ArrayList;
import java.util.List;

public class Model {
    public String token;
    public List<Request> basket;
    public List<Invoice> invoices;

    public Model() {
        token = "";
        basket = new ArrayList<>();
        invoices = new ArrayList<>();
    }

}
