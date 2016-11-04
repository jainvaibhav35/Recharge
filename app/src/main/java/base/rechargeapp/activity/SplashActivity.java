package base.rechargeapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;

import base.rechargeapp.AppPrefernces.AppPreference;
import base.rechargeapp.R;
import base.rechargeapp.utils.AppConstant;

/**
 * Created by lin on 17/10/16.
 */

public class SplashActivity extends AppCompatActivity {


    private AppPreference mAppprefernces ;
    private static int TIME = 3000;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isTaskRoot()) {
            finish();
            return;
        }

        setContentView(R.layout.splash_activity);

        mAppprefernces = AppPreference.getInstance(this);

        Handler mHandler = new Handler();

        if(mAppprefernces.getBoolean(AppConstant.ISLOGIN)){


            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            },TIME);


        }else{

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mainIntent = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            },TIME);

        }
    }
}
