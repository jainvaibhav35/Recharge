package base.rechargeapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import base.rechargeapp.AppPrefernces.AppPreference;
import base.rechargeapp.R;
import base.rechargeapp.appInterface.RetrofitInterface;
import base.rechargeapp.beans.Register.RegisterRequest;
import base.rechargeapp.beans.Register.Response.RegisterResponse;
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

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText mFirstName,mSecondName,mEmailAddess ,mBussinessName,mPassword,mConfirmPassword,mRFC , mAddress ,mMobileNumber;
    private AppCompatCheckBox mAcceptReject ;
    private TextView mRegister ,mTermsAndCondition;
    private RelativeLayout mRelativeMain , mRelativeLogin ;
    private ProgressBar mProgressbar ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        initViews();
        setListner();
    }


    /**
     * This Method is used for setting Listners
     */
    private void setListner() {
        mRegister.setOnClickListener(this);
        mRelativeLogin.setOnClickListener(this);
        mTermsAndCondition.setOnClickListener(this);
    }

    /**
     * This method is used to initialize all the views
     */
    private void initViews() {
        mFirstName = (EditText) findViewById(R.id.edtxt_firstname);
        mSecondName = (EditText) findViewById(R.id.edtxt_secondname);
        mEmailAddess = (EditText) findViewById(R.id.edtxt_email_address);
        mBussinessName = (EditText) findViewById(R.id.edtxt_bussinessname);
        mPassword = (EditText) findViewById(R.id.edtxt_password);
        mConfirmPassword = (EditText) findViewById(R.id.edtxt_confirm_password);
        mAcceptReject = (AppCompatCheckBox) findViewById(R.id.terms_checkbox);
        mRegister = (TextView) findViewById(R.id.txt_register);
        mRelativeMain = (RelativeLayout) findViewById(R.id.rl_main);
        mRelativeLogin = (RelativeLayout) findViewById(R.id.rl_login);
        mTermsAndCondition = (TextView) findViewById(R.id.terms_and_condition);
        mRFC = (EditText) findViewById(R.id.edtxt_rfc);
        mAddress = (EditText) findViewById(R.id.edtxt_address);
        mMobileNumber = (EditText) findViewById(R.id.edtxt_mobile_number);
        mProgressbar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    @Override
    public void onClick(View mView) {

        switch (mView.getId()){
            case R.id.txt_register:
                if(checkforValidation()){
                    if(Utils.isConnectedToInternet(this)){
                        try {
                            hitRegisterAPI(createRequest());
                            mProgressbar.setVisibility(View.VISIBLE);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        }else {
                        ApplicationSnackbar.showSnackBar(mRelativeMain,getString(R.string.internet_connection));
                    }
                }
                break;

            case R.id.terms_and_condition:
                startActivity(new Intent(this,TermsAndConditionActivity.class));
                break;

            case R.id.rl_login:
                Intent loginIntent = new Intent(this,LoginActivity.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(loginIntent);
                finish();
                break;
        }

    }

    private RegisterRequest createRequest() {

        RegisterRequest objRegisterBean = new RegisterRequest() ;
        objRegisterBean.setFirst_name(mFirstName.getText().toString());
        objRegisterBean.setLast_name(mSecondName.getText().toString());
        objRegisterBean.setEmail(mEmailAddess.getText().toString());
        objRegisterBean.setBusiness_type(mBussinessName.getText().toString());
        objRegisterBean.setRfc(mRFC.getText().toString());
        objRegisterBean.setAddress(mAddress.getText().toString());
        objRegisterBean.setPassword(mPassword.getText().toString());
        objRegisterBean.setMob_no(mMobileNumber.getText().toString());

        return objRegisterBean;
    }


    /**
     * Method to validate all the fieds
     * @return true / false - Either all fields are appopriate or not
     */
    private boolean checkforValidation() {

        if(!Utils.isEmptyOrNot(mFirstName)) {
            ApplicationSnackbar.showSnackBar(mRelativeMain,"FirstName " + getString(R.string.mandatory));
            return false;
        }

        if(!Utils.isEmptyOrNot(mSecondName)) {
            ApplicationSnackbar.showSnackBar(mRelativeMain,"SecondName " + getString(R.string.mandatory));
            return false;
        }

        if(!Utils.isEmptyOrNot(mEmailAddess)) {
            ApplicationSnackbar.showSnackBar(mRelativeMain,"EmailAddress " + getString(R.string.mandatory));
            return false;
        }

        if(!Utils.isValidEmail(mEmailAddess.getText().toString())) {
            ApplicationSnackbar.showSnackBar(mRelativeMain, getString(R.string.invalid_email));
            return false;
        }

        if(!Utils.isEmptyOrNot(mMobileNumber)) {
            ApplicationSnackbar.showSnackBar(mRelativeMain,"MobileNumber " + getString(R.string.mandatory));
            return false;
        }


        if(!Utils.isEmptyOrNot(mBussinessName)) {
            ApplicationSnackbar.showSnackBar(mRelativeMain,"BussinessName " + getString(R.string.mandatory));
            return false;
        }


        if(!Utils.isEmptyOrNot(mRFC)) {
            ApplicationSnackbar.showSnackBar(mRelativeMain,"RFC " + getString(R.string.mandatory));
            return false;
        }


        if(!Utils.isEmptyOrNot(mAddress)) {
            ApplicationSnackbar.showSnackBar(mRelativeMain,"Address " + getString(R.string.mandatory));
            return false;
        }

        if(!Utils.isEmptyOrNot(mPassword)) {
            ApplicationSnackbar.showSnackBar(mRelativeMain,"Password " +getString(R.string.mandatory));
            return false;
        }

        if(!Utils.isEmptyOrNot(mConfirmPassword)) {
            ApplicationSnackbar.showSnackBar(mRelativeMain,"Confirm Password " +getString(R.string.mandatory));
            return false;
        }

        if(!(mPassword.getText().toString().equals(mConfirmPassword.getText().toString()))){
            ApplicationSnackbar.showSnackBar(mRelativeMain,getString(R.string.password_mismatch));
            return false;
        }

        if(!mAcceptReject.isChecked()){
            ApplicationSnackbar.showSnackBar(mRelativeMain,getString(R.string.accept_terms_condition));
            return false;
        }

        return true;
    }


    /**
     * Method to hit API request with POST Method
     */
    private void hitRegisterAPI(RegisterRequest registrationData) {


        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // prepare call in Retrofit 2.0
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<RegisterResponse> call = retrofitInterface.hitRegisterAPI(registrationData);

        //asynchronous call

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response != null && response.body() != null){
                    if(response.body().getErrorCode().equals(AppConstant.SUCCESS_ERROR_CODE)){

                        Utils.setValuesInPreferences(response.body().getResponse(), RegistrationActivity.this);
                        navigateToMainActivity();

                    }else if(response.body().getErrorCode().equals(AppConstant.FAILURE_ERROR_CODE)){
                        ApplicationSnackbar.showSnackBar(mRelativeMain,response.body().getResult());
                    }
                }

                mProgressbar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                ApplicationSnackbar.showSnackBar(mRelativeMain,getString(R.string.something_went_wrong));
                mProgressbar.setVisibility(View.GONE);
            }
        });
    }

    private void navigateToMainActivity() {

        Intent mainIntent = new Intent(this,MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
