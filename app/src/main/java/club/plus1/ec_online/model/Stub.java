package club.plus1.ec_online.model;

import java.util.ArrayList;
import java.util.List;

public class Stub {

    public List<Request> requests;
    public List<Request> basket;

    public Stub(){
        requests = new ArrayList<>();
        requests.add(new Request("1.Legrand 774420 Розетка 2K+3 БЕЛ VLN",
                10, 0, 222.47));
        requests.add(new Request("2.Legrand 672221 Розетка 2К+3 БЕЛ ETIKA",
                10, 10, 134.57));
        requests.add(new Request("3.Legrand 411002 АВДТ DX3 1П+Н C16A 30MA-AC",
                10, 20, 3165.07));
        requests.add(new Request("4.Legrand 672220 Розетка 2K БЕЛ ETIKA",
                10, 20, 112.23));
        basket = new ArrayList<>();
        basket.add(new Request("1.Legrand 774420 Розетка 2K+3 БЕЛ VLN",
                10, 0, 222.47));
        basket.add(new Request("2.Legrand 672221 Розетка 2К+3 БЕЛ ETIKA",
                20, 10, 134.57));
        basket.add(new Request("3.Legrand 411002 АВДТ DX3 1П+Н C16A 30MA-AC",
                5, 20, 3165.07));
        basket.add(new Request("4.Legrand 672220 Розетка 2K БЕЛ ETIKA",
                10, 20, 112.23));
    }
}
