package club.plus1.ec_online.views;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import club.plus1.ec_online.R;
import club.plus1.ec_online.databinding.MenuBinding;
import club.plus1.ec_online.viewmodels.MenuViewModel;

public class MenuActivity extends AppCompatActivity {

    MenuViewModel viewModel;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView navigation;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MenuBinding binding = DataBindingUtil.setContentView(this, R.layout.menu);
        viewModel = new MenuViewModel(this);
        binding.setViewModel(viewModel);

        // Установить Toolbar для замены ActionBar'а.
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Найти наш view drawer'а
        drawer = findViewById(R.id.drawer);
        drawerToggle = setupDrawerToggle();

        // Привязать события DrawerLayout'а к ActionBarToggle
        drawer.addDrawerListener(drawerToggle);
        navigation = findViewById(R.id.navigator);
        setupDrawerContent(navigation);

        navigation.setItemIconTintList(null);
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

    public void selectDrawerItem(MenuItem menuItem) {
        viewModel.onOptionsItemSelected(this, menuItem);
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawer.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START);
            return true;
        }
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
         return new ActionBarDrawerToggle(
                 this, drawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }
}
