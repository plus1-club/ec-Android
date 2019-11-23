package club.plus1.ec_online.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import club.plus1.ec_online.App;
import club.plus1.ec_online.R;
import club.plus1.ec_online.databinding.RequestsBasketBinding;
import club.plus1.ec_online.viewadapters.RequestsBasketAdapter;
import club.plus1.ec_online.viewmodels.RequestViewModel;

public class RequestsBasketFragment extends Fragment {

    private RequestViewModel viewModel;
    Bundle arguments;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        viewModel = RequestViewModel.getInstance();
        viewModel.total.set(0);

        RequestsBasketAdapter requestsBasketAdapter = new RequestsBasketAdapter();
        requestsBasketAdapter.setItems(App.model.basket);
        viewModel.adapter.set(requestsBasketAdapter);

        RequestsBasketBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.requests_basket, null, false);
        binding.setViewModel(viewModel);
        binding.list.setHasFixedSize(true);
        binding.list.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        binding.list.setAdapter(requestsBasketAdapter);
        return binding.getRoot();
    }
}
