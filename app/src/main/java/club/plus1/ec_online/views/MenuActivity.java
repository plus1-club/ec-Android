package club.plus1.ec_online.views;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import club.plus1.ec_online.R;
import club.plus1.ec_online.viewmodels.MenuViewModel;

public class MenuActivity extends AppCompatActivity {

    MenuViewModel manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = MenuViewModel.getInstance();

        manager.binding = DataBindingUtil.setContentView(this, R.layout._main);
        manager.binding.setViewModel(manager);

        setSupportActionBar(manager.binding.include.toolbar);

        manager.drawerToggle = new ActionBarDrawerToggle(
                this,
                manager.binding.drawerLayout,
                manager.binding.include.toolbar,
                R.string.drawer_open, R.string.drawer_close);
        manager.binding.drawerLayout.addDrawerListener(manager.drawerToggle);
        manager.binding.nvView.setItemIconTintList(null);

        manager.setupDrawerContent(manager.binding.nvView);
        this.setTitle(R.string.app_name);
        manager.fragmentManager = getSupportFragmentManager();
        manager.fragmentManager.beginTransaction().add(R.id.flContent, new MenuFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (manager.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        manager.drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        manager.drawerToggle.onConfigurationChanged(newConfig);
    }
}
