package ru.electric.ec.online.server;

import android.content.Context;

import java.util.List;

import ru.electric.ec.online.App;
import ru.electric.ec.online.models.Request;

public class ServerResponse {

    public static void getEnter(final Context context, String login, String password) {
        ServerNetwork.getApi().enter(login, password).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::getEnter, 0));
    }

    public static void getExit(final Context context) {
        ServerNetwork.getApi().exit(App.model.token).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::getExit, 0));
    }

    public static void byCode(final Context context, String product, int count, boolean fullsearch) {
        ServerNetwork.getApi().byCode(App.model.token, product, count, fullsearch).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::getByCode, 0));
    }

    public static void getBasket(final Context context) {
        ServerNetwork.getApi().getBasket(App.model.token).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::getBasket, 0));
    }

    public static void postBasket(final Context context, List<Request> requests){
        ServerNetwork.getApi().postBasket(App.model.token, requests).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::postBasket, 0));
    }

    public static void putBasket(final Context context, List<Request> requests){
        ServerNetwork.getApi().putBasket(App.model.token, requests).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::putBasket, 0));
    }

    public static void deleteBasket(final Context context){
        ServerNetwork.getApi().deleteBasket(App.model.token).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::deleteBasket, 0));
    }

    public static void order(final Context context, String comment){
        ServerNetwork.getApi().order(App.model.token, comment).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::order, 0));
    }

    public static void unconfirmedList(final Context context) {
        ServerNetwork.getApi().unconfirmedList(App.model.token).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::invoiceList, 0));
    }

    public static void reservedList(final Context context) {
        ServerNetwork.getApi().reservedList(App.model.token).enqueue(
                ServerNetwork.callback(context, ServerRun.getInstance()::invoiceList, 0));
    }

    public static void orderedList(final Context context) {
        ServerNetwork.getApi().orderedList(App.model.token).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::invoiceList, 0));
    }

    public static void canceledList(final Context context) {
        ServerNetwork.getApi().canceledList(App.model.token).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::invoiceList, 0));
    }

    public static void shippedList(final Context context) {
        ServerNetwork.getApi().shippedList(App.model.token).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::invoiceList, 0));
    }

    public static void unconfirmedItem(final Context context, final int number) {
        ServerNetwork.getApi().unconfirmedItem(App.model.token, number).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::invoiceDetails, number));
    }

    public static void reservedItem(final Context context, final int number) {
        ServerNetwork.getApi().reservedItem(App.model.token, number).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::invoiceDetails, number));
    }

    public static void orderedItem(final Context context, final int number) {
        ServerNetwork.getApi().orderedItem(App.model.token, number).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::invoiceDetails, number));
    }

    public static void canceledItem(final Context context, final int number) {
        ServerNetwork.getApi().canceledItem(App.model.token, number).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::invoiceDetails, number));
    }

    public static void shippedItem(final Context context, final int number) {
        ServerNetwork.getApi().shippedItem(App.model.token, number).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::invoiceDetails, number));
    }

    public static void print(final Context context, final int number) {
        ServerNetwork.getApi().print(App.model.token, number).enqueue(
            ServerNetwork.callback(context, ServerRun.getInstance()::getPrint, number));
    }
}
