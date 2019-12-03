package ru.electric.ec.online.domains;

import java.util.ArrayList;
import java.util.List;

public class Model {
    public String token;
    public List<Request> search;
    public List<Request> basket;
    public List<Invoice> invoices;

    public Model() {
        token = "";
        search = new ArrayList<>();
        basket = new ArrayList<>();
        invoices = new ArrayList<>();
    }

}
