package club.plus1.ec_online.viewmodels;

import android.app.Activity;
import android.content.Context;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import club.plus1.ec_online.R;
import club.plus1.ec_online.Server;
import club.plus1.ec_online.databinding.MainBinding;
import club.plus1.ec_online.views.InvoiceDetailsFragment;
import club.plus1.ec_online.views.InvoiceTableFragment;
import club.plus1.ec_online.views.MenuFragment;
import club.plus1.ec_online.views.RequestFragment;
import club.plus1.ec_online.views.RequestsBasketFragment;
import club.plus1.ec_online.views.RequestsTableFragment;

public class MenuViewModel {

    private static MenuViewModel mInstance;

    public MainBinding binding;
    public ActionBarDrawerToggle drawerToggle;
    public FragmentManager fragmentManager;

    private MenuViewModel() {
    }

    // Получение единственного экземпляра класса
    public static MenuViewModel getInstance() {
        if (mInstance == null) {
            mInstance = new MenuViewModel();
        }
        return mInstance;
    }

    private boolean selectDrawerItem(MenuItem menuItem) {

        Context context = binding.getRoot().getContext();
        switch(menuItem.getItemId()) {
            case R.id.action_request:
                ShowFragment(context, R.string.text_request, "Request");
                break;
            case R.id.action_order:
                ShowFragment(context, R.string.text_order, "Request");
                break;
            case R.id.action_basket:
                ShowFragment(context, R.string.text_basket, "RequestsBasket");
                break;
            case R.id.action_unconfirmed:
                ShowFragment(context, R.string.text_list_unconfirmed, "InvoiceTable");
                break;
            case R.id.action_reserved:
                ShowFragment(context, R.string.text_list_reserved, "InvoiceTable");
                break;
            case R.id.action_ordered:
                ShowFragment(context, R.string.text_list_ordered, "InvoiceTable");
                break;
            case R.id.action_canceled:
                ShowFragment(context, R.string.text_list_canceled, "InvoiceTable");
                break;
            case R.id.action_shipped:
                ShowFragment(context, R.string.text_list_shipped, "InvoiceTable");
                break;
            default:
                Server.getExit(binding.drawerLayout.getContext());
                return true;
        }
        menuItem.setChecked(true);
        binding.drawerLayout.closeDrawers();
        return true;
    }

    public void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        return selectDrawerItem(menuItem);
                    }
                });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            binding.drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return false;
    }

    public void onCheckExistence(Context context){
        ShowFragment(context, R.string.text_request, "Request");
    }

    public void onMakeOrder(Context context) {
        ShowFragment(context, R.string.text_order, "Request");
    }

    public void onCart(final Context context) {
        Server.getBasket(context);
    }

    public void onUnconfirmed(Context context){
        ShowFragment(context, R.string.text_list_unconfirmed, "InvoiceTable");
    }

    public void onReserves(Context context){
        ShowFragment(context, R.string.text_list_reserved, "InvoiceTable");
    }

    public void onOrders(Context context){
        ShowFragment(context, R.string.text_list_ordered, "InvoiceTable");
    }

    public void onCanceled(Context context){
        ShowFragment(context, R.string.text_list_canceled, "InvoiceTable");
    }

    public void onHistory(Context context){
        ShowFragment(context, R.string.text_list_shipped, "InvoiceTable");
    }

    public void onExit(final Context context) {
        Server.getExit(context);
    }

    public void ShowFragment(Context context, Object titleResouce, String className){
        Fragment fragment;
        switch (className){
            case "Request":
                fragment = new RequestFragment();
                break;
            case "RequestsTable":
                fragment = new RequestsTableFragment();
                break;
            case "RequestsBasket":
                fragment = new RequestsBasketFragment();
                break;
            case "InvoiceTable":
                fragment = new InvoiceTableFragment();
                break;
            case "InvoiceDetails":
                fragment = new InvoiceDetailsFragment();
                break;
            default:
                fragment = new MenuFragment();
        }
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        if (titleResouce instanceof String){
            ((Activity) context).setTitle((String)titleResouce);
        } else if (titleResouce instanceof Integer) {
            ((Activity) context).setTitle((int)titleResouce);
        }
    }
}
