package base.rechargeapp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import base.rechargeapp.activity.LoginActivity;
import base.rechargeapp.activity.MainActivity;
import base.rechargeapp.R;
import base.rechargeapp.appInterface.RetrofitInterface;
import base.rechargeapp.beans.Login.LoginRequest;
import base.rechargeapp.beans.Register.Response.RegisterResponse;
import base.rechargeapp.beans.Register.Response.Response;
import base.rechargeapp.beans.profile.ProfileRequest;
import base.rechargeapp.utils.AppConstant;
import base.rechargeapp.utils.ApplicationSnackbar;
import base.rechargeapp.utils.NetworkConstant;
import base.rechargeapp.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lin on 13/10/16.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener{

    private View mView ;
    private Activity mActivity ;
    private EditText mFirstName , mSecondName , mEmailAddress , mMobileNumber , mBussinessName,mRfc ,mAddess ;
    private TextView mUpdate ;
    private RelativeLayout mRelativeMain ;
    private Response mUserData ;
    private ProgressBar mProgressBar ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mView = inflater.inflate(R.layout.profilefragment, null);
        mActivity = getActivity() ;

        initViews();
        initValues();
        setListners();
        getPreviousData();

        return mView;
    }

    private void initValues() {
        mUserData = Utils.convertUserDataToObject(mActivity);
    }

    private void getPreviousData() {

        if(mUserData != null){
            mFirstName.setText(mUserData.getFirstName());
            mSecondName.setText(mUserData.getLastName());
            mEmailAddress.setText(mUserData.getEmail());
            mMobileNumber.setText(mUserData.getMobNo());
            mBussinessName.setText(mUserData.getBusinessType());
            mRfc.setText(mUserData.getRfc());
            mAddess.setText(mUserData.getAddress());
        }

    }

    private void setListners() {
        mUpdate.setOnClickListener(this);
    }

    private void initViews() {

        mFirstName = (EditText) mView.findViewById(R.id.edtxt_firstname);
        mSecondName = (EditText) mView.findViewById(R.id.edtxt_secondname);
        mEmailAddress = (EditText) mView.findViewById(R.id.edtxt_email_address);
        mMobileNumber = (EditText) mView.findViewById(R.id.edtxt_mobile);
        mBussinessName = (EditText) mView.findViewById(R.id.edtxt_bussinessname);
        mUpdate = (TextView) mView.findViewById(R.id.txt_update);
        mRelativeMain = (RelativeLayout) mView.findViewById(R.id.rl_main);
        mRfc = (EditText) mView.findViewById(R.id.edtxt_rfc);
        mAddess = (EditText) mView.findViewById(R.id.edtxt_address);
        mProgressBar = (ProgressBar) mView.findViewById(R.id.progress_bar);
    }


    @Override
    public void onClick(View mView) {

        switch (mView.getId()){
            case R.id.txt_update:
                if(checkforValidation()){
                    if(Utils.isConnectedToInternet(mActivity)){
                        hitProfileAPI(createRequest());
                        mProgressBar.setVisibility(View.VISIBLE);
                    }else
                        ApplicationSnackbar.showSnackBar(mRelativeMain,getString(R.string.internet_connection));
                }
                break;
        }
    }


    /**
     * Method to validate all the fields
     * @return true / false - Either all fields are appropriate or not
     */
    private boolean checkforValidation() {

        if(!Utils.isEmptyOrNot(mFirstName)) {
            ApplicationSnackbar.showSnackBar(mRelativeMain,"First name " + getString(R.string.mandatory));
            return false;
        }

        if(!Utils.isEmptyOrNot(mSecondName)) {
            ApplicationSnackbar.showSnackBar(mRelativeMain,"Second name" + getString(R.string.mandatory));
            return false;
        }

        if(!Utils.isEmptyOrNot(mRfc)) {
            ApplicationSnackbar.showSnackBar(mRelativeMain,"RFC " + getString(R.string.mandatory));
            return false;
        }

        if(!Utils.isEmptyOrNot(mAddess)) {
            ApplicationSnackbar.showSnackBar(mRelativeMain,"Address " + getString(R.string.mandatory));
            return false;
        }

        if(!Utils.isEmptyOrNot(mBussinessName)) {
            ApplicationSnackbar.showSnackBar(mRelativeMain,"Bussiness name" + getString(R.string.mandatory));
            return false;
        }

        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        // For setting toolbar title
        ((MainActivity) mActivity).setTitle(mActivity.getString(R.string.profile));
    }

    /**
     * Method to hit API request with POST Method
     */
    private void hitProfileAPI(ProfileRequest profileRequestData) {


        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // prepare call in Retrofit 2.0
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<RegisterResponse> call = retrofitInterface.hitProfileAPI(profileRequestData);

        //asynchronous call

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, retrofit2.Response<RegisterResponse> response) {
                if(response != null && response.body() != null){
                    if(response.body().getErrorCode().equals(AppConstant.SUCCESS_ERROR_CODE)){
                        Utils.setValuesInPreferences(response.body().getResponse(),mActivity);
                    }
                    ApplicationSnackbar.showSnackBar(mRelativeMain,response.body().getResult());
                }

                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                ApplicationSnackbar.showSnackBar(mRelativeMain,getString(R.string.something_went_wrong));
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private ProfileRequest createRequest() {

        ProfileRequest objProfileRequest = new ProfileRequest() ;
        objProfileRequest.setFirst_name(mFirstName.getText().toString());
        objProfileRequest.setLast_name(mSecondName.getText().toString());
        objProfileRequest.setAddress(mAddess.getText().toString());
        objProfileRequest.setBusiness_type(mBussinessName.getText().toString());
        objProfileRequest.setUser_id(mUserData.getId());
        objProfileRequest.setToken_id(mUserData.getToken_id());
        return objProfileRequest;
    }
}
