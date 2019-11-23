package club.plus1.ec_online.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import club.plus1.ec_online.R;
import club.plus1.ec_online.databinding.MenuBinding;
import club.plus1.ec_online.viewmodels.MenuViewModel;

public class MenuFragment extends Fragment {

    private MenuViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        viewModel = MenuViewModel.getInstance();
        MenuBinding binding = DataBindingUtil.inflate(inflater, R.layout.menu, null, false);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }
}
