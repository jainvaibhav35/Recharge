package base.rechargeapp.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import base.rechargeapp.AppPrefernces.AppPreference;
import base.rechargeapp.utils.AppConstant;
import base.rechargeapp.utils.Utils;

/**
 * Created by lin on 13/10/16.
 */

public class ReceiverToLogout extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(AppConstant.LOGOUTFROMAPPLICATION)) {

            Utils.logout(context);

        }
    }
}
