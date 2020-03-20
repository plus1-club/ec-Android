package ru.electric.ec.online.ui.request;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.google.gson.internal.LinkedTreeMap;

import java.util.Objects;

import ru.electric.ec.online.databinding.RequestBinding;
import ru.electric.ec.online.server.ServerData;
import ru.electric.ec.online.server.ServerRouter;
import ru.electric.ec.online.ui.ViewRouter;
import ru.electric.ec.online.ui.bill.BillViewModel;
import ru.electric.ec.online.ui.files.FilesActivity;
import ru.electric.ec.online.ui.search.SearchActivity;

public class RequestViewModel {

    public ObservableField<String> product;
    public ObservableInt count;
    public ObservableInt productColumn;
    public ObservableInt countColumn;
    public ObservableBoolean isFullSearch;
    public ObservableField<String> excel;
    public ObservableBoolean isExcel;
    ObservableInt page;

    public ObservableField<String> title;
    ObservableField<RequestBinding> requestBinding;

    private static RequestViewModel mInstance;    // Ссылка для биндинга с View

    private RequestViewModel() {
        product = new ObservableField<>();
        count = new ObservableInt();
        count.set(1);
        productColumn = new ObservableInt();
        productColumn.set(1);
        countColumn = new ObservableInt();
        countColumn.set(2);
        isFullSearch = new ObservableBoolean();
        excel = new ObservableField<>();
        isExcel = new ObservableBoolean();
        page = new ObservableInt();

        isFullSearch.set(true);
        page.set(1);

        title = new ObservableField<>();
        requestBinding = new ObservableField<>();
    }

    // Получение единственного экземпляра класса
    public static RequestViewModel getInstance() {
        if (mInstance == null) {
            mInstance = new RequestViewModel();
        }
        return mInstance;
    }

    public void onBrowse(Context context) {
        final int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        int permission = ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)context, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        } else {
            Intent intent = new Intent(context, FilesActivity.class);
            intent.putExtra("title", (title.get()));
            context.startActivity(intent);
        }
    }

    public void onNext(Context context){
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra("title", (title.get()));
        context.startActivity(intent);
    }

    public void linkStock(Context context){
        final int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        int permission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        } else {
            Objects.requireNonNull(requestBinding.get()).swiperefresh.setRefreshing(true);
            ServerRouter.stockBalance(context, this);
        }
    }

    public void linkSample(Context context){
        final int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        int permission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        } else {
            Objects.requireNonNull(requestBinding.get()).swiperefresh.setRefreshing(true);
            ServerRouter.example(context, this);
        }
    }

    public void downloadOk(Context context, ServerData body, String fileName) {
        if (ServerRouter.isSuccess(body)) {
            LinkedTreeMap data = (LinkedTreeMap) body.data;
            String link = (String)Objects.requireNonNull(data.get("product"));
            ServerRouter.downloadFile(context, BillViewModel.getInstance(), link, fileName);
        } else {
            ViewRouter.onUnsuccessful(context, body);
        }
    }

    public void downloadError(Context context, Throwable throwable) {
        ViewRouter.onError(context, throwable);
    }
}
