package base.rechargeapp.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import base.rechargeapp.R;
import base.rechargeapp.appInterface.RetrofitInterface;
import base.rechargeapp.beans.Login.LoginRequest;
import base.rechargeapp.beans.Register.Response.RegisterResponse;
import base.rechargeapp.receivers.ReceiverToLogout;
import base.rechargeapp.utils.AppConstant;
import base.rechargeapp.utils.ApplicationSnackbar;
import base.rechargeapp.utils.NetworkConstant;
import base.rechargeapp.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lin on 11/10/16.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText mUserName, mPassword;
    private TextView mLogin, mForgotPassword;
    private RelativeLayout mRelativeMain, mRelativeRegister;
    private ProgressBar mProgressbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initViews();
        setListner();
        setPasswordTransformation();
    }

    private void setPasswordTransformation() {

        mPassword.setTransformationMethod(new PasswordTransformationMethod());
    }

    /**
     * This Method is used for setting Listners
     */
    private void setListner() {
        mLogin.setOnClickListener(this);
        mRelativeRegister.setOnClickListener(this);
        mForgotPassword.setOnClickListener(this);
    }

    /**
     * This method is used to initialize all the views
     */
    private void initViews() {
        mUserName = (EditText) findViewById(R.id.edtxt_username);
        mPassword = (EditText) findViewById(R.id.edtxt_password);
        mLogin = (TextView) findViewById(R.id.txt_login);
        mRelativeMain = (RelativeLayout) findViewById(R.id.rl_main);
        mRelativeRegister = (RelativeLayout) findViewById(R.id.rl_register);
        mProgressbar = (ProgressBar) findViewById(R.id.progress_bar);
        mForgotPassword = (TextView) findViewById(R.id.forgot_password);
    }

    @Override
    public void onClick(View mView) {

        switch (mView.getId()) {
            case R.id.txt_login:
                if (checkforValidation()) {
                    if (Utils.isConnectedToInternet(this)) {
                        hitLoginAPI(createRequest());
                        mProgressbar.setVisibility(View.VISIBLE);
                    } else
                        ApplicationSnackbar.showSnackBar(mRelativeMain, getString(R.string.internet_connection));
                }

                setAlarmManagerToLogoutAfter30Days();

                break;

            case R.id.rl_register:
                Intent registerIntent = new Intent(this, RegistrationActivity.class);
                registerIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(registerIntent);
                finish();
                break;

            case R.id.forgot_password:
                Intent forgotPasswordIntent = new Intent(this, ForgotPasswordActivity.class);
                forgotPasswordIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(forgotPasswordIntent);
                break;
        }
    }


    /**
     * This Method is used to set alarm so that after 30 days user will automaticaly logged out
     */
    private void setAlarmManagerToLogoutAfter30Days() {

        Intent intent = new Intent(this, ReceiverToLogout.class);
        intent.setAction(AppConstant.LOGOUTFROMAPPLICATION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), AppConstant.ALARMANAGEREQUESTCODE, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + ((long) 30 * 24 * 60 * 60 * 1000), pendingIntent);       // 30  days

    }

    /**
     * Method to validate all the fieds
     *
     * @return true / false - Either all fields are appopriate or not
     */
    private boolean checkforValidation() {

        if (!Utils.isEmptyOrNot(mUserName)) {
            ApplicationSnackbar.showSnackBar(mRelativeMain, "Email/Phone " + getString(R.string.mandatory));
            return false;
        }

        if (!Utils.isEmptyOrNot(mPassword)) {
            ApplicationSnackbar.showSnackBar(mRelativeMain, "Password " + getString(R.string.mandatory));
            return false;
        }

        return true;
    }


    /**
     * Method to hit API request with POST Method
     */
    private void hitLoginAPI(LoginRequest registrationData) {


        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // prepare call in Retrofit 2.0
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<RegisterResponse> call = retrofitInterface.hitLoginAPI(registrationData);

        //asynchronous call

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response != null && response.body() != null) {
                    if (response.body().getErrorCode().equals(AppConstant.SUCCESS_ERROR_CODE)) {

                        Utils.setValuesInPreferences(response.body().getResponse(), LoginActivity.this);
                        navigateToMainActivity();

                    } else if (response.body().getErrorCode().equals(AppConstant.FAILURE_ERROR_CODE)) {
                        ApplicationSnackbar.showSnackBar(mRelativeMain, response.body().getResult());
                    }
                }

                mProgressbar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                ApplicationSnackbar.showSnackBar(mRelativeMain, getString(R.string.something_went_wrong));
                mProgressbar.setVisibility(View.GONE);
            }
        });
    }

    private void navigateToMainActivity() {

        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    private LoginRequest createRequest() {

        LoginRequest objLoginRequest = new LoginRequest();
        objLoginRequest.setEmail(mUserName.getText().toString());
        objLoginRequest.setPassword(mPassword.getText().toString());
        return objLoginRequest;
    }
}
