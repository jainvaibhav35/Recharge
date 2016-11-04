package base.rechargeapp.AppPrefernces;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by lin on 4/7/16.
 */

public class AppPreference {

    private static AppPreference preference;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;


    /**
     * Constructor
     *
     * @param context
     */
    private AppPreference(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Method to get the sharedPrefernce Object if null
     * (SingleTon class
     * )
     *
     * @param context
     * @return
     */

    public static AppPreference getInstance(Context context) {
        if (preference == null)
            preference = new AppPreference(context);

        return preference;
    }


    /**
     * Method to set any value in preferences
     *
     * @param key   key used for Preference
     * @param value Value used for Prefernce
     */
    public void setStringWithKeys(String key, String value) {

        mEditor = mSharedPreferences.edit();
        mEditor.putString(key, value);
        mEditor.commit();
    }

    /**
     * Method to get the value of the key
     *
     * @param Key Key
     * @return Value for the key if exist , if key not exist then return ""
     */


    public boolean getBoolean(String Key) {
        return mSharedPreferences.getBoolean(Key, false);
    }



    /**
     * Method to set any value in preferences
     *
     * @param key   key used for Preference
     * @param value Value used for Prefernce
     */
    public void setBooleanWithKeys(String key, boolean value) {

        mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    /**
     * Method to get the value of the key
     *
     * @param Key Key
     * @return Value for the key if exist , if key not exist then return ""
     */


    public String getString(String Key) {
        return mSharedPreferences.getString(Key, "");
    }


    /**
     * Method to clear all the values in the shared preference
     */
    public void clearPrefrences(){
        mEditor = mSharedPreferences.edit();
        mEditor.clear();
        mEditor.commit();
    }


}
