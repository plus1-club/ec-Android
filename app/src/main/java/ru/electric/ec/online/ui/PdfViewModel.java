package ru.electric.ec.online.ui;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

public class PdfViewModel {

    private static PdfViewModel mInstance;    // Ссылка для биндинга с View
    public ObservableField<String> title;
    ObservableField<String> link;
    private ObservableField<String> local;
    public ObservableInt number;


    private PdfViewModel() {
        title = new ObservableField<>();
        link = new ObservableField<>();
        local = new ObservableField<>();
        number = new ObservableInt();
    }

    // Получение единственного экземпляра класса
    public static PdfViewModel getInstance() {
        if (mInstance == null) {
            mInstance = new PdfViewModel();
        }
        return mInstance;
    }

    public void onShare(Context context){
        //InfoViewModel.log(context, false, true,
        //    Service.getStr(R.string.test_in_develop_download_invoice));
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareMessage= title.get() + "\n";
            shareMessage = shareMessage + link.get();
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            context.startActivity(Intent.createChooser(shareIntent, title.get()));
        } catch(Exception e) {
            //e.toString();
        }
    }
}
