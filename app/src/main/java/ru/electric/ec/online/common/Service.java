package ru.electric.ec.online.common;

import android.util.Log;

import androidx.databinding.ObservableDouble;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import ru.electric.ec.online.R;
import ru.electric.ec.online.models.Count;

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
     * Получение статуса количества товара на основе переданных данных
     * @param count запрашиваемое количество товара
     * @param stockCount количество товара на складе или статус количества
     *      -1 - товара недостаточно, но более 500;
     *      -2 - превышено количество проверок;
     *      -3 - товар не найден
     * @param requestProduct искомый продукт (для статуса товар не найден)
     * @param needUpdate требуется ли проверить количество (оно в клиенте не известно)
     * @return статус количества товара
     */
    public static Count status(int count, int stockCount, String requestProduct, boolean needUpdate){
        String textStatus;
        String colorName;
        int color;
        if (stockCount == -3) {
            textStatus = Service.getStr(R.string.status_brown, requestProduct);
            color = R.color.brown;
            colorName = "brown";
        } else if (count == 0) {
            textStatus = Service.getStr(R.string.status_black);
            color = R.color.black;
            colorName = "black";
        } else if (stockCount == -2) {
            textStatus = Service.getStr(R.string.status_violet);
            color = R.color.violet;
            colorName = "violet";
        } else if (needUpdate) {
            textStatus = Service.getStr(R.string.status_blue);
            color = R.color.blue;
            colorName = "blue";
        } else if (stockCount == 0) {
            textStatus = Service.getStr(R.string.status_red);
            color = R.color.red;
            colorName = "red";
        } else if (stockCount >= count) {
            textStatus = Service.getStr(R.string.status_green);
            color = R.color.green;
            colorName = "green";
        } else if (stockCount > 500 || stockCount == -1) {
            textStatus = Service.getStr(R.string.status_orange);
            color = R.color.orange;
            colorName = "orange";
        } else {
            textStatus = Service.getStr(R.string.status_yellow, stockCount);
            color = R.color.yellow;
            colorName = "yellow";
        }

        return new Count(count, stockCount, requestProduct, textStatus, color, colorName);
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
