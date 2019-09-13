package club.plus1.ec_electric.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import club.plus1.ec_electric.R;
import club.plus1.ec_electric.model.Request;
import club.plus1.ec_electric.viewmodel.RequestBasketAdapter;
import club.plus1.ec_electric.viewmodel.RequestTableAdapter;

public class RequestBasketActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_basket);

        recyclerView = findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // specify an adapter (see also next example)
        List<Request> requests = new ArrayList<>();
        requests.add(new Request("1.Schneider MGU5.418.25ZD 2 USB Зарядное устройство, 2.1A, беж",
                1, 2141.81));
        requests.add(new Request("2.Legrand 774420 Розетка 2К+3 нем.ст.VALENA белый",
                201, 151.65));
        requests.add(new Request("3.Schneider PA16-004D 1ая розетка с заземлением, со штор.",
                18, 159.29));
        requests.add(new Request("4.Legrand 10954 Суппорт/Рамка 4 М Dpl Кр.65",
                20, 162.74));

        RequestBasketAdapter requestBasketAdapter = new RequestBasketAdapter();
        requestBasketAdapter.setItems(requests);
        adapter = requestBasketAdapter;
        recyclerView.setAdapter(adapter);
    }
}
