package club.plus1.ec_online.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import club.plus1.ec_online.domains.Detail;
import club.plus1.ec_online.domains.Invoice;
import club.plus1.ec_online.domains.Product;
import club.plus1.ec_online.domains.Request;

public class StorageStub {

    private static StorageStub mInstance;
    private List<Request> basket;
    private List<Product> goods;

    public List<Invoice> invoicesEmpty;
    public List<Invoice> invoicesUnconf;
    public List<Invoice> invoicesReserv;
    public List<Invoice> invoicesOrder;
    public List<Invoice> invoicesCancel;
    public List<Invoice> invoicesShip;

    private List<Detail> details1;
    private List<Detail> details2;
    private List<Detail> details3;
    private List<Detail> details4;

    private StorageStub() {
        goods = StorageStock.getInstance().goods;
        for (Product product : goods) {
            product.setPrice((new Random().nextDouble()) * (new Random().nextDouble()) * 10000.0);
        }

        basket = new ArrayList<>();
        basket.add(new Request("Legrand 774420 Розетка 2K+3 БЕЛ VLN",
                10, 0, 222.47, true));
        basket.add(new Request("Legrand 672221 Розетка 2К+3 БЕЛ ETIKA",
                20, 10, 134.57, true));
        basket.add(new Request("Legrand 411002 АВДТ DX3 1П+Н C16A 30MA-AC",
                5, 20, 3165.07, true));
        basket.add(new Request("Legrand 672220 Розетка 2K БЕЛ ETIKA",
                10, 20, 112.23, true));
        basket.add(new Request("Schneider MGU5.418.25ZD 2 USB Зарядное устройство, 2.1A, беж",
                1, 0, 2141.81, true));

        details1 = new ArrayList<>(); // 1029,89
        details1.add(new Detail("Legrand 774420 Розетка 2K+3 БЕЛ VLN",
                2, 222.47, 444.94, "Нет", "2-3 недели"));
        details1.add(new Detail("Legrand 672221 Розетка 2К+3 БЕЛ ETIKA",
                6, 807.42, 448.56, "В наличии", "5-7 дней"));

        details2 = new ArrayList<>(); // 2141.81
        details2.add(new Detail("Schneider MGU5.418.25ZD 2 USB Зарядное устройство, 2.1A, беж",
                1, 2141.81, 2141.81, "Нет", "2-3 недели"));

        details3 = new ArrayList<>(); // 5633.07
        details3.add(new Detail("Legrand 672221 Розетка 2К+3 БЕЛ ETIKA",
                10, 134.57, 1345.70, "В наличии", "На складе"));
        details3.add(new Detail("Legrand 411002 АВДТ DX3 1П+Н C16A 30MA-AC",
                1, 3165.07, 3165.07, "В наличии", "5-7 дней"));
        details3.add(new Detail("Legrand 672220 Розетка 2K БЕЛ ETIKA",
                10, 112.23, 1122.30, "В наличии", "5-7 дней"));

        details4 = new ArrayList<>(); // 5776.15‬
        details4.add(new Detail("Legrand 774420 Розетка 2K+3 БЕЛ VLN",
                1, 222.47, 222.47, "Нет", "2-3 недели"));
        details4.add(new Detail("Legrand 672221 Розетка 2К+3 БЕЛ ETIKA",
                1, 134.57, 134.57, "В наличии", "5-7 дней"));
        details4.add(new Detail("Legrand 411002 АВДТ DX3 1П+Н C16A 30MA-AC",
                1, 3165.07, 3165.07, "В наличии", "На складе"));
        details4.add(new Detail("Legrand 672220 Розетка 2K БЕЛ ETIKA",
                1, 112.23, 112.23, "В наличии", "5-7 дней"));
        details4.add(new Detail("Schneider MGU5.418.25ZD 2 USB Зарядное устройство, 2.1A, беж",
                1, 2141.81, 2141.81, "Нет", "2-3 недели"));

        invoicesEmpty = new ArrayList<>();

        invoicesUnconf = new ArrayList<>();
        invoicesUnconf.add(new Invoice(1756503, "27.03.2019", "Неподтвержденный резерв", details1));
        invoicesUnconf.add(new Invoice(1753858, "27.03.2019", "Неподтвержденный резерв", details2));

        invoicesReserv = new ArrayList<>();
        invoicesReserv.add(new Invoice(2753858, "27.03.2019", "Резерв", details2));
        invoicesReserv.add(new Invoice(2737303, "27.03.2019", "Резерв", details3));
        invoicesReserv.add(new Invoice(2736947, "27.03.2019", "Резерв", details4));

        invoicesOrder = new ArrayList<>();
        invoicesOrder.add(new Invoice(3736947, "27.03.2019", "Заказ", details4));

        invoicesCancel = new ArrayList<>();
        invoicesCancel.add(new Invoice(4756503, "27.03.2019", "Аннулирован", details1));
        invoicesCancel.add(new Invoice(4753858, "27.03.2019", "Аннулирован", details2));
        invoicesCancel.add(new Invoice(4737303, "27.03.2019", "Просрочен", details3));
        invoicesCancel.add(new Invoice(4736947, "27.03.2019", "Просрочен", details4));

        invoicesShip = new ArrayList<>();
        invoicesShip.add(new Invoice(5756503, "27.03.2019", "Отгружен", 39737, details1));
        invoicesShip.add(new Invoice(5736947, "27.03.2019", "Отгружен", 39740, details4));
    }

    public static StorageStub getInstance() {
        if (mInstance == null) {
            mInstance = new StorageStub();
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
                    requests.add(new Request(product.product, requestCount, product.count, false));
                }
            } else {
                requests.add(new Request(product.product, requestCount, product.count, false));
            }
        }
        return requests;
    }
}
