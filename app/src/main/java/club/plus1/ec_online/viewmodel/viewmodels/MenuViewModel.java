package club.plus1.ec_online.viewmodel.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import club.plus1.ec_online.R;
import club.plus1.ec_online.model.web.Network;
import club.plus1.ec_online.model.web.Server;
import club.plus1.ec_online.view.App;
import club.plus1.ec_online.view.activities.EnterActivity;
import club.plus1.ec_online.view.activities.InvoiceTableActivity;
import club.plus1.ec_online.view.activities.RequestActivity;
import club.plus1.ec_online.view.activities.RequestsBasketActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public void onCart(Context context){
        Intent intent = new Intent(context, RequestsBasketActivity.class);
        intent.putExtra("title", context.getString(R.string.title_basket));
        context.startActivity(intent);
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

        Network.getInstance();
        Network.getApi().exit(App.token).enqueue(new Callback<Server>() {
            @Override
            public void onResponse(@NonNull Call<Server> call, @NonNull Response<Server> response) {
                if (!response.isSuccessful()) {
                    App.log(context, false, "response code " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Server> call, @NonNull Throwable t) {
                App.log(context, true, "failure " + t);
            }
        });

        Intent intent = new Intent(context, EnterActivity.class);
        context.startActivity(intent);
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
