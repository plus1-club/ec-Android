package ru.electric.ec.online;

import ru.electric.ec.online.ui.InvoiceTableViewModel;
import ru.electric.ec.online.ui.RequestViewModel;

public class Model {
    public RequestViewModel request;
    public InvoiceTableViewModel invoice;
    public String token;

    Model() {
        init();
    }

    private void init() {
        token = "";
        request = RequestViewModel.getInstance();
        invoice = InvoiceTableViewModel.getInstance();
    }
}
