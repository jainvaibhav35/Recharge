package base.rechargeapp;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by lin on 7/7/16.
 */

public class application extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        // Initializing FireBase
//        Firebase.setAndroidContext(this);

        // Initializing Calligraphy
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/SF_UI_Display_Light.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }
}
