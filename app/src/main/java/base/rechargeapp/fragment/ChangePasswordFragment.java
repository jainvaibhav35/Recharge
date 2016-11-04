package base.rechargeapp.fragment;

import android.app.Activity;
import android.content.Context;
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

import base.rechargeapp.AppPrefernces.AppPreference;
import base.rechargeapp.R;
import base.rechargeapp.activity.MainActivity;
import base.rechargeapp.appInterface.RetrofitInterface;
import base.rechargeapp.beans.Login.LoginRequest;
import base.rechargeapp.beans.Register.Response.RegisterResponse;
import base.rechargeapp.beans.changePassword.ChangePasswordRequest;
import base.rechargeapp.beans.changePassword.ChangePasswordResponse;
import base.rechargeapp.utils.AppConstant;
import base.rechargeapp.utils.ApplicationSnackbar;
import base.rechargeapp.utils.NetworkConstant;
import base.rechargeapp.utils.Utils;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lin on 13/10/16.
 */

public class ChangePasswordFragment extends Fragment implements View.OnClickListener{

    private View mView ;
    private Activity mActivity ;
    private EditText mOldPassword , mNewPassword , mConfirmNewPassword ;
    private TextView mUpdate ;
    private RelativeLayout mRelativeMain ;
    private ProgressBar mProgressbar ;
    private base.rechargeapp.beans.Register.Response.Response mUserData ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mView = inflater.inflate(R.layout.change_password, null);
        mActivity = getActivity() ;

        initViews();
        setListners();
        getUserCurrentData();

        return mView;
    }

    private void getUserCurrentData() {
        mUserData =  Utils.convertUserDataToObject(mActivity);
    }

    private void setListners() {
        mUpdate.setOnClickListener(this);
    }

    private void initViews() {

        mOldPassword = (EditText) mView.findViewById(R.id.edtxt_old_password);
        mNewPassword = (EditText) mView.findViewById(R.id.edtxt_new_password);
        mConfirmNewPassword = (EditText) mView.findViewById(R.id.edtxt_confirm_new_password);
        mUpdate = (TextView) mView.findViewById(R.id.txt_update);
        mRelativeMain = (RelativeLayout) mView.findViewById(R.id.rl_main);
        mProgressbar = (ProgressBar) mView.findViewById(R.id.progress_bar);
    }


    @Override
    public void onClick(View mView) {

        switch (mView.getId()){
            case R.id.txt_update:
                if(checkforValidation()){
                    if(Utils.isConnectedToInternet(mActivity)){
                        hitChangePasswordAPI(createRequest());
                        mProgressbar.setVisibility(View.VISIBLE);
                    }else
                        ApplicationSnackbar.showSnackBar(mRelativeMain,getString(R.string.internet_connection));
                }
                break;
        }
    }


    /**
     * Method to validate all the fieds
     * @return true / false - Either all fields are appopriate or not
     */
    private boolean checkforValidation() {

        if(!Utils.isEmptyOrNot(mOldPassword)) {
            ApplicationSnackbar.showSnackBar(mRelativeMain,"Old Password " + getString(R.string.mandatory));
            return false;
        }


        if(!Utils.isEmptyOrNot(mNewPassword)) {
            ApplicationSnackbar.showSnackBar(mRelativeMain,"New Password " + getString(R.string.mandatory));
            return false;
        }

        if(!Utils.isEmptyOrNot(mConfirmNewPassword)) {
            ApplicationSnackbar.showSnackBar(mRelativeMain,"Confirm New Password " + getString(R.string.mandatory));
            return false;
        }

        if(!(mNewPassword.getText().toString().equals(mConfirmNewPassword.getText().toString()))){
            ApplicationSnackbar.showSnackBar(mRelativeMain,getString(R.string.password_mismatch));
            return false;
        }

        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        // For setting toolbar title
        ((MainActivity) mActivity).setTitle(mActivity.getString(R.string.change_password));
    }


    /**
     * Method to hit API request with POST Method
     */
    private void hitChangePasswordAPI(ChangePasswordRequest mChangePasswordBean) {


        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // prepare call in Retrofit 2.0
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<ChangePasswordResponse> call = retrofitInterface.hitChangePasswordApi(mChangePasswordBean);

        //asynchronous call

        call.enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                if(response != null && response.body() != null){
                    if(response.body().getErrorCode().equals(AppConstant.SUCCESS_ERROR_CODE)){
                        ApplicationSnackbar.showSnackBar(mRelativeMain,response.body().getResult());
                    }else if(response.body().getErrorCode().equals(AppConstant.FAILURE_ERROR_CODE)){
                        ApplicationSnackbar.showSnackBar(mRelativeMain,response.body().getResult());
                    }
                }

                mProgressbar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                ApplicationSnackbar.showSnackBar(mRelativeMain,getString(R.string.something_went_wrong));
                mProgressbar.setVisibility(View.GONE);
            }
        });
    }


    private ChangePasswordRequest createRequest() {

        ChangePasswordRequest objChangePasswordRequest = new ChangePasswordRequest();
        if (mUserData != null) {
//             objChangePasswordRequest = new ChangePasswordRequest();
            objChangePasswordRequest.setOldPassword(mOldPassword.getText().toString());
            objChangePasswordRequest.setNewPassword(mNewPassword.getText().toString());
            objChangePasswordRequest.setUser_id(mUserData.getId());
            objChangePasswordRequest.setToken_id(mUserData.getToken_id());
        }
        return objChangePasswordRequest;

    }
}
