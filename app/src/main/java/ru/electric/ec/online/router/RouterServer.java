package ru.electric.ec.online.router;

import android.annotation.SuppressLint;
import android.content.Context;

import com.google.gson.internal.LinkedTreeMap;

import java.io.File;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import ru.electric.ec.online.common.App;
import ru.electric.ec.online.models.Request;
import ru.electric.ec.online.server.ServerData;
import ru.electric.ec.online.server.ServerNetwork;
import ru.electric.ec.online.ui.details.DetailsViewModel;
import ru.electric.ec.online.ui.enter.EnterViewModel;
import ru.electric.ec.online.ui.invoice.InvoiceViewModel;
import ru.electric.ec.online.ui.request.RequestViewModel;

@SuppressWarnings("ResultOfMethodCallIgnored")
@SuppressLint("CheckResult")
public class RouterServer {

    public static void setToken(ServerData body){
        App.getModel().token = (String) ((LinkedTreeMap) body.data).get("user_token");
    }

    public static boolean isSuccess(ServerData data){
        if (data != null)
        {
            return data.success && data.error.isEmpty();
        } else {
            return false;
        }
    }

    public static void enterUser(Context context, EnterViewModel viewModel){
        ServerNetwork.getApi()
            .enter(viewModel.login.get(), viewModel.password.get())
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                body -> viewModel.enterOk(context, body),
                error ->viewModel.enterError(context, error));
    }

    public static void getExit(Context context, EnterViewModel viewModel) {
        ServerNetwork.getApi()
            .exit(App.getModel().token)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                body -> viewModel.exitOk(context, body),
                error ->viewModel.exitError(context, error));
    }

    public static void byCode(Context context, RequestViewModel viewModel) {
        ServerNetwork.getApi()
            .byCode(App.getModel().token,
                    viewModel.product.get(),
                    viewModel.count.get(),
                    viewModel.isFullSearch.get())
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                body -> viewModel.searchOk(context, body),
                error ->viewModel.searchError(context, error));
    }

    public static void fromExcel(Context context, RequestViewModel viewModel) {
        if (viewModel.excel.get() != null){
            File file = new File(Objects.requireNonNull(viewModel.excel.get()));
            RequestBody requestFile = RequestBody.create(file, MediaType.parse("multipart/form-data"));
            MultipartBody.Part excelPart = MultipartBody.Part.createFormData("excel",
                    file.getName(), requestFile);

            ServerNetwork.getApi()
                .fromExcel(App.getModel().token,
                        excelPart,
                        viewModel.productColumn.get(),
                        viewModel.countColumn.get(),
                        viewModel.isFullSearch.get())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        body -> viewModel.searchOk(context, body),
                        error ->viewModel.searchError(context, error));
        }
    }

    public static void getBasket(Context context, RequestViewModel viewModel) {
        ServerNetwork.getApi()
            .getBasket(App.getModel().token)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                body -> viewModel.basketOk(context, body),
                error ->viewModel.basketError(context, error));
    }

    public static void postBasket(Context context, RequestViewModel viewModel, List<Request> requests){
        ServerNetwork.getApi()
            .postBasket(App.getModel().token, requests)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                body -> viewModel.postBasketOk(context, body),
                error ->viewModel.basketError(context, error));
    }

    public static void putBasket(Context context, RequestViewModel viewModel){
        List<Request> requests = viewModel.basket;
        ServerNetwork.getApi()
            .putBasket(App.getModel().token, requests)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                body -> viewModel.updateBasketOk(context, body),
                error ->viewModel.basketError(context, error));
    }

    public static void deleteBasket(Context context, RequestViewModel viewModel){
        ServerNetwork.getApi()
            .deleteBasket(App.getModel().token)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                body -> viewModel.updateBasketOk(context, body),
                error ->viewModel.basketError(context, error));
    }

    public static void order(Context context, RequestViewModel viewModel, String comment){
        ServerNetwork.getApi()
            .order(App.getModel().token, comment)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                body -> viewModel.orderOk(context, body),
                error ->viewModel.basketError(context, error));
    }

    public static void unconfirmedList(Context context, InvoiceViewModel viewModel) {
        ServerNetwork.getApi()
            .unconfirmedList(App.getModel().token)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                body -> viewModel.invoiceOk(context, body),
                error ->viewModel.invoiceError(context, error));
    }

    public static void reservedList(Context context, InvoiceViewModel viewModel) {
        ServerNetwork.getApi()
            .reservedList(App.getModel().token)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                body -> viewModel.invoiceOk(context, body),
                error ->viewModel.invoiceError(context, error));
    }

    public static void orderedList(Context context, InvoiceViewModel viewModel) {
        ServerNetwork.getApi()
            .orderedList(App.getModel().token)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                body -> viewModel.invoiceOk(context, body),
                error ->viewModel.invoiceError(context, error));
    }

    public static void canceledList(Context context, InvoiceViewModel viewModel) {
        ServerNetwork.getApi()
            .canceledList(App.getModel().token)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                body -> viewModel.invoiceOk(context, body),
                error ->viewModel.invoiceError(context, error));
    }

    public static void shippedList(Context context, InvoiceViewModel viewModel) {
        ServerNetwork.getApi()
            .shippedList(App.getModel().token)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                body -> viewModel.invoiceOk(context, body),
                error ->viewModel.invoiceError(context, error));
    }

    public static void unconfirmedItem(Context context, DetailsViewModel viewModel, final int number) {
        ServerNetwork.getApi()
            .unconfirmedItem(App.getModel().token, number)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                body -> viewModel.detailOk(context, body, number),
                error ->viewModel.detailError(context, error));
    }

    public static void reservedItem(Context context, DetailsViewModel viewModel, final int number) {
        ServerNetwork.getApi()
            .reservedItem(App.getModel().token, number)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                body -> viewModel.detailOk(context, body, number),
                error ->viewModel.detailError(context, error));
    }

    public static void orderedItem(Context context, DetailsViewModel viewModel, final int number) {
        DetailsViewModel.getInstance().number.set(number);
        ServerNetwork.getApi()
            .orderedItem(App.getModel().token, number)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                body -> viewModel.detailOk(context, body, number),
                error ->viewModel.detailError(context, error));
    }

    public static void canceledItem(Context context, DetailsViewModel viewModel, final int number) {
        ServerNetwork.getApi()
            .canceledItem(App.getModel().token, number)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                body -> viewModel.detailOk(context, body, number),
                error ->viewModel.detailError(context, error));
    }

    public static void shippedItem(Context context, DetailsViewModel viewModel, final int number) {
        ServerNetwork.getApi()
            .shippedItem(App.getModel().token, number)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                body -> viewModel.detailOk(context, body, number),
                error ->viewModel.detailError(context, error));
    }

    public static void print(Context context, DetailsViewModel viewModel, final int number) {
        ServerNetwork.getApi()
            .print(App.getModel().token, number)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                body -> viewModel.printOk(context, body, number),
                error ->viewModel.detailError(context, error));
   }

}
