package ru.electric.ec.online.ui.menu;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import ru.electric.ec.online.R;

public class MenuViewModel {

    public Toolbar toolbar;
    public ActionBarDrawerToggle actionBar;
    private AppCompatActivity activity;
    private DrawerLayout drawer;
    private NavigationView navigation;

    public MenuViewModel(
            AppCompatActivity activity,
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

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
            menuItem -> {
                selectDrawerItem(menuItem);
                return true;
            });
    }

    private void selectDrawerItem(MenuItem menuItem) {
        MenuItemViewModel.getInstance(activity).onOptionsItemSelected(activity, menuItem);
        menuItem.setChecked(true);
        drawer.closeDrawers();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(activity, this.drawer, this.toolbar,
                R.string.text_drawer_open,  R.string.text_drawer_close);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START);
            return true;
        }
        return actionBar.onOptionsItemSelected(item);
    }
}