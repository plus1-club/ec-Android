package club.plus1.ec_online.model;

import java.util.ArrayList;
import java.util.List;

public class Stub {

    public List<Request> basket;

    public Stub(){
        basket = new ArrayList<>();
        basket.add(new Request("1.Legrand 774420 Розетка 2K+3 БЕЛ VLN",
                10, 0, 222.47));
        basket.add(new Request("2.Legrand 672221 Розетка 2К+3 БЕЛ ETIKA",
                20, 10, 134.57));
        basket.add(new Request("3.Legrand 411002 АВДТ DX3 1П+Н C16A 30MA-AC",
                5, 20, 3165.07));
        basket.add(new Request("4.Legrand 672220 Розетка 2K БЕЛ ETIKA",
                10, 20, 112.23));
        basket.add(new Request("5.Schneider MGU5.418.25ZD 2 USB Зарядное устройство, 2.1A, беж",
                1, 0, 2141.81));

    }

    public List<Request> getRequests(String search, int count) {
        List<Request> requests = new ArrayList<>();
        boolean isSearch = !(search.isEmpty());
        boolean isCount = (count != 0);
        for (Request request : basket) {
            int requestCount = isCount ? count : request.requestCount;
            if (isSearch) {
                if (request.product.toLowerCase().contains(search.toLowerCase())) {
                    requests.add(new Request(request.product, requestCount, request.stockCount));
                }
            } else {
                requests.add(new Request(request.product, requestCount, request.stockCount));
            }
        }
        return requests;
    }
}
