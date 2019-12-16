package ru.electric.ec.online.domains;

import ru.electric.ec.online.viewmodels.InvoiceTableViewModel;
import ru.electric.ec.online.viewmodels.RequestViewModel;

public class Model {
    public String token;
    public RequestViewModel request;
    public InvoiceTableViewModel invoice;

    public Model() {
        token = "";
        request = RequestViewModel.getInstance();
        invoice = InvoiceTableViewModel.getInstance();
    }
}
