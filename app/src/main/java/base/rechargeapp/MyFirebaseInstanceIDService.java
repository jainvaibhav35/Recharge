package base.rechargeapp;

import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by lin on 20/10/16.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
    }
}
