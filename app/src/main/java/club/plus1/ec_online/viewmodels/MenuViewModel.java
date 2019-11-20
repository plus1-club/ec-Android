package club.plus1.ec_online.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import club.plus1.ec_online.R;
import club.plus1.ec_online.Server;
import club.plus1.ec_online.views.InvoiceTableActivity;
import club.plus1.ec_online.views.RequestActivity;

public class MenuViewModel {

    public MenuViewModel(Context context){}

    public void onCheckExistence(Context context){
        Intent intent = new Intent(context, RequestActivity.class);
        intent.putExtra("title", context.getString(R.string.title_request));
        context.startActivity(intent);
    }

    public void onMakeOrder(Context context) {
        Intent intent = new Intent(context, RequestActivity.class);
        intent.putExtra("title", context.getString(R.string.title_order));
        context.startActivity(intent);
    }

    public void onCart(final Context context) {
        Server.getBasket(context);
    }

    public void onUnconfirmed(Context context){
        Intent intent = new Intent(context, InvoiceTableActivity.class);
        intent.putExtra("title", context.getString(R.string.title_list_unconfirmed));
        context.startActivity(intent);
    }

    public void onReserves(Context context){
        Intent intent = new Intent(context, InvoiceTableActivity.class);
        intent.putExtra("title", context.getString(R.string.title_list_reserves));
        context.startActivity(intent);
    }

    public void onOrders(Context context){
        Intent intent = new Intent(context, InvoiceTableActivity.class);
        intent.putExtra("title", context.getString(R.string.title_list_orders));
        context.startActivity(intent);
    }

    public void onCanceled(Context context){
        Intent intent = new Intent(context, InvoiceTableActivity.class);
        intent.putExtra("title", context.getString(R.string.title_list_canceled));
        context.startActivity(intent);
    }

    public void onHistory(Context context){
        Intent intent = new Intent(context, InvoiceTableActivity.class);
        intent.putExtra("title", context.getString(R.string.title_list_history));
        context.startActivity(intent);
    }

    public void onExit(final Context context) {
        Server.getExit(context);
    }

    public boolean onOptionsItemSelected(Context context, MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_product_existence:
                onCheckExistence(context);
                return true;
            case R.id.action_make_order:
                onMakeOrder(context);
                return true;
            case R.id.action_cart:
                onCart(context);
                return true;
            case R.id.action_unconfirmed:
                onUnconfirmed(context);
                return true;
            case R.id.action_reserves:
                onReserves(context);
                return true;
            case R.id.action_orders:
                onOrders(context);
                return true;
            case R.id.action_canceled:
                onCanceled(context);
                return true;
            case R.id.action_history:
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
