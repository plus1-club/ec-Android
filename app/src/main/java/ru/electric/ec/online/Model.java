package ru.electric.ec.online;

import ru.electric.ec.online.viewmodels.InvoiceTableViewModel;
import ru.electric.ec.online.viewmodels.RequestViewModel;

public class Model {
    public RequestViewModel request;
    public InvoiceTableViewModel invoice;
    public String token;

    Model() {
        token = "";
        request = RequestViewModel.getInstance();
        invoice = InvoiceTableViewModel.getInstance();
    }
}
