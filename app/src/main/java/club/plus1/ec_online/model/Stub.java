package club.plus1.ec_online.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Stub {

    private static Stub mInstance;
    public List<Request> basket;
    private List<Product> goods;
    public List<Invoice> invoices;

    private Stub() {
        goods = Stock.getInstance().goods;
        for (Product product : goods) {
            product.setPrice((new Random().nextDouble()) * (new Random().nextDouble()) * 10000.0);
        }

        basket = new ArrayList<>();
        basket.add(new Request("Legrand 774420 Розетка 2K+3 БЕЛ VLN",
                10, 0, 222.47));
        basket.add(new Request("Legrand 672221 Розетка 2К+3 БЕЛ ETIKA",
                20, 10, 134.57));
        basket.add(new Request("Legrand 411002 АВДТ DX3 1П+Н C16A 30MA-AC",
                5, 20, 3165.07));
        basket.add(new Request("Legrand 672220 Розетка 2K БЕЛ ETIKA",
                10, 20, 112.23));
        basket.add(new Request("Schneider MGU5.418.25ZD 2 USB Зарядное устройство, 2.1A, беж",
                1, 0, 2141.81));

        invoices = new ArrayList<>();
        invoices.add(new Invoice(1756503, "27.03.2019", 7970.98, "Неподтвержденный резерв"));
        invoices.add(new Invoice(1753858, "27.03.2019", 5274.93, "Неподтвержденный резерв"));
        invoices.add(new Invoice(1737303, "27.03.2019", 85.03, "Неподтвержденный резерв"));
        invoices.add(new Invoice(1736947, "27.03.2019", 1924.70, "Неподтвержденный резерв"));
        invoices.add(new Invoice(2756503, "27.03.2019", 7970.98, "Резерв"));
        invoices.add(new Invoice(2753858, "27.03.2019", 5274.93, "Резерв"));
        invoices.add(new Invoice(2737303, "27.03.2019", 85.03, "Резерв"));
        invoices.add(new Invoice(2736947, "27.03.2019", 1924.70, "Резерв"));
        invoices.add(new Invoice(3756503, "27.03.2019", 7970.98, "Заказ"));
        invoices.add(new Invoice(3753858, "27.03.2019", 5274.93, "Заказ"));
        invoices.add(new Invoice(3737303, "27.03.2019", 85.03, "Заказ"));
        invoices.add(new Invoice(3736947, "27.03.2019", 1924.70, "Заказ"));
        invoices.add(new Invoice(4756503, "27.03.2019", 7970.98, "Аннулирован"));
        invoices.add(new Invoice(4753858, "27.03.2019", 5274.93, "Аннулирован"));
        invoices.add(new Invoice(4737303, "27.03.2019", 85.03, "Просрочен"));
        invoices.add(new Invoice(4736947, "27.03.2019", 1924.70, "Просрочен"));
        invoices.add(new Invoice(5756503, "27.03.2019", 7970.98, "Отгружен", 39737));
        invoices.add(new Invoice(5753858, "27.03.2019", 5274.93, "Отгружен", 39738));
        invoices.add(new Invoice(5737303, "27.03.2019", 85.03, "Отгружен", 39739));
        invoices.add(new Invoice(5736947, "27.03.2019", 1924.70, "Отгружен", 39740));
    }

    public static Stub getInstance() {
        if (mInstance == null) {
            mInstance = new Stub();
        }
        return mInstance;
    }

    public List<Request> getRequests(String search, int count) {
        List<Request> requests = new ArrayList<>();
        boolean isSearch = !(search.isEmpty());
        boolean isCount = (count != 0);
        for (Product product : goods) {
            int requestCount = isCount ? count : 1;
            if (isSearch) {
                if (product.product.toLowerCase().contains(search.toLowerCase())) {
                    requests.add(new Request(product.product, requestCount, product.count));
                }
            } else {
                requests.add(new Request(product.product, requestCount, product.count));
            }
        }
        return requests;
    }
}
