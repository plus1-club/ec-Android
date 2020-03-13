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
import com.google.gson.internal.LinkedTreeMap;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

import okhttp3.ResponseBody;
import ru.electric.ec.online.router.RouterServer;
import ru.electric.ec.online.router.RouterView;
import ru.electric.ec.online.server.ServerData;
import ru.electric.ec.online.ui.details.DetailsActivity;
import ru.electric.ec.online.ui.invoice.InvoiceActivity;
import ru.electric.ec.online.ui.request.RequestActivity;

public class BillViewModel {

    private static BillViewModel mInstance;    // Ссылка для биндинга с View
    public ObservableField<String> title;
    ObservableField<File> local;
    public ObservableInt number;

    private BillViewModel() {
        title = new ObservableField<>();
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
            //shareMessage = shareMessage + link.get();
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            context.startActivity(Intent.createChooser(shareIntent, title.get()));
        } catch(Exception e) {
            //e.toString();
        }
    }

    public void printOk(Context context, ServerData body, int number) {
        if (RouterServer.isSuccess(body)) {
            LinkedTreeMap data = (LinkedTreeMap) body.data;
            String link = (String) Objects.requireNonNull(data.get("file"));
            RouterServer.downloadFile(context, BillViewModel.getInstance(), link,
                    String.format(Locale.getDefault(),"%1$d.pdf", number));
        } else {
            RouterView.onUnsuccessful(context, body);
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

    @SuppressLint("StaticFieldLeak")
    private class DownloadTask extends AsyncTask {

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
                local.set(localFile);
                return localFile;
            } catch (IOException e) {
                RouterView.onError(context, e);
                return null;
            }
        }

        protected void onPostExecute(Object result) {
            if(context instanceof RequestActivity){
                Intent intent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
                context.startActivity(intent);
                ((RequestActivity) context).binding.swiperefresh.setRefreshing(false);
            } else if (context instanceof InvoiceActivity){
                Intent intent = new Intent(context, BillActivity.class);
                intent.putExtra("title", title.get());
                intent.putExtra("number", number.get());
                context.startActivity(intent);
                ((InvoiceActivity) context).binding.swiperefresh.setRefreshing(false);
            } else if (context instanceof DetailsActivity){
                Intent intent = new Intent(context, BillActivity.class);
                intent.putExtra("title", title.get());
                intent.putExtra("number", number.get());
                context.startActivity(intent);
                ((DetailsActivity) context).binding.swiperefresh.setRefreshing(false);
            }
        }
    }
}
