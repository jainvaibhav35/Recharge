package base.rechargeapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import base.rechargeapp.R;
import base.rechargeapp.appInterface.RetrofitInterface;
import base.rechargeapp.beans.forgot_password.RequestForgotPassword;
import base.rechargeapp.beans.forgot_password.ResponseForgotPassword;
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

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText mEmail;
    private TextView mLogin, mForgotPassword;
    private RelativeLayout mRelativeMain, mRelativeRegister;
    private ProgressBar mProgressbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initViews();
        setListner();
    }

    /**
     * This Method is used for setting Listners
     */
    private void setListner() {
        mForgotPassword.setOnClickListener(this);
        mRelativeRegister.setOnClickListener(this);
    }

    /**
     * This method is used to initialize all the views
     */
    private void initViews() {
        mEmail = (EditText) findViewById(R.id.edtxt_username);
        mLogin = (TextView) findViewById(R.id.txt_login);
        mRelativeMain = (RelativeLayout) findViewById(R.id.rl_main);
        mRelativeRegister = (RelativeLayout) findViewById(R.id.rl_register);
        mProgressbar = (ProgressBar) findViewById(R.id.progress_bar);
        mForgotPassword = (TextView) findViewById(R.id.txt_forgot);
    }

    @Override
    public void onClick(View mView) {

        switch (mView.getId()) {
            case R.id.txt_forgot:

                if (checkforValidation()) {
                    if (Utils.isConnectedToInternet(this)) {
                        hitForgotPasswordAPI(createRequest());
                    } else
                        ApplicationSnackbar.showSnackBar(mRelativeMain, getString(R.string.internet_connection));
                }

                break;

            case R.id.rl_register:
                Intent loginIntent = new Intent(this, LoginActivity.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(loginIntent);
                finish();
                break;
        }
    }

    /**
     * Method to validate all the fieds
     *
     * @return true / false - Either all fields are appopriate or not
     */
    private boolean checkforValidation() {

        if (!Utils.isEmptyOrNot(mEmail)) {
            ApplicationSnackbar.showSnackBar(mRelativeMain, "Email " + getString(R.string.mandatory));
            return false;
        }

        if (!Utils.isValidEmail(mEmail.getText().toString())) {
            ApplicationSnackbar.showSnackBar(mRelativeMain, getString(R.string.invalid_email));
            return false;
        }

        return true;
    }


    /**
     * Method to hit API request with POST Method
     */
    private void hitForgotPasswordAPI(RequestForgotPassword requestForgotPassword) {


        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // prepare call in Retrofit 2.0
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<ResponseForgotPassword> call = retrofitInterface.hitForgotPassword(requestForgotPassword);

        //asynchronous call

        call.enqueue(new Callback<ResponseForgotPassword>() {
            @Override
            public void onResponse(Call<ResponseForgotPassword> call, Response<ResponseForgotPassword> response) {
                if (response != null && response.body() != null) {
                    if (response.body().getErrorCode().equals(AppConstant.SUCCESS_ERROR_CODE)) {

                    } else if (response.body().getErrorCode().equals(AppConstant.FAILURE_ERROR_CODE)) {

                    }
                }
                ApplicationSnackbar.showSnackBar(mRelativeMain, response.body().getResult());
                mProgressbar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseForgotPassword> call, Throwable t) {
                ApplicationSnackbar.showSnackBar(mRelativeMain, getString(R.string.something_went_wrong));
                mProgressbar.setVisibility(View.GONE);
            }
        });
    }

    private RequestForgotPassword createRequest() {

        RequestForgotPassword objRequestForgotPassword = new RequestForgotPassword();
        objRequestForgotPassword.setEmail(mEmail.getText().toString());
        return objRequestForgotPassword;
    }
}
