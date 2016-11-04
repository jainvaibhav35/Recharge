package base.rechargeapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import base.rechargeapp.AppPrefernces.AppPreference;
import base.rechargeapp.activity.LoginActivity;
import base.rechargeapp.beans.Register.Response.Response;
import base.rechargeapp.database.sqllite.CRUD_OperationOfDB;
import base.rechargeapp.database.sqllite.ContractClass;

/**
 * Created by Vaibhav Jain on 6/10/16.
 */

public class Utils {


    /**
     * Method to check the Internet Connectiity
     *
     * @param mContext
     * @return -- true / false
     */
    public static boolean isConnectedToInternet(Context mContext) {

        ConnectivityManager connMgr = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        // ARE WE CONNECTED TO THE NET

        if (connMgr.getActiveNetworkInfo() != null
                && connMgr.getActiveNetworkInfo().isAvailable()
                && connMgr.getActiveNetworkInfo().isConnected())
            return true;
        else {
            return false;
        }
    }

    /**
     * This Method is used to get whether Edittext is null or have non -empty values
     *
     * @param mEditext - EditText for which for to test
     * @return - true/false
     */

    public static boolean isEmptyOrNot(EditText mEditext) {

        if (mEditext != null && !(mEditext.getText().toString().trim().equals("")))
            return true;
        else
            return false;
    }


    /**
     * This Method is used to get whether TextView is null or have non -empty values
     *
     * @param mTextview - textview for which for to test
     * @return - true/false
     */

    public static boolean isEmptyOrNot(TextView mTextview) {

        if (mTextview != null && !(mTextview.getText().toString().trim().equals("")))
            return true;
        else
            return false;
    }


    /**
     * Method is used to Validate Email Address
     *
     * @param email - email Address
     * @return - true / false
     */

    public final static boolean isValidEmail(CharSequence email) {
        if (email == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    /**
     * convert util date into String with specific format
     *
     * @param date   --- Provide Date Object here
     * @param format --- Date format
     * @return --- date in strings
     */
    public static String convertDateToString(Date date, String format) {

        DateFormat df = new SimpleDateFormat(format);
        String str = df.format(date);
        return str;

    }

    /**
     * convert String to Date
     *
     * @param dateString -- date in string
     * @param format     -- date format
     * @return -- Date Object
     */
    public static Date convertStringToDate(String dateString, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return convertedDate;
    }


    /**
     * Method to get Current date and Time in the given format
     *
     * @param format - Format
     */

    public static String getCurrentDateAndTime(String format) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat(format);
        return mdformat.format(calendar.getTime());
    }


    /**
     * This method will clear all prefernces and database entries
     *
     * @param mContext
     */
    public static void logout(Context mContext) {
        AppPreference.getInstance(mContext).clearPrefrences();
        CRUD_OperationOfDB mDatabase = new CRUD_OperationOfDB(mContext);
        mDatabase.deleteFromDB(ContractClass.FeedEntry.TABLE_NAME, null, null);

        AppPreference.getInstance(mContext).clearPrefrences();

        Intent loginIntent = new Intent(mContext, LoginActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(loginIntent);
    }

    public static Response convertUserDataToObject(Activity mActivity) {

        Response objResponse = null;
        if (AppPreference.getInstance(mActivity).getString(AppConstant.USERDATA) != null && !AppPreference.getInstance(mActivity).getString(AppConstant.USERDATA).equals("")) {
            objResponse = new Gson().fromJson(AppPreference.getInstance(mActivity).getString(AppConstant.USERDATA), Response.class);
        }
        return objResponse;
    }

    /**
     * This method is used to set User Data in preferences
     *
     * @param objRegisterResponse
     */
    public static void setValuesInPreferences(base.rechargeapp.beans.Register.Response.Response objRegisterResponse, Context mContext) {

        AppPreference mAppPreferences = AppPreference.getInstance(mContext);
        mAppPreferences.setBooleanWithKeys(AppConstant.ISLOGIN, true);
        mAppPreferences.setStringWithKeys(AppConstant.USERDATA, new Gson().toJson(objRegisterResponse));

    }


     /* DialogInterface dialogInterface = new DialogInterface() {
        @Override
        public void clickResponse(int Id, String response) {
            if (Id == DIALOGID) {
                if (response.equals(AppConstant.dialogYes))
                    // Do here what you want to perform on yes click
                    ApplicationSnackbar.showSnackBar(mRelativeMain, "Yes Clicked");
                else
                    // Do here what you want to perform on no click
                    ApplicationSnackbar.showSnackBar(mRelativeMain, "No Clicked");
            }
        }
    };*/
}
