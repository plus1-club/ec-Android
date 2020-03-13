package ru.electric.ec.online.ui.bill;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

import okhttp3.ResponseBody;
import ru.electric.ec.online.router.RouterView;
import ru.electric.ec.online.ui.request.RequestActivity;

public class BillViewModel {

    private static BillViewModel mInstance;    // Ссылка для биндинга с View
    public ObservableField<String> title;
    ObservableField<String> link;
    private ObservableField<String> local;
    public ObservableInt number;

    private BillViewModel() {
        title = new ObservableField<>();
        link = new ObservableField<>();
        local = new ObservableField<>();
        number = new ObservableInt();
    }

    // Получение единственного экземпляра класса
    public static BillViewModel getInstance() {
        if (mInstance == null) {
            mInstance = new BillViewModel();
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

    public void downloadOk(Context context, ResponseBody body, String fileName) {
        try{
            DownloadTask load = new DownloadTask(context);
            load.execute(body, fileName);
        } catch (Exception e){
            RouterView.onError(context, e);
        }
    }

    public void downloadError(Context context, Throwable throwable) {
        RouterView.onError(context, throwable);
    }

    private static class DownloadTask extends AsyncTask {

        @SuppressLint("StaticFieldLeak")
        private Context context;

        DownloadTask(Context context){
            this.context = context;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            ResponseBody body = (ResponseBody) objects[0];
            String fileName = (String) objects[1];
            try {
                File localPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File localFile = new File(localPath, fileName);
                Files.asByteSink(localFile).write(body.bytes());
                return localFile;
            } catch (IOException e) {
                RouterView.onError(context, e);
                return null;
            }
        }

        protected void onPostExecute(Object result) {
            Intent intent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
            context.startActivity(intent);
            ((RequestActivity) context).binding.swiperefresh.setRefreshing(false);
        }
    }
}
