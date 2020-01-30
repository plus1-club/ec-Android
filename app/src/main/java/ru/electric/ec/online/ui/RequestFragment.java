package ru.electric.ec.online.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import ru.electric.ec.online.R;
import ru.electric.ec.online.databinding.RequestByCodeBinding;
import ru.electric.ec.online.databinding.RequestFromExcelBinding;

public class RequestFragment extends Fragment {

    private static final String ARG_PAGE = "ARG_PAGE";
    private int page;
    private RequestViewModel viewModel;

    static RequestFragment newInstance(int page) {
        RequestFragment fragment = new RequestFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            page = getArguments().getInt(ARG_PAGE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        viewModel = RequestViewModel.getInstance();
        if (page == 1) {
            RequestByCodeBinding binding = DataBindingUtil.inflate(inflater, R.layout.request_by_code, null, false);
            binding.setViewModel(viewModel);
            return binding.getRoot();
        } else {
            RequestFromExcelBinding binding = DataBindingUtil.inflate(inflater, R.layout.request_from_excel, null, false);
            binding.setViewModel(viewModel);
            return binding.getRoot();
        }
    }
}