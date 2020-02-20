package ru.electric.ec.online.server;

import android.content.Context;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.google.gson.internal.LinkedTreeMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import retrofit2.Response;
import ru.electric.ec.online.R;
import ru.electric.ec.online.common.App;
import ru.electric.ec.online.models.Invoice;
import ru.electric.ec.online.ui.details.DetailsActivity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("server.ServerRun")
class ServerRunTest {

    private ServerRun object;
    private Response<ServerData> response;
    private ServerData body;

    @Mock(extraInterfaces = {DetailsActivity.class})
    Context mockContext;

    @BeforeEach
    void setUp() {
        object = ServerRun.getInstance();
        mockContext = mock(Context.class);
        response = Response.success(new ServerData());
        body = response.body();
    }

    @AfterEach
    void tearDown() {
        object = null;
        mockContext = null;
        response = null;
    }

    @Test
    @DisplayName("getEnter(): Успешный вход на сервер")
    void getEnter_success() {
        Map<String, String> map = new LinkedTreeMap<>();
        map.put("user_token", "0");
        body.success = true;
        body.error = "";
        body.data = map;
        object.getEnter(mockContext, response, 0);
    }

    @Test
    @DisplayName("getEnter(): Вход на сервер с ошибкой")
    void getEnter_error() {
        body.success = false;
        body.error = "Test error";
        body.message = "Test message";
        object.getEnter(mockContext, response, 0);
    }

    @Test
    @DisplayName("getExit(): Выход с сервера")
    void getExit() {
        object.getExit(mockContext, response, 0);
    }

    @Test
    @DisplayName("getByCode(): Не успешный поиск товара по коду")
    void getByCode_not_success() {
        body.success = false;
        object.getByCode(mockContext, response, 0);
    }

    @Test
    @DisplayName("getByCode(): Поиск неделимого товара по коду")
    void getByCode_success_not_divided() {
        List<Object> list = new LinkedList<>();
        Map<String, String> map = new LinkedTreeMap<>();
        map.put("product", "Test");
        map.put("requestCount", "2");
        map.put("stockCount", "1");
        map.put("multiplicity", "3");
        map.put("unit", "шт");
        list.add(map);
        body.success = true;
        body.error = "";
        body.data = list;
        object.getByCode(mockContext, response, 0);
    }

    @Test
    @DisplayName("getByCode(): Поиск делимого товара по коду")
    void getByCode_success_divided() {
        List<Object> list = new LinkedList<>();
        Map<String, String> map = new LinkedTreeMap<>();
        map.put("product", "Test");
        map.put("requestCount", "2");
        map.put("stockCount", "1");
        map.put("multiplicity", "1");
        map.put("unit", "шт");
        list.add(map);
        body.success = true;
        body.error = "";
        body.data = list;
        object.getByCode(mockContext, response, 0);
    }

    @Test
    @DisplayName("getBasket(): Не успешное получение корзины")
    void getBasket_not_success() {
        body.success = false;
        object.getBasket(mockContext, response, 0);
    }

    @Test
    @DisplayName("getBasket(): Получение корзины с неделимым товаром")
    void getBasket_success_not_divided() {
        List<Object> list = new LinkedList<>();
        Map<String, String> map = new LinkedTreeMap<>();
        map.put("product", "Test");
        map.put("requestCount", "2");
        map.put("stockCount", "1");
        map.put("multiplicity", "3");
        map.put("unit", "шт");
        map.put("price", "10");
        list.add(map);
        body.success = true;
        body.error = "";
        body.data = list;
        object.getBasket(mockContext, response, 0);
    }

    @Test
    @DisplayName("getBasket(): Получение корзины с делимым товаром")
    void getBasket_success_divided() {
        List<Object> list = new LinkedList<>();
        Map<String, String> map = new LinkedTreeMap<>();
        map.put("product", "Test");
        map.put("requestCount", "2");
        map.put("stockCount", "1");
        map.put("multiplicity", "1");
        map.put("unit", "шт");
        map.put("price", "10");
        list.add(map);
        body.success = true;
        body.error = "";
        body.data = list;
        object.getBasket(mockContext, response, 0);
    }

    @Test
    @DisplayName("postBasket(): Отправка корзины на сервер")
    void postBasket() {
        object.postBasket(mockContext, response, 0);
    }

    @Test
    @DisplayName("putBasket(): Обновление корзины на сервере")
    void putBasket() {
        object.putBasket(mockContext, response, 0);
    }

    @Test
    @DisplayName("deleteBasket(): Очистка/удаление из корзины на сервере")
    void deleteBasket() {
        object.deleteBasket(mockContext, response, 0);
    }

    @Test
    @DisplayName("order(): Успешное создание заказа")
    void order_success() {
        Map<String, String> map = new LinkedTreeMap<>();
        map.put("number", "654321");
        body.success = true;
        body.data = map;
        body.error = "";
        object.order(mockContext, response, 0);
    }

    @Test
    @DisplayName("order(): Не успешное создание заказа")
    void order_not_success() {
        body.success = false;
        object.order(mockContext, response, 0);
    }

    @Test
    @DisplayName("invoiceList(): Не успешное получение списка счетов")
    void invoiceList_not_success() {
        body.success = false;
        object.invoiceList(mockContext, response, 0);
    }

    @Test
    @DisplayName("invoiceList(): Успешное получение списка счетов (не отгрузки)")
    void invoiceList_success_not_shipped() {
        List<Object> list = new ArrayList<>();
        Map<String, Object> map = new LinkedTreeMap<>();
        map.put("number", "1");
        map.put("date", "01.01.2020");
        map.put("status", "Test");
        map.put("waybill", "1");
        list.add(map);
        body.success = true;
        body.error = "";
        body.data = list;
        object.invoiceList(mockContext, response, 0);
    }

    @Test
    @DisplayName("invoiceList(): Успешное получение списка счетов (отгрузки)")
    void invoiceList_success_shipped() {
        List<Object> list = new ArrayList<>();
        Map<String, Object> map = new LinkedTreeMap<>();
        map.put("number", "1");
        map.put("date", "01.01.2020");
        map.put("sum", "100.0");
        map.put("status", "Отгружен");
        list.add(map);
        body.success = true;
        body.error = "";
        body.data = list;
        when(mockContext.getString(R.string.status_shipped)).thenReturn("Отгружен");
        object.invoiceList(mockContext, response, 0);
    }

    @Test
    @DisplayName("invoiceDetails(): Не успешное получение деталей счета")
    void invoiceDetails_not_success() {
        body.success = false;
        object.invoiceDetails(mockContext, response, 0);
    }

    @Test
    @DisplayName("invoiceDetails(): Успешное получение деталей пустого счета")
    void invoiceDetails_success_invoices_null() {
        body.success = true;
        body.error = "";
        object.invoiceDetails(mockContext, response, 0);
    }

    @Test
    @DisplayName("invoiceDetails(): Успешное получение деталей заполненного счета")
    void invoiceDetails_success_invoices_has() {
        body.success = true;
        body.error = "";
        ObservableList<Invoice> invoiceList = new ObservableArrayList<>();
        Invoice invoice = new Invoice(1,"01.01.2020", 100.0, "Отгружен");
        invoiceList.add(invoice);
        App.getModel().invoice.invoices.addAll(invoiceList);

        List<Object> list = new ArrayList<>();
        Map<String, Object> map = new LinkedTreeMap<>();
        map.put("product", "Product");
        map.put("count", "2");
        map.put("price", "50.0");
        map.put("sum", "100.0");
        map.put("available", "Test");
        map.put("delivery", "Test");
        list.add(map);
        body.success = true;
        body.error = "";
        body.data = list;

        object.invoiceDetails(mockContext, response, 1);
    }

    @Test
    @DisplayName("getPrint(): Не успешная печать счета (не получен файл)")
    void getPrint_not_success() {
        body.success = false;
        object.getPrint(mockContext, response, 0);
    }

    @Test
    @DisplayName("getPrint(): Успешная печать счета (получен файл)")
    void getPrint_success() {
        Map<String, String> map = new LinkedTreeMap<>();
        map.put("file", "File.test");
        body.success = true;
        body.data = map;
        body.error = "";
        object.getPrint(mockContext, response, 0);
    }

    @Test
    @DisplayName("isSuccess(): Не успешно - тело null")
    void isSuccess_body_null() {
        Response<ServerData> nullResponse = Response.success(null);
        assertFalse(object.isSuccess(nullResponse));
    }

    @Test
    @DisplayName("isSuccess(): Не успешно - пустая ошибка и успех = false")
    void isSuccess_success_false_error_empty() {
        body.success = false;
        body.error = "";
        assertFalse(object.isSuccess(response));
    }

    @Test
    @DisplayName("isSuccess(): Не успешно - не пустая ошибка и успех = false")
    void isSuccess_success_false_error_not_empty() {
        body.success = false;
        body.error = "test";
        assertFalse(object.isSuccess(response));
    }

    @Test
    @DisplayName("isSuccess(): Не успешно - не пустая ошибка и успех = true")
    void isSuccess_success_true_error_not_empty() {
        body.success = true;
        body.error = "test";
        assertFalse(object.isSuccess(response));
    }

    @Test
    @DisplayName("isSuccess(): Успешно - пустая ошибка и успех = true")
    void isSuccess_success_true_error_empty() {
        body.success = true;
        body.error = "";
        assertTrue(object.isSuccess(response));
    }

}