package ru.electric.ec.online.router;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import ru.electric.ec.online.models.Info;
import ru.electric.ec.online.ui.enter.EnterActivity;
import ru.electric.ec.online.ui.info.InfoActivity;
import ru.electric.ec.online.ui.menu.MenuActivity;

public class RouterView {

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

    public static void openInfo(Context context, Info info){
        Intent intent = new Intent(context, InfoActivity.class);
        intent.putExtra("title", info.title);
        intent.putExtra("info", info.message);
        intent.putExtra("activityName", info.activityName);
        context.startActivity(intent);
    }

}
