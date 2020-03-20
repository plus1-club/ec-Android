package ru.electric.ec.online.common;

import ru.electric.ec.online.ui.basket.BasketViewModel;
import ru.electric.ec.online.ui.invoice.InvoiceViewModel;
import ru.electric.ec.online.ui.request.RequestViewModel;
import ru.electric.ec.online.ui.search.SearchViewModel;

public class Model {
    public RequestViewModel request;
    public SearchViewModel search;
    public BasketViewModel basket;
    public InvoiceViewModel invoice;
    public String token;

    Model() {
        init();
    }

    private void init() {
        token = "";
        request = RequestViewModel.getInstance();
        search = SearchViewModel.getInstance();
        basket = BasketViewModel.getInstance();
        invoice = InvoiceViewModel.getInstance();
    }
}
