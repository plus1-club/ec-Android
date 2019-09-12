package club.plus1.ec_electric.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import club.plus1.ec_electric.view.RequestPageFragment;

public class RequestFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGE_COUNT = 2;
    private String[] tabTitles = new String[]{"Проверка по коду", "Загрузить Excel"};
    private Context context;

    public RequestFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }

    @Override public int getCount() {
        return PAGE_COUNT;
    }

    @NonNull
    @Override public Fragment getItem(int position) {
        return RequestPageFragment.newInstance(position + 1);
    }

    @Override public CharSequence getPageTitle(int position) {
        // генерируем заголовок в зависимости от позиции
        return tabTitles[position];
    }
}
