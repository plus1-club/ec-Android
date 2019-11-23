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
import club.plus1.ec_online.databinding.InvoiceDetailsBinding;
import club.plus1.ec_online.viewadapters.InvoiceDetailsAdapter;
import club.plus1.ec_online.viewmodels.InvoiceDetailsViewModel;
import club.plus1.ec_online.viewmodels.InvoiceTableViewModel;

public class InvoiceDetailsFragment extends Fragment {

    private InvoiceDetailsViewModel viewModel;
    private InvoiceTableViewModel parent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Bundle bundle = Objects.requireNonNull(getActivity()).getIntent().getExtras();

        parent = InvoiceTableViewModel.getInstance();
        viewModel = InvoiceDetailsViewModel.getInstance();
        viewModel.number.set(Objects.requireNonNull(bundle).getInt("number"));
        viewModel.date.set(bundle.getString("date"));
        viewModel.sum.set(bundle.getDouble("sum"));
        viewModel.status.set(bundle.getString("status"));
        viewModel.waybill.set(bundle.getInt("waybill"));

        InvoiceDetailsAdapter invoiceDetailsAdapter = new InvoiceDetailsAdapter();
        invoiceDetailsAdapter.setItems(parent.invoices.get(bundle.getInt("position")).details);

        InvoiceDetailsBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.invoice_details, null, false);
        binding.setViewModel(viewModel);
        binding.list.setHasFixedSize(true);
        binding.list.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        binding.list.setAdapter(invoiceDetailsAdapter);
        return binding.getRoot();
    }
}
