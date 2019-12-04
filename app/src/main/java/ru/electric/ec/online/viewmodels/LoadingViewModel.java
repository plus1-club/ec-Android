package ru.electric.ec.online.viewmodels;

import android.content.Context;

import androidx.databinding.ObservableField;

public class LoadingViewModel {

    private static LoadingViewModel mInstance;
    private ObservableField<String> activityName;
    public ObservableField<String> title;

    private LoadingViewModel(Context context){
        activityName = new ObservableField<>();
        title = new ObservableField<>();
    }

    // Получение единственного экземпляра класса
    public static LoadingViewModel getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new LoadingViewModel(context);
        }
        return mInstance;
    }
}
