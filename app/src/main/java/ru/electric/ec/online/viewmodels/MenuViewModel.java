package ru.electric.ec.online.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import ru.electric.ec.online.R;
import ru.electric.ec.online.models.ServerResponse;
import ru.electric.ec.online.views.InvoiceTableActivity;
import ru.electric.ec.online.views.RequestActivity;
import ru.electric.ec.online.views.RequestsBasketActivity;

public class MenuViewModel {

    private static MenuViewModel mInstance;

    public MenuViewModel(Context context){}

    // Получение единственного экземпляра класса
    public static MenuViewModel getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MenuViewModel(context);
        }
        return mInstance;
    }

    public void onCheckExistence(Context context){
        Intent intent = new Intent(context, RequestActivity.class);
        intent.putExtra("title", context.getString(R.string.text_request));
        context.startActivity(intent);
    }

    public void onMakeOrder(Context context) {
        Intent intent = new Intent(context, RequestActivity.class);
        intent.putExtra("title", context.getString(R.string.text_order));
        context.startActivity(intent);
    }

    public void onCart(final Context context) {
        Intent intent = new Intent(context, RequestsBasketActivity.class);
        intent.putExtra("title", context.getString(R.string.text_basket));
        context.startActivity(intent);
    }

    public void onUnconfirmed(Context context){
        Intent intent = new Intent(context, InvoiceTableActivity.class);
        intent.putExtra("title", context.getString(R.string.text_list_unconfirmed));
        context.startActivity(intent);
    }

    public void onReserves(Context context){
        Intent intent = new Intent(context, InvoiceTableActivity.class);
        intent.putExtra("title", context.getString(R.string.text_list_reserved));
        context.startActivity(intent);
    }

    public void onOrders(Context context){
        Intent intent = new Intent(context, InvoiceTableActivity.class);
        intent.putExtra("title", context.getString(R.string.text_list_ordered));
        context.startActivity(intent);
    }

    public void onCanceled(Context context){
        Intent intent = new Intent(context, InvoiceTableActivity.class);
        intent.putExtra("title", context.getString(R.string.text_list_canceled));
        context.startActivity(intent);
    }

    public void onHistory(Context context){
        Intent intent = new Intent(context, InvoiceTableActivity.class);
        intent.putExtra("title", context.getString(R.string.text_list_shipped));
        context.startActivity(intent);
    }

    public void onExit(final Context context) {
        ServerResponse.getExit(context);
    }

    boolean onOptionsItemSelected(Context context, MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_request:
                onCheckExistence(context);
                return true;
            case R.id.action_order:
                onMakeOrder(context);
                return true;
            case R.id.action_basket:
                onCart(context);
                return true;
            case R.id.action_unconfirmed:
                onUnconfirmed(context);
                return true;
            case R.id.action_reserved:
                onReserves(context);
                return true;
            case R.id.action_ordered:
                onOrders(context);
                return true;
            case R.id.action_canceled:
                onCanceled(context);
                return true;
            case R.id.action_shipped:
                onHistory(context);
                return true;
            case R.id.action_exit:
                onExit(context);
                return true;
            default:
                return false;
        }
    }
}