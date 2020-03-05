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
import ru.electric.ec.online.ui.basket.BasketActivity;
import ru.electric.ec.online.ui.details.DetailsActivity;
import ru.electric.ec.online.ui.details.DetailsViewModel;
import ru.electric.ec.online.ui.enter.EnterActivity;
import ru.electric.ec.online.ui.enter.EnterViewModel;
import ru.electric.ec.online.ui.invoice.InvoiceActivity;
import ru.electric.ec.online.ui.request.RequestViewModel;
import ru.electric.ec.online.ui.search.SearchActivity;

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

    public static void enterUser(EnterActivity context, EnterViewModel viewModel){
        ServerNetwork.getApi()
            .enter(viewModel.login.get(), viewModel.password.get())
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(context::enterOk, context::enterError);
    }

    public static void getExit(Context context) {
        ServerNetwork.getApi()
            .exit(App.getModel().token)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(RouterView::exit, RouterView::exit);
    }

    public static void byCode(SearchActivity context, RequestViewModel viewModel) {
        ServerNetwork.getApi()
            .byCode(App.getModel().token,
                    viewModel.product.get(),
                    viewModel.count.get(),
                    viewModel.isFullSearch.get())
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(context::searchOk, context::searchError);
    }

    public static void fromExcel(SearchActivity context, RequestViewModel viewModel) {
        if (viewModel.excel.get() != null){
            File fileExcel =  new File(Objects.requireNonNull(viewModel.excel.get()));
            RequestBody requestFile = RequestBody.create(fileExcel, MediaType.parse("multipart/form-data"));
            MultipartBody.Part excelPart = MultipartBody.Part.createFormData("excel",
                    fileExcel.getName(), requestFile);

            ServerNetwork.getApi()
                    .fromExcel(App.getModel().token,
                            excelPart,
                            viewModel.productColumn.get(),
                            viewModel.countColumn.get(),
                            viewModel.isFullSearch.get())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(context::searchOk, context::searchError);
        }
    }

    public static void getBasket(BasketActivity context) {
        ServerNetwork.getApi()
            .getBasket(App.getModel().token)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(context::getBasketOk, context::basketError);
    }

    public static void postBasket(BasketActivity context, List<Request> requests){
        ServerNetwork.getApi()
            .postBasket(App.getModel().token, requests)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(context::updateBasketOk, context::basketError);
    }

    public static void putBasket(BasketActivity context, List<Request> requests){
        ServerNetwork.getApi()
            .putBasket(App.getModel().token, requests)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(context::updateBasketOk, context::basketError);
    }

    public static void deleteBasket(BasketActivity context){
        ServerNetwork.getApi()
            .deleteBasket(App.getModel().token)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(context::updateBasketOk, context::basketError);
    }

    public static void order(BasketActivity context, String comment){
        ServerNetwork.getApi()
            .order(App.getModel().token, comment)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(context::orderOk, context::basketError);
    }

    public static void unconfirmedList(InvoiceActivity context) {
        ServerNetwork.getApi()
            .unconfirmedList(App.getModel().token)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(context::invoiceOk, context::invoiceError);
    }

    public static void reservedList(InvoiceActivity context) {
        ServerNetwork.getApi()
            .reservedList(App.getModel().token)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(context::invoiceOk, context::invoiceError);
    }

    public static void orderedList(InvoiceActivity context) {
        ServerNetwork.getApi()
            .orderedList(App.getModel().token)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(context::invoiceOk, context::invoiceError);
    }

    public static void canceledList(InvoiceActivity context) {
        ServerNetwork.getApi()
            .canceledList(App.getModel().token)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(context::invoiceOk, context::invoiceError);
    }

    public static void shippedList(InvoiceActivity context) {
        ServerNetwork.getApi()
            .shippedList(App.getModel().token)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(context::invoiceOk, context::invoiceError);
    }

    public static void unconfirmedItem(DetailsActivity context, final int number) {
        ServerNetwork.getApi()
            .unconfirmedItem(App.getModel().token, number)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(context::detailOk, context::detailError);
    }

    public static void reservedItem(DetailsActivity context, final int number) {
        ServerNetwork.getApi()
            .reservedItem(App.getModel().token, number)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(context::detailOk, context::detailError);
    }

    public static void orderedItem(DetailsActivity context, final int number) {
        DetailsViewModel.getInstance().number.set(number);
        ServerNetwork.getApi()
            .orderedItem(App.getModel().token, number)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(context::detailOk, context::detailError);
    }

    public static void canceledItem(DetailsActivity context, final int number) {
        ServerNetwork.getApi()
            .canceledItem(App.getModel().token, number)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(context::detailOk, context::detailError);
    }

    public static void shippedItem(DetailsActivity context, final int number) {
        ServerNetwork.getApi()
            .shippedItem(App.getModel().token, number)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(context::detailOk, context::detailError);
    }

    public static void print(DetailsActivity context, final int number) {
        ServerNetwork.getApi()
            .print(App.getModel().token, number)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(context::printOk, context::detailError);
   }

}
