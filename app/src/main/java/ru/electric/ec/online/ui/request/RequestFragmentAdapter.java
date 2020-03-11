package ru.electric.ec.online.ui.request;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class RequestFragmentAdapter extends FragmentPagerAdapter {

    private final int PAGE_COUNT = 2;
    private String[] tabTitles = new String[]{"Проверка по коду", "Загрузить Excel"};

    RequestFragmentAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return RequestFragment.newInstance(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
