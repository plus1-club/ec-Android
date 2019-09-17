package club.plus1.ec_electric.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import club.plus1.ec_electric.R;
import club.plus1.ec_electric.model.Request;
import club.plus1.ec_electric.model.Stub;
import club.plus1.ec_electric.viewmodel.RequestViewModel;
import club.plus1.ec_electric.viewmodel.adapters.RequestsTableAdapter;

public class RequestsTableActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    RequestViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requests_table);

        recyclerView = findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Stub stub = new Stub();
        List<Request> requests = stub.requests;

        RequestsTableAdapter requestsTableAdapter = new RequestsTableAdapter();
        requestsTableAdapter.setItems(requests);
        adapter = requestsTableAdapter;
        recyclerView.setAdapter(adapter);
    }
}
