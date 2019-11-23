package club.plus1.ec_online.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Objects;

import club.plus1.ec_online.R;
import club.plus1.ec_online.databinding.RequestsTableBinding;
import club.plus1.ec_online.models.StorageStub;
import club.plus1.ec_online.viewadapters.RequestsTableAdapter;
import club.plus1.ec_online.viewmodels.RequestViewModel;

public class RequestsTableFragment extends Fragment {

    private RequestViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        viewModel = RequestViewModel.getInstance();

        StorageStub storageStub = StorageStub.getInstance();
        RequestsTableAdapter requestsTableAdapter = new RequestsTableAdapter();
        requestsTableAdapter.setItems(storageStub.getRequests(
                Objects.requireNonNull(viewModel.product.get()), viewModel.count.get()));

        RequestsTableBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.requests_table, null, false);
        binding.setViewModel(viewModel);
        binding.list.setHasFixedSize(true);
        binding.list.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        binding.list.setAdapter(requestsTableAdapter);
        return binding.getRoot();
    }
}
