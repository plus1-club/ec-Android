package ru.electric.ec.online;

import ru.electric.ec.online.ui.invoice.InvoiceViewModel;
import ru.electric.ec.online.ui.request.RequestViewModel;

public class Model {
    public RequestViewModel request;
    public InvoiceViewModel invoice;
    public String token;

    Model() {
        init();
    }

    private void init() {
        token = "";
        request = RequestViewModel.getInstance();
        invoice = InvoiceViewModel.getInstance();
    }
}
