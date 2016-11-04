package base.rechargeapp.appInterface;

import android.util.Log;

/**
 * Created by lin on 12/7/16.
 */

public class InterfaceClass implements DialogInterface {

    public InterfaceClass() {

    }

    @Override
    public void clickResponse(int Id, String response) {
        Log.e("Here it is ", "Id is " + Id + "Response is " + response);
    }

}
