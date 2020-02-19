package ru.electric.ec.online.ui.bill;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import java.io.File;
import java.util.Objects;

import ru.electric.ec.online.R;
import ru.electric.ec.online.databinding.PdfBinding;
import ru.electric.ec.online.ui.menu.MenuViewModel;

public class BillActivity extends AppCompatActivity {

    BillViewModel viewModel;
    MenuViewModel navigationModel;
    PdfBinding binding;
    Bitmap bitmap;
    File localFile;
    private static final int REQUEST = 112;

    // declare the dialog as a member field of your activity
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = BillViewModel.getInstance();
        Bundle bundle = getIntent().getExtras();
        viewModel.title.set(Objects.requireNonNull(bundle).getString("title"));
        viewModel.link.set(bundle.getString("link"));
        viewModel.number.set(bundle.getInt("number"));
        this.setTitle(viewModel.title.get());

        binding = DataBindingUtil.setContentView(this, R.layout.pdf);
        binding.setViewModel(viewModel);

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.webView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + viewModel.link.get());
        binding.progressBar.setVisibility(View.GONE);

        navigationModel = new MenuViewModel(
                this,  binding.drawer, binding.include.toolbar, binding.navigator);
        setSupportActionBar(navigationModel.toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (navigationModel.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        navigationModel.actionBar.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        navigationModel.actionBar.onConfigurationChanged(newConfig);
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

}