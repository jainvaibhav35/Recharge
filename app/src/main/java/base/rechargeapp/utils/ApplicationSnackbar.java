package base.rechargeapp.utils;

import android.support.design.widget.Snackbar;
import android.view.ViewGroup;

/**
 * Organization LPTPL
 * Created by Vaibhav Jain on 18/5/16.
 * <p>
 * This class Contains method to shoa snackbar in Applicatioon
 */
public class ApplicationSnackbar {


    /**
     * Method to show snackbar anywhere in thew Application
     *
     * @param viewGroup --Parent Layout of the layout
     * @param message   -- Message to be displayed in Snackbar
     */
    public static void showSnackBar(ViewGroup viewGroup, String message) {
        try {
            if (viewGroup != null && message != null) {
                Snackbar snackbar = Snackbar.make(viewGroup, message, Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
