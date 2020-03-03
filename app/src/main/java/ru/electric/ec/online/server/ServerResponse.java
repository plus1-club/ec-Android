package ru.electric.ec.online.server;

import android.content.Context;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import ru.electric.ec.online.common.App;
import ru.electric.ec.online.models.Request;

public class ServerResponse {

    public static void getExit(final Context context) {
        ServerNetwork.getApi().exit(App.getModel().token).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::getExit, 0));
    }

    public static void byCode(final Context context, String product, int count, boolean fullsearch) {
        ServerNetwork.getApi().byCode(App.getModel().token, product, count, fullsearch).enqueue(
                ServerNetwork.callback(context, ServerRun.getInstance()::getByCode, 0));
    }

    public static void fromExcel(final Context context, String excelPath, int productColumn, int countColumn, boolean fullsearch) {
        File file =  new File(excelPath);
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("multipart/form-data"));
        MultipartBody.Part excel = MultipartBody.Part.createFormData("excel", file.getName(), requestFile);

        ServerNetwork.getApi().fromExcel(App.getModel().token, excel, productColumn, countColumn, fullsearch).enqueue(
                ServerNetwork.callback(context, ServerRun.getInstance()::getFromExcel, 0));
    }

    public static void getBasket(final Context context) {
        ServerNetwork.getApi().getBasket(App.getModel().token).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::getBasket, 0));
    }

    public static void postBasket(final Context context, List<Request> requests){
        ServerNetwork.getApi().postBasket(App.getModel().token, requests).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::postBasket, 0));
    }

    public static void putBasket(final Context context, List<Request> requests){
        ServerNetwork.getApi().putBasket(App.getModel().token, requests).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::putBasket, 0));
    }

    public static void deleteBasket(final Context context){
        ServerNetwork.getApi().deleteBasket(App.getModel().token).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::deleteBasket, 0));
    }

    public static void order(final Context context, String comment){
        ServerNetwork.getApi().order(App.getModel().token, comment).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::order, 0));
    }

    public static void unconfirmedList(final Context context) {
        ServerNetwork.getApi().unconfirmedList(App.getModel().token).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::invoiceList, 0));
    }

    public static void reservedList(final Context context) {
        ServerNetwork.getApi().reservedList(App.getModel().token).enqueue(
                ServerNetwork.callback(context, ServerRun.getInstance()::invoiceList, 0));
    }

    public static void orderedList(final Context context) {
        ServerNetwork.getApi().orderedList(App.getModel().token).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::invoiceList, 0));
    }

    public static void canceledList(final Context context) {
        ServerNetwork.getApi().canceledList(App.getModel().token).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::invoiceList, 0));
    }

    public static void shippedList(final Context context) {
        ServerNetwork.getApi().shippedList(App.getModel().token).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::invoiceList, 0));
    }

    public static void unconfirmedItem(final Context context, final int number) {
        ServerNetwork.getApi().unconfirmedItem(App.getModel().token, number).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::invoiceDetails, number));
    }

    public static void reservedItem(final Context context, final int number) {
        ServerNetwork.getApi().reservedItem(App.getModel().token, number).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::invoiceDetails, number));
    }

    public static void orderedItem(final Context context, final int number) {
        ServerNetwork.getApi().orderedItem(App.getModel().token, number).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::invoiceDetails, number));
    }

    public static void canceledItem(final Context context, final int number) {
        ServerNetwork.getApi().canceledItem(App.getModel().token, number).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::invoiceDetails, number));
    }

    public static void shippedItem(final Context context, final int number) {
        ServerNetwork.getApi().shippedItem(App.getModel().token, number).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::invoiceDetails, number));
    }

    public static void print(final Context context, final int number) {
        ServerNetwork.getApi().print(App.getModel().token, number).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::getPrint, number));
    }

}
