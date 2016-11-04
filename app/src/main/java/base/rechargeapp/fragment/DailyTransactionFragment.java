package base.rechargeapp.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import base.rechargeapp.activity.MainActivity;
import base.rechargeapp.R;
import base.rechargeapp.adapter.SearchTransactionAdapter;
import base.rechargeapp.appInterface.RetrofitInterface;
import base.rechargeapp.beans.TransactionBean;
import base.rechargeapp.beans.transaction_list.RequestTransactionList;
import base.rechargeapp.beans.transaction_list.response.ResponseTransactionList;
import base.rechargeapp.database.sqllite.CRUD_OperationOfDB;
import base.rechargeapp.database.sqllite.ContractClass;
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
 * Created by lin on 13/10/16.
 */

public class DailyTransactionFragment extends Fragment implements View.OnClickListener {

    private View mView ;
    private Activity mActivity ;
    private TextView mNavigatePreviousDate ,mNavigateNextDate ,mCurrentDate,mNoTransaction;
    private RecyclerView mSearchData ;
    private SearchTransactionAdapter mSearchTransactionAdapter ;
    private ArrayList<TransactionBean> mTransactionDataList ;
    private ArrayList<String> mTransactionIdList ;
    private String mCurrentSelectedDate ;
    private RelativeLayout mRelativeMain ;
    private base.rechargeapp.beans.Register.Response.Response mUserData ;
    private ProgressBar mProgressBar ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.daily_transaction, null);
        mActivity = getActivity() ;
        
        initViews();
        initValues();
        setTypeface();

        setListner();
        setLayoutManager();




        return mView;
    }

    private void setListner() {
        mNavigatePreviousDate.setOnClickListener(this);
        mNavigateNextDate.setOnClickListener(this);
        mCurrentDate.setOnClickListener(this);
    }

    private void initValues() {

        mTransactionDataList = new ArrayList<>();
        mTransactionIdList = new ArrayList<>();
        mUserData = Utils.convertUserDataToObject(mActivity);
    }

    private void setTypeface() {
        Typeface font = Typeface.createFromAsset(mActivity.getAssets(), "fonts/"+ mActivity.getString(R.string.icon_font));
        mNavigatePreviousDate.setTypeface(font);
        mNavigateNextDate.setTypeface(font);
    }

    /**
     * Method to fetch data from the db
     */

    private void getTransactioData(String date) {

            try {

                CRUD_OperationOfDB mDatabase = new CRUD_OperationOfDB(getActivity());
                Cursor cursor = mDatabase.fetchData(ContractClass.FeedEntry.TABLE_NAME,null,ContractClass.FeedEntry.DATE + "=?", new String[]{date}, ContractClass.FeedEntry.DATE+" DESC");

                if(mTransactionDataList != null && mTransactionDataList.size() > 0)
                    mTransactionDataList.clear();

                if (cursor != null) {
                    cursor.moveToFirst();
                    cursor.getCount();
                    do {
                        if(cursor.getCount() > 0) {
                            TransactionBean objTransactionBean = new TransactionBean();
                            objTransactionBean.setmMobileNumber(cursor.getString(cursor.getColumnIndexOrThrow(ContractClass.FeedEntry.MOBILE_NUMBER)));
                            objTransactionBean.setmOperatorName(cursor.getString(cursor.getColumnIndexOrThrow(ContractClass.FeedEntry.OPERATOR)));
                            objTransactionBean.setmAmount(cursor.getString(cursor.getColumnIndexOrThrow(ContractClass.FeedEntry.AMOUNT)));
                            objTransactionBean.setmTransactionId(cursor.getString(cursor.getColumnIndexOrThrow(ContractClass.FeedEntry.TXN_ID)));
                            objTransactionBean.setmStatus(cursor.getString(cursor.getColumnIndexOrThrow(ContractClass.FeedEntry.STATUS)));
                            objTransactionBean.setmDate(cursor.getString(cursor.getColumnIndexOrThrow(ContractClass.FeedEntry.DATE)));
                            objTransactionBean.setmTime(cursor.getString(cursor.getColumnIndexOrThrow(ContractClass.FeedEntry.TIME)));

                            mTransactionDataList.add(objTransactionBean);
                        }
                    }while (cursor.moveToNext());
                }

                initAdapter();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    private void initAdapter() {
        if (mSearchTransactionAdapter == null) {
            mSearchTransactionAdapter = new SearchTransactionAdapter(mActivity,mTransactionDataList);
            mSearchData.setAdapter(mSearchTransactionAdapter);

        } else {
            mSearchTransactionAdapter.setUpDateList(mActivity,mTransactionDataList);
            mSearchTransactionAdapter.notifyDataSetChanged();
        }

        if(mTransactionDataList != null && mTransactionDataList.size() > 0)
            mNoTransaction.setVisibility(View.GONE);
        else {
            mNoTransaction.setText(getString(R.string.no_transaction_history));
            mNoTransaction.setVisibility(View.VISIBLE);
        }
        }

    private void initAdapter(ArrayList<TransactionBean> mTransactionDataList) {
        if (mSearchTransactionAdapter == null) {
            mSearchTransactionAdapter = new SearchTransactionAdapter(mActivity,mTransactionDataList);
            mSearchData.setAdapter(mSearchTransactionAdapter);

        } else {
            mSearchTransactionAdapter.setUpDateList(mActivity,mTransactionDataList);
            mSearchTransactionAdapter.notifyDataSetChanged();
        }

        if(mTransactionDataList != null && mTransactionDataList.size() > 0)
            mNoTransaction.setVisibility(View.GONE);
        else {
                mNoTransaction.setText(getString(R.string.no_search_data));
                mNoTransaction.setVisibility(View.VISIBLE);
        }
        }

    private void initViews() {

        mNavigatePreviousDate = (TextView) mView.findViewById(R.id.txt_left_arrow);
        mNavigateNextDate = (TextView) mView.findViewById(R.id.txt_right_arrow);
        mSearchData = (RecyclerView) mView.findViewById(R.id.recyclerview_transaction_history);
        mCurrentDate = (TextView) mView.findViewById(R.id.txt_date);
        mNoTransaction = (TextView) mView.findViewById(R.id.txt_no_transaction);
        mRelativeMain = (RelativeLayout) mView.findViewById(R.id.rl_main);
        mProgressBar = (ProgressBar) mView.findViewById(R.id.progress_bar);
    }

    @Override
    public void onClick(View mView) {

        switch (mView.getId()){
            case R.id.txt_left_arrow:
                manipulatePreviousDateData();
                break;
            case R.id.txt_right_arrow:
                manipulateNextDateData();
                break;

            case R.id.txt_date:

                DatePickerDialog dialog =  new DatePickerDialog(getActivity(), datePickerDialog, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                Calendar cal = Calendar.getInstance();

                dialog.getDatePicker().setMinDate(new Date(System.currentTimeMillis() - ((long) 30*24*60*60*1000)).getTime());
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
//                new Date().getDate();
                dialog.show();

                break;
        }
    }

    private void manipulatePreviousDateData() {

        // Current Selected Date
       Date currentSelectedDate = Utils.convertStringToDate(mCurrentSelectedDate,AppConstant.DATE_FORMAT_DD_MMM_YYYY);

        // One day pevious date from current selected date
        Date previousDate = new Date(currentSelectedDate.getTime() - 24*60*60*1000);

        // Check Either
        if((System.currentTimeMillis() - previousDate.getTime()) > (((long) 31*24*60*60*1000))){
            ApplicationSnackbar.showSnackBar(mRelativeMain,mActivity.getString(R.string.cannot_navigate_before_30_days));
        }else{
            setValues(Utils.convertDateToString(previousDate,AppConstant.DATE_FORMAT_DD_MMM_YYYY));
            getTransactioData(Utils.convertDateToString(previousDate,AppConstant.DATE_FORMAT_DD_MMM_YYYY));
            initAdapter();
        }
    }

    private void manipulateNextDateData() {

        // Current Selected Date
        Date currentSelectedDate = Utils.convertStringToDate(mCurrentSelectedDate,AppConstant.DATE_FORMAT_DD_MMM_YYYY);

        // One day pevious date from current selected date
        Date nextDate = new Date(currentSelectedDate.getTime() + ((long)24*60*60*1000));

        // Check Either
        if((System.currentTimeMillis()  <  nextDate.getTime())){
            ApplicationSnackbar.showSnackBar(mRelativeMain,mActivity.getString(R.string.cannot_navigate_after_today));
        }else{
            setValues(Utils.convertDateToString(nextDate,AppConstant.DATE_FORMAT_DD_MMM_YYYY));
            getTransactioData(Utils.convertDateToString(nextDate,AppConstant.DATE_FORMAT_DD_MMM_YYYY));
            initAdapter();
        }
    }

    /**
     * Set Layout Manager for the recycler View
     */
    private void setLayoutManager() {

        if (mSearchData != null) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            mSearchData.setLayoutManager(layoutManager);
        }
    }

    private void setValues(String date){
        mCurrentDate.setText(date);
        mCurrentSelectedDate = date ;
    }

    @Override
    public void onResume() {
        super.onResume();
        setValues(Utils.getCurrentDateAndTime(AppConstant.DATE_FORMAT_DD_MMM_YYYY));

        if(Utils.isConnectedToInternet(mActivity)){

            hitTransactionListAPI(createRequest());
            mProgressBar.setVisibility(View.VISIBLE);

        }else{
            getTransactioData(Utils.getCurrentDateAndTime(AppConstant.DATE_FORMAT_DD_MMM_YYYY));
        }
//
//        initAdapter();
        // For setting toolbar title
        ((MainActivity) mActivity).setTitle(mActivity.getString(R.string.daily_transaction));
    }

    Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener datePickerDialog = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                setValues(dayOfMonth + " " + getMonthForInt(monthOfYear) + " " + year);
                getTransactioData(dayOfMonth + " " + getMonthForInt(monthOfYear) + " " + year);
                initAdapter();

            }

        };

    /**
     * This Method is used to get Month Name from month number
     * @param m   - Month Number
     * @return  -  Month Name
     */
    private  String getMonthForInt(int m) {
        String month = "invalid";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (m >= 0 && m <= 11 ) {
            month = months[m];
        }
        return month.substring(0,3);
    }


    /**
     * Method to hit API request with POST Method
     */
    private void hitTransactionListAPI(RequestTransactionList transactionListData) {


        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // prepare call in Retrofit 2.0
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<ResponseTransactionList> call = retrofitInterface.hitTransactionList(transactionListData);

        //asynchronous call

        call.enqueue(new Callback<ResponseTransactionList>() {
            @Override
            public void onResponse(Call<ResponseTransactionList> call, Response<ResponseTransactionList> response) {
                if(response != null && response.body() != null){
                    if(response.body().getErrorCode().equals(AppConstant.SUCCESS_ERROR_CODE)){

                        removeDuplicateDataFromLocalDB(response.body().getResponse());
//                        Utils.setValuesInPreferences(response.body().getResponse(),mActivity);
//                        navigateToMainActivity();

                    }else if(response.body().getErrorCode().equals(AppConstant.FAILURE_ERROR_CODE)){
                        ApplicationSnackbar.showSnackBar(mRelativeMain,response.body().getResult());
                    }
                }

                getTransactioData(Utils.getCurrentDateAndTime(AppConstant.DATE_FORMAT_DD_MMM_YYYY));
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseTransactionList> call, Throwable t) {
                ApplicationSnackbar.showSnackBar(mRelativeMain,getString(R.string.something_went_wrong));
                getTransactioData(Utils.getCurrentDateAndTime(AppConstant.DATE_FORMAT_DD_MMM_YYYY));
                mProgressBar.setVisibility(View.GONE);

            }
        });
    }

    private void removeDuplicateDataFromLocalDB(List<base.rechargeapp.beans.transaction_list.response.Response> responseList) {

        getTransactioIdlist();

        if(mTransactionIdList != null && mTransactionIdList.size() > 0 && responseList != null){
            for(int i = 0 ;i < responseList.size() ; i++){
                if(mTransactionIdList.contains(responseList.get(i).getTransction_id())){
                    // update image
                    updateTransactionStatus(responseList.get(i).getTransction_id(),responseList.get(i).getImage());
                }else{
                    // enter value
                    insertDatainTable(responseList.get(i));
                }
            }
        }else{
            //enter value
            for(int i = 0 ;i < responseList.size() ; i++) {
                insertDatainTable(responseList.get(i));
            }
        }

    }


    /**
     * Method to fetch data from the db
     */

    private void getTransactioIdlist() {

        try {

            CRUD_OperationOfDB mDatabase = new CRUD_OperationOfDB(getActivity());
            Cursor cursor = mDatabase.fetchData(ContractClass.FeedEntry.TABLE_NAME, null, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();
                cursor.getCount();
                do {
                    mTransactionIdList.add(cursor.getString(cursor.getColumnIndexOrThrow(ContractClass.FeedEntry.TXN_ID)));
                }while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to insert values in DB
     */
    private void insertDatainTable(base.rechargeapp.beans.transaction_list.response.Response mResponseData) {

        try {
            long milliseconds = System.currentTimeMillis();
            ContentValues values = new ContentValues();
            values.put(ContractClass.FeedEntry.VENDER_ID, mUserData.getId()); // userid of logged in user
            values.put(ContractClass.FeedEntry.TXN_ID, mResponseData.getTransction_id());  // By default put txn_id as 0
            values.put(ContractClass.FeedEntry.MOBILE_NUMBER, mResponseData.getMobile_no());
            values.put(ContractClass.FeedEntry.OPERATOR, mResponseData.getName());
            values.put(ContractClass.FeedEntry.AMOUNT, mResponseData.getRecharge_amount());
            values.put(ContractClass.FeedEntry.DATE, Utils.convertDateToString(Utils.convertStringToDate(mResponseData.getCreated_date(),AppConstant.DATE_TIME_FORMAT_FROM_SERVER),AppConstant.DATE_FORMAT_DD_MMM_YYYY));
            values.put(ContractClass.FeedEntry.TIME, Utils.convertDateToString(Utils.convertStringToDate(mResponseData.getCreated_date(),AppConstant.DATE_TIME_FORMAT_FROM_SERVER),AppConstant.TIME_FORMAT_HH_MM));
            values.put(ContractClass.FeedEntry.STATUS, mResponseData.getStatus());
            values.put(ContractClass.FeedEntry.TIME_IN_MILLI, milliseconds);
            values.put(ContractClass.FeedEntry.IMAGE_URL, mResponseData.getImage());

            CRUD_OperationOfDB mDatabase = new CRUD_OperationOfDB(getActivity());

            if (mDatabase.InsertIntoDB(ContractClass.FeedEntry.TABLE_NAME, values) >= 0) {
//                ApplicationSnackbar.showSnackBar(mRelativeMain, "Success Insert");
            } else {
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updateTransactionStatus(String mTransactioId , String mImage){

        try {

            ContentValues values = new ContentValues();
            values.put(ContractClass.FeedEntry.IMAGE_URL, mImage);  // By default put txn_id as 0

            CRUD_OperationOfDB mDatabase = new CRUD_OperationOfDB(getActivity());

            if (mDatabase.UpdateDB(ContractClass.FeedEntry.TABLE_NAME,ContractClass.FeedEntry.TXN_ID + "=?", new String[]{mTransactioId + ""}, values) >= 0) {
                ApplicationSnackbar.showSnackBar(mRelativeMain, "Success Update");
            } else {
                //insertDatainTable();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private RequestTransactionList createRequest() {

        RequestTransactionList objRequestTransactionList = new RequestTransactionList();
        objRequestTransactionList.setToken_id(mUserData.getToken_id());
        objRequestTransactionList.setUser_id(mUserData.getId());

        return objRequestTransactionList ;
    }
}
