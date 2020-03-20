package ru.electric.ec.online.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import ru.electric.ec.online.R;
import ru.electric.ec.online.common.Service;
import ru.electric.ec.online.data.DataRouter;
import ru.electric.ec.online.models.Info;
import ru.electric.ec.online.server.ServerData;
import ru.electric.ec.online.ui.enter.EnterActivity;
import ru.electric.ec.online.ui.info.InfoActivity;
import ru.electric.ec.online.ui.menu.MenuActivity;
import ru.electric.ec.online.ui.request.RequestActivity;

public class ViewRouter {

    public static void openPhoneCall(Context context, String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }

    public static void openMenu(Context context){
        Intent intent = new Intent(context, MenuActivity.class);
        context.startActivity(intent);
    }

    public static void openEnter(Context context){
        Intent intent = new Intent(context, EnterActivity.class);
        context.startActivity(intent);
    }

    public static void openRequest(Context context, int page){
        Intent intent = new Intent(context, RequestActivity.class);
        intent.putExtra("page", page);
        context.startActivity(intent);
    }

    public static void openInfo(Context context, Info info){
        Intent intent = new Intent(context, InfoActivity.class);
        intent.putExtra("title", info.title);
        intent.putExtra("info", info.message);
        if (info.activityName == null || info.activityName.isEmpty()){
            intent.putExtra("activityName", context.getClass().getSimpleName());
        } else {
            intent.putExtra("activityName", info.activityName);
        }
        context.startActivity(intent);
    }

    public static void onError(Context context, Throwable throwable){
        String text;
        if (throwable instanceof UnknownHostException){
            text = Service.getStr(R.string.error_UnknownHostException);
        } else if (throwable instanceof SocketTimeoutException){
            text = Service.getStr(R.string.error_SocketTimeoutException);
        } else {
            text = throwable.getMessage();
        }
        String message = Service.getStr(R.string.text_response_failure, text);
        Info info = new Info(false, true, message);
        DataRouter.saveInfo(info);
        ViewRouter.openInfo(context, info);
    }

    public static void onError(Context context, Throwable throwable, String activityName){
        String text;
        if (throwable instanceof UnknownHostException){
            text = Service.getStr(R.string.error_UnknownHostException);
        } else if (throwable instanceof SocketTimeoutException){
            text = Service.getStr(R.string.error_SocketTimeoutException);
        } else {
            text = throwable.getMessage();
        }
        String message = Service.getStr(R.string.text_response_failure,  text);
        Info info = new Info(false, true, message, activityName);
        DataRouter.saveInfo(info);
        ViewRouter.openInfo(context, info);
    }

    public static void onUnsuccessful(Context context, ServerData body){
        String message = Service.getStr(R.string.text_response_error, body.message);
        Info info = new Info(false, true, message);
        DataRouter.saveInfo(info);
        ViewRouter.openInfo(context, info);
    }
    public static void onUnsuccessful(Context context, ServerData body, String activityName){
        String message = Service.getStr(R.string.text_response_error, body.message);
        Info info = new Info(false, true, message, activityName);
        DataRouter.saveInfo(info);
        ViewRouter.openInfo(context, info);
    }
}
