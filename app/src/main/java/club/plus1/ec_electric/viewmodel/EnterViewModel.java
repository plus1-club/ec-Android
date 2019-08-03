package club.plus1.ec_electric.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import club.plus1.ec_electric.view.StartActivity;

public class EnterViewModel {
    public String login;
    public String password;

    public EnterViewModel(Context context) {
    }

    public void onEnter(Context context){
        Intent intent = new Intent(context, StartActivity.class);
        context.startActivity(intent);
    }

    public void onPhone(Context context, String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }
}
