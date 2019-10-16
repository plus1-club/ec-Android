package club.plus1.ec_online.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Stub {

    private static Stub mInstance;
    public List<Request> basket;
    private List<Product> goods;
    public List<Invoice> invoices;
    public List<Detail> details;

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

        details = new ArrayList<>();
        details.add(new Detail("411504 Legrand ВДТ DX3 2G 25A 30mA-AC",
                2, 3985.59, 7970.98, "В наличии", "На складе"));
        details.add(new Detail("672221 Legrand Розетка 2K+3 винты БЕЛ ETIKA",
                6, 74.76, 448.56, "В наличии", "5-7 дней"));

        invoices = new ArrayList<>();
        invoices.add(new Invoice(1756503, "27.03.2019", 8419.54,
                "Неподтвержденный резерв", details));
        invoices.add(new Invoice(1753858, "27.03.2019", 5274.93,
                "Неподтвержденный резерв", details));
        invoices.add(new Invoice(1737303, "27.03.2019", 85.03,
                "Неподтвержденный резерв", details));
        invoices.add(new Invoice(1736947, "27.03.2019", 1924.70,
                "Неподтвержденный резерв", details));
        invoices.add(new Invoice(2756503, "27.03.2019", 8419.54,
                "Резерв", details));
        invoices.add(new Invoice(2753858, "27.03.2019", 5274.93,
                "Резерв", details));
        invoices.add(new Invoice(2737303, "27.03.2019", 85.03,
                "Резерв", details));
        invoices.add(new Invoice(2736947, "27.03.2019", 1924.70,
                "Резерв", details));
        invoices.add(new Invoice(3756503, "27.03.2019", 8419.54,
                "Заказ", details));
        invoices.add(new Invoice(3753858, "27.03.2019", 5274.93,
                "Заказ", details));
        invoices.add(new Invoice(3737303, "27.03.2019", 85.03,
                "Заказ", details));
        invoices.add(new Invoice(3736947, "27.03.2019", 1924.70,
                "Заказ", details));
        invoices.add(new Invoice(4756503, "27.03.2019", 8419.54,
                "Аннулирован", details));
        invoices.add(new Invoice(4753858, "27.03.2019", 5274.93,
                "Аннулирован", details));
        invoices.add(new Invoice(4737303, "27.03.2019", 85.03,
                "Просрочен", details));
        invoices.add(new Invoice(4736947, "27.03.2019", 1924.70,
                "Просрочен", details));
        invoices.add(new Invoice(5756503, "27.03.2019", 8419.54,
                "Отгружен", 39737, details));
        invoices.add(new Invoice(5753858, "27.03.2019", 5274.93,
                "Отгружен", 39738, details));
        invoices.add(new Invoice(5737303, "27.03.2019", 85.03,
                "Отгружен", 39739, details));
        invoices.add(new Invoice(5736947, "27.03.2019", 1924.70,
                "Отгружен", 39740, details));
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
