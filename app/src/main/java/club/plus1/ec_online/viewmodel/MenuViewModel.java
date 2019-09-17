package club.plus1.ec_online.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import club.plus1.ec_online.R;
import club.plus1.ec_online.view.EnterActivity;
import club.plus1.ec_online.view.InvoiceTableActivity;
import club.plus1.ec_online.view.RequestsBasketActivity;
import club.plus1.ec_online.view.RequestActivity;

public class MenuViewModel {

    public MenuViewModel(Context context){}

    public void onCheckExistence(Context context){
        Intent intent = new Intent(context, RequestActivity.class);
        context.startActivity(intent);
    }

    public void onCart(Context context){
        Intent intent = new Intent(context, RequestsBasketActivity.class);
        context.startActivity(intent);
    }

    public void onMakeOrder(Context context){
        Intent intent = new Intent(context, RequestActivity.class);
        context.startActivity(intent);
    }

    public void onUnconfirmed(Context context){
        Intent intent = new Intent(context, InvoiceTableActivity.class);
        context.startActivity(intent);
    }

    public void onReserves(Context context){
        Intent intent = new Intent(context, InvoiceTableActivity.class);
        context.startActivity(intent);
    }

    public void onOrders(Context context){
        Intent intent = new Intent(context, InvoiceTableActivity.class);
        context.startActivity(intent);
    }

    public void onCanceled(Context context){
        Intent intent = new Intent(context, InvoiceTableActivity.class);
        context.startActivity(intent);
    }

    public void onHistory(Context context){
        Intent intent = new Intent(context, InvoiceTableActivity.class);
        context.startActivity(intent);
    }

    public void onExit(Context context){
        Intent intent = new Intent(context, EnterActivity.class);
        context.startActivity(intent);
    }

    public boolean onOptionsItemSelected(Context context, MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_product_existence:
                onCheckExistence(context);
                return true;
            case R.id.action_cart:
                onCart(context);
                return true;
            case R.id.action_reserves:
                onReserves(context);
                return true;
            case R.id.action_canceled:
                onCanceled(context);
                return true;
            case R.id.action_make_order:
                onMakeOrder(context);
                return true;
            case R.id.action_unconfirmed:
                onUnconfirmed(context);
                return true;
            case R.id.action_orders:
                onOrders(context);
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
