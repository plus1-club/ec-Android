package club.plus1.ec_online.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import club.plus1.ec_online.R;
import club.plus1.ec_online.databinding.InvoiceTableBinding;
import club.plus1.ec_online.models.StorageStub;
import club.plus1.ec_online.viewadapters.InvoiceTableAdapter;
import club.plus1.ec_online.viewmodels.InvoiceTableViewModel;

public class InvoiceTableFragment extends Fragment {

    private InvoiceTableViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        viewModel = InvoiceTableViewModel.getInstance();

        StorageStub storageStub = StorageStub.getInstance();
        InvoiceTableAdapter invoiceTableAdapter = new InvoiceTableAdapter();

        String title = viewModel.title.get();
        if (getString(R.string.text_list_unconfirmed).equals(title)) {
            invoiceTableAdapter.setItems(storageStub.invoicesUnconf);
        } else if (getString(R.string.text_list_reserved).equals(title)) {
            invoiceTableAdapter.setItems(storageStub.invoicesReserv);
        } else if (getString(R.string.text_list_ordered).equals(title)) {
            invoiceTableAdapter.setItems(storageStub.invoicesOrder);
        } else if (getString(R.string.text_list_canceled).equals(title)) {
            invoiceTableAdapter.setItems(storageStub.invoicesCancel);
        } else if (getString(R.string.text_list_shipped).equals(title)) {
            invoiceTableAdapter.setItems(storageStub.invoicesShip);
        } else {
            invoiceTableAdapter.setItems(storageStub.invoicesEmpty);
        }

        InvoiceTableBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.invoice_table, null, false);
        binding.setViewModel(viewModel);
        binding.list.setHasFixedSize(true);
        binding.list.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        binding.list.setAdapter(invoiceTableAdapter);
        return binding.getRoot();
    }
}
