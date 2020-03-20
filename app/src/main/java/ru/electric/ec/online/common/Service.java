package ru.electric.ec.online.common;

import android.util.Log;

import androidx.databinding.ObservableDouble;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import ru.electric.ec.online.R;
import ru.electric.ec.online.models.Basket;
import ru.electric.ec.online.models.Search;

/**
 * Класс общих сервисных функций
 * @author Сергей Лавров
 * @version 0.5
 */
public class Service {

    /**
     * Форматирование суммы в рублях
     * @param value значение для форматировние
     * @param template шаблон текста для подстановки отформатированной цифры
     * @return сумма в рублях
     */
    public static String rub(ObservableDouble value, String template){
        DecimalFormat formatter=new DecimalFormat();
        DecimalFormatSymbols symbols=DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator(' '); //явно задаем символ разделителя тысяч
        symbols.setDecimalSeparator(',');
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        formatter.setDecimalFormatSymbols(symbols);
        String textValue = formatter.format(value.get());
        return template.replace("%1$d",textValue);
    }

    /**
     * Получение целого числа из строки
     * @param string строка с текстом для распознавания
     * @return целое число из строки
     */
    public static int getInt(String string) {
        if (string == null || string.isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(string);
        }
    }

    /**
     * Получение дробного числа из строки
     * @param string строка с текстом для распознавания
     * @return дробное число из строки
     */
    public static double getDouble(String string){
        if (string == null || string.isEmpty()) {
            return 0.0;
        } else {
            String cleared = string;
            cleared = cleared.replaceAll(" ", "");
            cleared = cleared.replaceAll(",", ".");
            return Double.parseDouble(cleared);
        }
    }

    /**
     * Сравнение строк на равенство без учёта регистра букв и с защитой от пустого значения
     * @param one первая строка
     * @param two вторая строка
     * @return равны ли две строки
     */
    public static boolean isEqual(String one, String two){
        if (one == null || two == null)
            return false;
        else
            return one.toLowerCase().equals(two.toLowerCase());
    }

    /**
     * Обновление статуса количества товара в переданной строке
     * @param request строка в которой проводится обновление статуса
     */
    public static void status(Search request){
        if (request.stockCount == -3) {
            request.status = Service.getStr(R.string.status_brown, request.requestProduct);
            request.color = R.color.brown;
            request.colorName = "brown";
        } else if (request.requestCount == 0) {
            request.status = Service.getStr(R.string.status_black);
            request.color = R.color.black;
            request.colorName = "black";
        } else if (request.stockCount == -2) {
            request.status = Service.getStr(R.string.status_violet);
            request.color = R.color.violet;
            request.colorName = "violet";
        } else if (request.needUpdate) {
            request.status = Service.getStr(R.string.status_blue);
            request.color = R.color.blue;
            request.colorName = "blue";
        } else if (request.stockCount == 0) {
            request.status = Service.getStr(R.string.status_red);
            request.color = R.color.red;
            request.colorName = "red";
        } else if (request.stockCount >= request.requestCount) {
            request.status = Service.getStr(R.string.status_green);
            request.color = R.color.green;
            request.colorName = "green";
        } else if (request.stockCount > 500 || request.stockCount == -1) {
            request.status = Service.getStr(R.string.status_orange);
            request.color = R.color.orange;
            request.colorName = "orange";
        } else {
            request.status = Service.getStr(R.string.status_yellow, request.stockCount);
            request.color = R.color.yellow;
            request.colorName = "yellow";
        }
    }

    /**
     * Обновление статуса количества товара в переданной строке
     * @param request строка в которой проводится обновление статуса
     */
    public static void status(Basket request){
        if (request.stockCount == -3) {
            request.status = Service.getStr(R.string.status_brown, request.requestProduct);
            request.color = R.color.brown;
            request.colorName = "brown";
        } else if (request.requestCount == 0) {
            request.status = Service.getStr(R.string.status_black);
            request.color = R.color.black;
            request.colorName = "black";
        } else if (request.stockCount == -2) {
            request.status = Service.getStr(R.string.status_violet);
            request.color = R.color.violet;
            request.colorName = "violet";
        } else if (request.needUpdate) {
            request.status = Service.getStr(R.string.status_blue);
            request.color = R.color.blue;
            request.colorName = "blue";
        } else if (request.stockCount == 0) {
            request.status = Service.getStr(R.string.status_red);
            request.color = R.color.red;
            request.colorName = "red";
        } else if (request.stockCount >= request.requestCount) {
            request.status = Service.getStr(R.string.status_green);
            request.color = R.color.green;
            request.colorName = "green";
        } else if (request.stockCount > 500 || request.stockCount == -1) {
            request.status = Service.getStr(R.string.status_orange);
            request.color = R.color.orange;
            request.colorName = "orange";
        } else {
            request.status = Service.getStr(R.string.status_yellow, request.stockCount);
            request.color = R.color.yellow;
            request.colorName = "yellow";
        }
    }


    /**
     * Вывод в информации или ошибки в консоль
     * @param isError это ошибка?
     * @param message сообщение для вывода в консоль
     */
    public static void log(boolean isError, String message) {
        if (isError) {
            Log.e("ec", message);
        } else {
            Log.d("ec", message);
        }
    }

    /**
     * Получение строки из ресурсов (без параметров)
     * @param res идентификатор строки
     * @return строка из ресурсов
     */
    public static String getStr(int res){
        try{
            return App.getAppContext().getString(res);
        } catch (Exception e){
            return "";
        }
    }

    /**
     * Получение строки из ресурсов (с 1 параметром)
     * @param res идентификатор строки
     * @param param параметр строки
     * @return строка из ресурсов
     */
    public static String getStr(int res, Object param){
        try {
            return App.getAppContext().getString(res, param);
        } catch (Exception e) {
            return param.toString();
        }
    }

    /**
     * Получение строки из ресурсов (с 2 параметрами)
     * @param res идентификатор строки
     * @param param1 1й параметр строки
     * @param param2 2й параметр строки
     * @return строка из ресурсов
     */
    public static String getStr(int res, Object param1, Object param2){
        try {
            return App.getAppContext().getString(res, param1, param2);
        } catch (Exception e) {
            return param1.toString() + param2.toString();
        }
    }
}
