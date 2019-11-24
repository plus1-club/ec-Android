package club.plus1.ec_online.viewmodels;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import club.plus1.ec_online.R;

public class NavigationViewModel {

    @SuppressLint("StaticFieldLeak")
    private static NavigationViewModel mInstance;

    public Toolbar toolbar;
    public ActionBarDrawerToggle actionBar;
    private Activity activity;
    private DrawerLayout drawer;
    private NavigationView navigation;

    private NavigationViewModel(
            Activity activity,
            DrawerLayout drawer,
            Toolbar toolbar,
            NavigationView navigation) {
        this.drawer = drawer;
        this.toolbar = toolbar;
        this.navigation = navigation;
        this.activity = activity;

        // Привязать события DrawerLayout'а к ActionBarToggle
        this.actionBar = setupDrawerToggle();
        drawer.addDrawerListener(this.actionBar);

        // Настройка панели навигации
        setupDrawerContent(this.navigation);
        this.navigation.setItemIconTintList(null);
    }

    // Получение единственного экземпляра класса
    public static NavigationViewModel getInstance(
            Activity activity, DrawerLayout drawer, Toolbar toolbar, NavigationView navigation) {
        if (mInstance == null) {
            mInstance = new NavigationViewModel(activity, drawer, toolbar, navigation);
        }
        return mInstance;
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    private void selectDrawerItem(MenuItem menuItem) {
        MenuViewModel.getInstance(activity).onOptionsItemSelected(activity, menuItem);
        menuItem.setChecked(true);
        activity.setTitle(menuItem.getTitle());
        drawer.closeDrawers();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(
                activity, this.drawer, this.toolbar,
                R.string.drawer_open,  R.string.drawer_close);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START);
            return true;
        }
        return actionBar.onOptionsItemSelected(item);
    }

}
