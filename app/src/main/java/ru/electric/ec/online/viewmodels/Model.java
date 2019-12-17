package ru.electric.ec.online.viewmodels;

public class Model {
    public RequestViewModel request;
    InvoiceTableViewModel invoice;
    String token;

    Model() {
        token = "";
        request = RequestViewModel.getInstance();
        invoice = InvoiceTableViewModel.getInstance();
    }
}
