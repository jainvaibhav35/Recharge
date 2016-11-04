package base.rechargeapp.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by lin on 29/8/16.
 */

public class CustomReceivers extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("abc")) {
            Log.e("cr", "onReceive: " + intent.getAction());
        }

        if (intent.getAction().equals("def")) {
            Log.e("scr", "onReceive: " + intent.getAction());
        }
    }
}
