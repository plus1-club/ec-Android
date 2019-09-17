package club.plus1.ec_online.model;

import java.util.ArrayList;
import java.util.List;

public class Stub {

    public List<Request> requests;

    public Stub(){
        requests = new ArrayList<>();
        requests.add(new Request("1.Schneider MGU5.418.25ZD 2 USB Зарядное устройство, 2.1A, беж",
                1, 2141.81));
        requests.add(new Request("2.Legrand 774420 Розетка 2К+3 нем.ст.VALENA белый",
                201, 151.65));
        requests.add(new Request("3.Schneider PA16-004D 1ая розетка с заземлением, со штор.",
                18, 159.29));
        requests.add(new Request("4.Legrand 10954 Суппорт/Рамка 4 М Dpl Кр.65",
                20, 162.74));

    }
}
