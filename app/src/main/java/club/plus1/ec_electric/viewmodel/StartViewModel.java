package club.plus1.ec_electric.viewmodel;

import android.content.Context;
import android.content.Intent;

import club.plus1.ec_electric.view.InvoiceTableActivity;
import club.plus1.ec_electric.view.RequestByCodeActivity;
import club.plus1.ec_electric.view.RequestTableActivity;

public class StartViewModel {

    public StartViewModel(Context context){}

    public void onCheckExistence(Context context){
        Intent intent = new Intent(context, RequestByCodeActivity.class);
        context.startActivity(intent);
    }

    public void onMakeOrder(Context context){
        Intent intent = new Intent(context, RequestByCodeActivity.class);
        context.startActivity(intent);
    }

    public void onCart(Context context){
        Intent intent = new Intent(context, RequestTableActivity.class);
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

}
