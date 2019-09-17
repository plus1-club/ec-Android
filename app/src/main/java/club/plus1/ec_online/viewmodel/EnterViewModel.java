package club.plus1.ec_online.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import club.plus1.ec_online.view.MenuActivity;

public class EnterViewModel {
    public String login;
    public String password;

    public EnterViewModel(Context context) {
    }

    public void onEnter(Context context){
        Intent intent = new Intent(context, MenuActivity.class);
        context.startActivity(intent);
    }

    public void onPhone(Context context, String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }
}
