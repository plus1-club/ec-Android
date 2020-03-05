package ru.electric.ec.online.ui.search;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import ru.electric.ec.online.R;
import ru.electric.ec.online.common.App;
import ru.electric.ec.online.common.Service;
import ru.electric.ec.online.databinding.SearchBinding;
import ru.electric.ec.online.models.Request;
import ru.electric.ec.online.router.RouterServer;
import ru.electric.ec.online.router.RouterView;
import ru.electric.ec.online.server.ServerData;
import ru.electric.ec.online.ui.info.InfoActivity;
import ru.electric.ec.online.ui.menu.MenuViewModel;
import ru.electric.ec.online.ui.request.RequestViewModel;

public class SearchActivity extends AppCompatActivity {

    RequestViewModel viewModel;
    MenuViewModel navigationModel;

    private SearchViewAdapter adapter;
    private SearchBinding binding;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = RequestViewModel.getInstance();
        viewModel.search.clear();

        Bundle bundle = getIntent().getExtras();
        this.setTitle(Objects.requireNonNull(bundle).getString("title"));

        // Подготовка биндинга
        binding = DataBindingUtil.setContentView(this, R.layout.search);
        binding.setViewModel(viewModel);
        binding.list.setHasFixedSize(true);

        // Подготовка и установка лайаут-менеджера
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.list.setLayoutManager(layoutManager);

        // Подготовка и установка адаптера
        adapter = new SearchViewAdapter();
        binding.list.setAdapter(adapter);
        adapter.setItems(viewModel.search);

        // Подключение навигации
        navigationModel = new MenuViewModel(
                this,  binding.drawer, binding.include.toolbar, binding.navigator);
        setSupportActionBar(navigationModel.toolbar);

        // Обновление списка
        binding.swiperefresh.setRefreshing(true);
        if (viewModel.isExcel.get()){
            RouterServer.fromExcel(this, viewModel);
        } else {
            RouterServer.byCode(this, viewModel);
        }
        binding.swiperefresh.setOnRefreshListener(this::refreshSearch);
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

    public void refreshSearch(){
        binding.swiperefresh.setRefreshing(true);
        adapter = new SearchViewAdapter();
        if (viewModel.search.size() > 0){
            adapter.setItems(viewModel.search);
            binding.list.setAdapter(adapter);
            binding.swiperefresh.setRefreshing(false);
        } else {
            Intent intent = new Intent(this, InfoActivity.class);
            intent.putExtra("title", this.getTitle());
            intent.putExtra("info", this.getString(R.string.text_product_not_found));
            intent.putExtra("activityName", "RequestActivity");
            this.startActivity(intent);
        }
     }

    public void searchOk(ServerData body) {
        App.getModel().request.search.clear();
        if (RouterServer.isSuccess(body)) {
            List<?> data = (List<?>) body.data;
            for (Object element : data) {
                @SuppressWarnings("unchecked")
                Map<String, String> el = (LinkedTreeMap<String, String>) element;
                Request request = new Request(
                        el.get("product"),
                        Service.getInt(el.get("requestCount")),
                        Service.getInt(el.get("stockCount")),
                        Service.getInt(el.get("multiplicity")),
                        el.get("unit"),
                        false);
                if (request.requestCount % request.multiplicity > 0) {
                    request.requestCount += request.multiplicity - (request.requestCount % request.multiplicity);
                }
                App.getModel().request.search.add(request);
            }
        } else {
            RouterView.onUnsuccessful(this, body, "RequestActivity");
        }
        adapter = new SearchViewAdapter();
        if (viewModel.search.size() > 0){
            adapter.setItems(viewModel.search);
            binding.list.setAdapter(adapter);
            binding.swiperefresh.setRefreshing(false);
        } else {
            Intent intent = new Intent(this, InfoActivity.class);
            intent.putExtra("title", this.getTitle());
            intent.putExtra("info", this.getString(R.string.text_product_not_found));
            intent.putExtra("activityName", "RequestActivity");
            this.startActivity(intent);
        }
    }

    public void searchError(Throwable throwable) {
        RouterView.onError(this, throwable, "RequestActivity");
    }
}
