package club.plus1.ec_online.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import club.plus1.ec_online.R;
import club.plus1.ec_online.databinding.RequestBinding;
import club.plus1.ec_online.viewadapters.RequestFragmentAdapter;
import club.plus1.ec_online.viewmodels.RequestViewModel;

public class RequestFragment extends Fragment {

    private RequestViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        viewModel = RequestViewModel.getInstance();
        RequestBinding binding = DataBindingUtil.inflate(inflater, R.layout.request, null, false);
        binding.setViewModel(viewModel);
        if (viewModel.fragmentManager == null){
            viewModel.fragmentManager = getFragmentManager();
        }
        binding.viewpager.setAdapter(new RequestFragmentAdapter(viewModel.fragmentManager));
        binding.tabs.setupWithViewPager(binding.viewpager);
        return binding.getRoot();
    }
}
