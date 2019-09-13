package club.plus1.ec_electric.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import club.plus1.ec_electric.R;

public class RequestPageFragment extends Fragment {
    private static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), RequestTableActivity.class);
            startActivity(intent);
        }
    };


    public static RequestPageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        RequestPageFragment fragment = new RequestPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE);
        }
    }

    @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        View view;
        if (mPage == 1) {
            view = inflater.inflate(R.layout.request_by_code, container, false);
        } else {
            view = inflater.inflate(R.layout.request_from_excel, container, false);
        }

        Button button = view.findViewById(R.id.buttonNext);
        button.setOnClickListener(clickListener);

        return view;
    }
}