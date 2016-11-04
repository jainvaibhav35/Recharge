package base.rechargeapp.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import base.rechargeapp.AppPrefernces.AppPreference;
import base.rechargeapp.activity.LoginActivity;
import base.rechargeapp.activity.MainActivity;
import base.rechargeapp.R;
import base.rechargeapp.adapter.ServiceProviderAdapter;
import base.rechargeapp.adapter.ServiceProviderPacksAdapter;
import base.rechargeapp.appInterface.RetrofitInterface;
import base.rechargeapp.beans.Login.LoginRequest;
import base.rechargeapp.beans.Recharge.RechargeRequest;
import base.rechargeapp.beans.Recharge.RechargeResponse.RechargeResponse;
import base.rechargeapp.beans.Register.Response.RegisterResponse;
import base.rechargeapp.beans.Register.Response.Response;
import base.rechargeapp.beans.serviceProvider.ServiceProviderRequest;
import base.rechargeapp.beans.serviceProvider.ServiceProviderResponse.ResponseServiceProvider;
import base.rechargeapp.database.sqllite.CRUD_OperationOfDB;
import base.rechargeapp.database.sqllite.ContractClass;
import base.rechargeapp.utils.AppConstant;
import base.rechargeapp.utils.ApplicationSnackbar;
import base.rechargeapp.utils.NetworkConstant;
import base.rechargeapp.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Organizaton LPTPL
 * Created by Vaibhav Jain on 18/5/16.
 */
public class RechargeFragment extends Fragment implements View.OnClickListener{

    private static final int DIALOGID = 100;
    //    private final String TAG = getActivity().getClass().getSimpleName();
    private View mView;
    private RelativeLayout mRelativeMain;
    private Activity mActivity ;
    private TextView mRupeeSymbol,mBalance,mServiceProvider ,mAmount,mDone  ;
    private EditText mMobileNumber, mConfirmMobileNumber , mEmailAddress ;
    private long milliseconds ;
    private AppPreference mAppPreferences ;
    private Response mUserData ;
    private ResponseServiceProvider mServiceProviderData ;
    private ProgressBar mProgressbar ;
    private int selectedPosition = -1;
    private RelativeLayout mRelativeHeader ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mView = inflater.inflate(R.layout.rechargefragment, null);
        mActivity = getActivity() ;

        initViews();
        initValues();
        setListners();
        setTypeface();

        getDataFromPreferences();

        getServiceProviderAndTheirPacks(createRequest(),false);

//        FirebaseInstanceID.getToken();

        return mView;
    }

    private ServiceProviderRequest createRequest() {
        ServiceProviderRequest objServiceProviderRequest = new ServiceProviderRequest();
        objServiceProviderRequest.setToken_id(mUserData.getToken_id());
        objServiceProviderRequest.setUser_id(mUserData.getId());
        objServiceProviderRequest.setApi_key(AppConstant.API_KEY_VALUE);
        objServiceProviderRequest.setSecret_key(AppConstant.SECRET_KEY_VALUE);
        objServiceProviderRequest.setDevice_type(AppConstant.DEVICE_TYPE);
        objServiceProviderRequest.setDevice_token(FirebaseInstanceId.getInstance().getToken());

        return objServiceProviderRequest ;
    }


    private RechargeRequest createRechargeRequest() {
        RechargeRequest objRequestRequest = new RechargeRequest();
        objRequestRequest.setToken_id(mUserData.getToken_id());
        objRequestRequest.setUser_id(mUserData.getId());
        objRequestRequest.setApi_key(AppConstant.API_KEY_VALUE);
        objRequestRequest.setSecret_key(AppConstant.SECRET_KEY_VALUE);
        objRequestRequest.setMobile_number(mMobileNumber.getText().toString());
        objRequestRequest.setEmail(mEmailAddress.getText().toString());
        objRequestRequest.setAmount(mAmount.getText().toString());
        objRequestRequest.setProvider_id(mServiceProviderData.getResponse().get(selectedPosition).getId());

        return objRequestRequest ;
    }

    private void getServiceProviderAndTheirPacks(ServiceProviderRequest mServiceProvideRequest,final boolean isShowList) {


        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // prepare call in Retrofit 2.0
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<ResponseServiceProvider> call = retrofitInterface.hitGetServiceProviderWithPacks(mServiceProvideRequest);

        //asynchronous call

        call.enqueue(new Callback<ResponseServiceProvider>() {
            @Override
            public void onResponse(Call<ResponseServiceProvider> call, retrofit2.Response<ResponseServiceProvider> response) {
                if(response != null && response.body() != null){
                    if(response.body().getErrorCode().equals(AppConstant.SUCCESS_ERROR_CODE)){

                        mServiceProviderData = response.body() ;

                    }else if(response.body().getErrorCode().equals(AppConstant.FAILURE_ERROR_CODE)){
                        ApplicationSnackbar.showSnackBar(mRelativeMain,response.body().getResult());
                    }
                }

                mProgressbar.setVisibility(View.GONE);

                if(isShowList){
                    showServiceProviders();
                }
            }

            @Override
            public void onFailure(Call<ResponseServiceProvider> call, Throwable t) {
                mProgressbar.setVisibility(View.GONE);
                ApplicationSnackbar.showSnackBar(mRelativeMain,mActivity.getString(R.string.something_went_wrong));
            }

        });

    }
    private void initValues() {

        mAppPreferences = AppPreference.getInstance(mActivity);
    }

    private void getDataFromPreferences() {

        if(!mAppPreferences.getString(AppConstant.USERDATA).equals("")){
            mUserData = new Gson().fromJson(mAppPreferences.getString(AppConstant.USERDATA),Response.class);
            setData();
        }

    }

    private void setData() {
        if(mUserData != null && mUserData.getPrice() != null&& !mUserData.getPrice().equals("")){
            if(Double.parseDouble(mUserData.getPrice()) <= AppConstant.LOW_BALANCE) {
                mRupeeSymbol.setTextColor(getResources().getColor(R.color.low_balance));
                mBalance.setTextColor(getResources().getColor(R.color.low_balance));
                mRelativeHeader.setBackground(mActivity.getResources().getDrawable(R.mipmap.low_balance));
            }else{
                mRupeeSymbol.setTextColor(getResources().getColor(R.color.high_balance));
                mBalance.setTextColor(getResources().getColor(R.color.high_balance));
                mRelativeHeader.setBackground(mActivity.getResources().getDrawable(R.mipmap.high_balance));
            }
            mBalance.setText(mUserData.getPrice());
        }
    }

    private void setTypeface() {
        Typeface font = Typeface.createFromAsset(mActivity.getAssets(), "fonts/"+ mActivity.getString(R.string.icon_font));
        mRupeeSymbol.setTypeface(font);
    }

    private void setListners() {
        mDone.setOnClickListener(this);
        mServiceProvider.setOnClickListener(this);
        mAmount.setOnClickListener(this);
    }

    private void initViews() {

        mRelativeMain = (RelativeLayout) mView.findViewById(R.id.rl_main);
        mRupeeSymbol = (TextView) mView.findViewById(R.id.cheat_icon);
        mBalance = (TextView) mView.findViewById(R.id.txt_balance);
        mServiceProvider = (TextView) mView.findViewById(R.id.edtxt_service_provider);
        mAmount = (TextView) mView.findViewById(R.id.edtxt_amount);
        mMobileNumber = (EditText) mView.findViewById(R.id.edtxt_mobile);
        mConfirmMobileNumber = (EditText) mView.findViewById(R.id.edtxt_confirm_mobile);
        mEmailAddress = (EditText) mView.findViewById(R.id.edtxt_email_address);
        mDone = (TextView) mView.findViewById(R.id.txt_done);
        mProgressbar = (ProgressBar) mView.findViewById(R.id.progress_bar);
        mRelativeHeader = (RelativeLayout) mView.findViewById(R.id.rl_header);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        // For setting toolbar title
        ((MainActivity) mActivity).setTitle(mActivity.getString(R.string.recharge));
    }


    @Override
    public void onClick(View mView) {
        switch (mView.getId()){
            case R.id.txt_done:
                if(checkforValidation()){
                    if(Utils.isConnectedToInternet(mActivity)){
                        // doCallRecharge API
                        insertDatainTable();
//                        updateTransactionStatus();
                        hitRechargeAPI(createRechargeRequest());
                        mProgressbar.setVisibility(View.VISIBLE);

                    }else {
                        ApplicationSnackbar.showSnackBar(mRelativeMain,getString(R.string.internet_connection));
                    }
                }
                break;

            case R.id.edtxt_service_provider:
                showServiceProviders();
                break;

            case R.id.edtxt_amount:
                if(mServiceProvider.getText().equals("") || selectedPosition == -1){
                    ApplicationSnackbar.showSnackBar(mRelativeMain,getString(R.string.please_select_provider_first));
                }else
                showDialogOfServiceProviderPacks();
                break;

        }
    }


    /**
     * Method to validate all the fieds
     * @return true / false - Either all fields are appopriate or not
     */
    private boolean checkforValidation() {

        if(!Utils.isEmptyOrNot(mMobileNumber)) {
            ApplicationSnackbar.showSnackBar(mRelativeMain,"Mobile Number " + getString(R.string.mandatory));
            return false;
        }

        if(!Utils.isEmptyOrNot(mConfirmMobileNumber)) {
            ApplicationSnackbar.showSnackBar(mRelativeMain,getString(R.string.pls_confirm_mobile));
            return false;
        }

        if(!(mMobileNumber.getText().toString().equals(mConfirmMobileNumber.getText().toString()))){
            ApplicationSnackbar.showSnackBar(mRelativeMain,getString(R.string.number_mismatch));
            return false;
        }

        if(!Utils.isEmptyOrNot(mEmailAddress)) {
            ApplicationSnackbar.showSnackBar(mRelativeMain,"EmailAddress " + getString(R.string.mandatory));
            return false;
        }

        if(!Utils.isValidEmail(mEmailAddress.getText().toString())) {
            ApplicationSnackbar.showSnackBar(mRelativeMain, getString(R.string.invalid_email));
            return false;
        }

        if(!Utils.isEmptyOrNot(mServiceProvider)) {
            ApplicationSnackbar.showSnackBar(mRelativeMain,"Servicve provider " + getString(R.string.mandatory));
            return false;
        }

        if(!Utils.isEmptyOrNot(mAmount)) {
            ApplicationSnackbar.showSnackBar(mRelativeMain,"Amount " +getString(R.string.mandatory));
            return false;
        }

        return true;
    }


    /**
     * Method to insert values in DB
     */
    private void insertDatainTable() {

        try {
            milliseconds = System.currentTimeMillis();
            ContentValues values = new ContentValues();
            values.put(ContractClass.FeedEntry.VENDER_ID, mUserData.getId()); // userid of logged in user
            values.put(ContractClass.FeedEntry.TXN_ID, 0);  // By default put txn_id as 0
            values.put(ContractClass.FeedEntry.MOBILE_NUMBER, mMobileNumber.getText().toString());
            values.put(ContractClass.FeedEntry.OPERATOR, mServiceProvider.getText().toString());
            values.put(ContractClass.FeedEntry.AMOUNT, mAmount.getText().toString());
            values.put(ContractClass.FeedEntry.DATE, Utils.getCurrentDateAndTime(AppConstant.DATE_FORMAT_DD_MMM_YYYY));
            values.put(ContractClass.FeedEntry.TIME, Utils.getCurrentDateAndTime(AppConstant.TIME_FORMAT_HH_MM));
            values.put(ContractClass.FeedEntry.STATUS, AppConstant.PENDING);
            values.put(ContractClass.FeedEntry.TIME_IN_MILLI, milliseconds);
            values.put(ContractClass.FeedEntry.IMAGE_URL, "");

            CRUD_OperationOfDB mDatabase = new CRUD_OperationOfDB(getActivity());

            if (mDatabase.InsertIntoDB(ContractClass.FeedEntry.TABLE_NAME, values) >= 0) {
                ApplicationSnackbar.showSnackBar(mRelativeMain, "Success Insert");
            } else {
                //insertDatainTable();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Method to fetch data from the db
     */
    private void getDataFromDB() {
        try {
            String AllData = "";

            CRUD_OperationOfDB mDatabase = new CRUD_OperationOfDB(getActivity());
            Cursor cursor = mDatabase.fetchData(ContractClass.FeedEntry.TABLE_NAME, null, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();
                cursor.getCount();
                while (cursor.moveToNext()) {
                    AllData += cursor.getString(cursor.getColumnIndexOrThrow(ContractClass.FeedEntry.COLUMN_NAME_ENTRY_ID)) + " , ";
                }
            }

//            retrivedData.setText(AllData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showServiceProviders(){
        if(mServiceProviderData != null){

            showDialogOfServiceProvider();

        }else {
            mProgressbar.setVisibility(View.VISIBLE);
            getServiceProviderAndTheirPacks(createRequest(),true);
        }
    }

    private void showDialogOfServiceProvider() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.list_dialog);
        dialog.setCancelable(true);

        TextView tvHeader = (TextView) dialog.findViewById(R.id.tv_header);
        tvHeader.setText(getString(R.string.select_service_provider));

        TextView tvNoElementFound = (TextView) dialog.findViewById(R.id.tvNoElementFound);

        ListView lvList = (ListView) dialog.findViewById(R.id.lv_list);

        lvList.setAdapter(new ServiceProviderAdapter(mActivity, mServiceProviderData.getResponse()));

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mServiceProvider.setText(mServiceProviderData.getResponse().get(position).getName());

                selectedPosition = position ;

                if(mServiceProviderData.getResponse().get(position).getPack().size() == 0){
                    mAmount.setText(getString(R.string.no_packs_available));
                    mAmount.setClickable(false);
                }else {
                    mAmount.setClickable(true);
                    mAmount.setText("");
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void showDialogOfServiceProviderPacks() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.list_dialog);
        dialog.setCancelable(true);

        TextView tvHeader = (TextView) dialog.findViewById(R.id.tv_header);
        tvHeader.setText(getString(R.string.select_pack));

        TextView tvNoElementFound = (TextView) dialog.findViewById(R.id.tvNoElementFound);

        ListView lvList = (ListView) dialog.findViewById(R.id.lv_list);

        lvList.setAdapter(new ServiceProviderPacksAdapter(mActivity, mServiceProviderData.getResponse().get(selectedPosition).getPack()));

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mAmount.setText(mServiceProviderData.getResponse().get(selectedPosition).getPack().get(position).getPackPrice());

                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void updateTransactionStatus(String mStatus , String mTransactioId){

        try {

            ContentValues values = new ContentValues();
            values.put(ContractClass.FeedEntry.TXN_ID, mTransactioId);  // By default put txn_id as 0
            values.put(ContractClass.FeedEntry.STATUS, mStatus.equals("success") ? AppConstant.SUCCESS : AppConstant.SUCCESS);

            CRUD_OperationOfDB mDatabase = new CRUD_OperationOfDB(getActivity());

            if (mDatabase.UpdateDB(ContractClass.FeedEntry.TABLE_NAME,ContractClass.FeedEntry.TIME_IN_MILLI + "=?", new String[]{milliseconds + ""}, values) >= 0) {
                ApplicationSnackbar.showSnackBar(mRelativeMain, "Success Update");
            } else {
                //insertDatainTable();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



/**
     * Method to hit API request with POST Method
     */

    private void hitRechargeAPI(RechargeRequest rechargeRequestData) {


        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // prepare call in Retrofit 2.0
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<RechargeResponse> call = retrofitInterface.hitRechargeApi(rechargeRequestData);

        //asynchronous call

        call.enqueue(new Callback<RechargeResponse>() {
            @Override
            public void onResponse(Call<RechargeResponse> call, retrofit2.Response<RechargeResponse> response) {
                if(response != null && response.body() != null){
                    if(response.body().getErrorCode().equals(AppConstant.SUCCESS_ERROR_CODE)){
                        updateTransactionStatus(response.body().getResponse().getStatus() , response.body().getResponse().getTransctionId());
                        mUserData.setPrice(response.body().getResponse().getUpdatedBalance());
                        Utils.setValuesInPreferences(mUserData,mActivity);
                        getDataFromPreferences();
                    }else if(response.body().getErrorCode().equals(AppConstant.FAILURE_ERROR_CODE)){
                        ApplicationSnackbar.showSnackBar(mRelativeMain,response.body().getResult());
                    }

                    ((MainActivity)mActivity).showRechageStatusFragment(mUserData.getPrice(),response.body().getResponse().getStatus() , response.body().getResponse().getTransctionId());

                }

                mProgressbar.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<RechargeResponse> call, Throwable t) {
                ApplicationSnackbar.showSnackBar(mRelativeMain,getString(R.string.something_went_wrong));
                mProgressbar.setVisibility(View.GONE);
            }

        });
    }

}
