package ru.electric.ec.online.ui.basket;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import ru.electric.ec.online.R;
import ru.electric.ec.online.common.App;
import ru.electric.ec.online.common.Service;
import ru.electric.ec.online.databinding.BasketBinding;
import ru.electric.ec.online.models.Info;
import ru.electric.ec.online.models.Request;
import ru.electric.ec.online.router.RouterData;
import ru.electric.ec.online.router.RouterServer;
import ru.electric.ec.online.router.RouterView;
import ru.electric.ec.online.server.ServerData;
import ru.electric.ec.online.ui.menu.MenuViewModel;
import ru.electric.ec.online.ui.request.RequestViewModel;

public class BasketActivity extends AppCompatActivity {

    RequestViewModel viewModel;
    MenuViewModel navigationModel;

    private BasketViewAdapter adapter;
    private BasketBinding binding;
    private LinearLayoutManager layoutManager;
    private AppCompatActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        viewModel = RequestViewModel.getInstance();
        Bundle bundle = getIntent().getExtras();
        this.setTitle(Objects.requireNonNull(bundle).getString("title"));
        viewModel.total.set(0);

        // Подготовка биндинга
        binding = DataBindingUtil.setContentView(this, R.layout.basket);
        binding.setViewModel(viewModel);
        binding.list.setHasFixedSize(true);
        binding.list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // Подготовка и установка лайаут-менеджера
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.list.setLayoutManager(layoutManager);

        // Подготовка и установка адаптера
        adapter = new BasketViewAdapter();
        adapter.setItems(viewModel.basket);
        binding.list.setAdapter(adapter);
        viewModel.basketAdapter.set(adapter);

        // Подключение навигации
        navigationModel = new MenuViewModel(
                this,  binding.drawer, binding.include.toolbar, binding.navigator);
        setSupportActionBar(navigationModel.toolbar);

        // Обновление списка
        binding.swiperefresh.setRefreshing(true);
        RouterServer.getBasket(this);
        refreshBasket();
        binding.swiperefresh.setOnRefreshListener(
            () -> {
                RouterServer.getBasket(this);
                refreshBasket();
            }
        );
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

    public void refreshBasket(){
        new Handler().postDelayed(() -> {
            adapter = new BasketViewAdapter();
            adapter.setItems(viewModel.basket);
            binding.list.setAdapter(adapter);
            viewModel.total.set(0);
            viewModel.basketAdapter.set(adapter);
            for (Request item : viewModel.basket) {
                if(item.check) {
                    viewModel.total.set(viewModel.total.get() + item.requestCount * item.price);
                }
            }
            binding.swiperefresh.setRefreshing(false);
        }, 3000);
    }

    public void getBasketOk(ServerData body) {
        App.getModel().request.basket.clear();
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
                        Service.getDouble(el.get("price")),
                        true);
                if (request.requestCount % request.multiplicity > 0) {
                    request.requestCount += request.multiplicity - (request.requestCount % request.multiplicity);
                }
                App.getModel().request.basket.add(request);
            }
        }
    }

    public void updateBasketOk(ServerData body) {
        if (RouterServer.isSuccess(body)) {
            RouterServer.getBasket(this);
        }
    }

    public void orderOk(ServerData body) {
        if (RouterServer.isSuccess(body)) {
            LinkedTreeMap data = (LinkedTreeMap) body.data;
            int orderNumber = Service.getInt((String)data.get("number"));
            App.getModel().request.orderNumber.set(orderNumber);

            String message = Service.getStr(R.string.text_order_processed, orderNumber);
            Info info = new Info(false, true, message, "BasketActivity");
            info.title = this.getString(R.string.text_basket);
            RouterData.saveInfo(info);
            RouterView.openInfo(this, info);
        } else {
            RouterView.onUnsuccessful(this, body);
        }
        RouterServer.getBasket(this);
    }

    public void basketError(Throwable throwable) {
        RouterView.onError(this, throwable);
    }
}
