package club.plus1.ec_electric.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import club.plus1.ec_electric.R;
import club.plus1.ec_electric.databinding.MenuBinding;
import club.plus1.ec_electric.viewmodel.MenuViewModel;

public class MenuActivity extends AppCompatActivity {

    MenuViewModel viewModel;
    MenuViewModel menuModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MenuBinding binding = DataBindingUtil.setContentView(this, R.layout.menu);
        viewModel = new MenuViewModel(this);
        menuModel = new MenuViewModel(this);
        binding.setViewModel(viewModel);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item == null) {
            return false;
        } else if(menuModel.onOptionsItemSelected(this, item)) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
