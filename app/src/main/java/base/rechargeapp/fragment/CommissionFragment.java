package base.rechargeapp.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
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

import base.rechargeapp.R;
import base.rechargeapp.activity.MainActivity;
import base.rechargeapp.adapter.CommissionReportAdapter;
import base.rechargeapp.appInterface.RetrofitInterface;
import base.rechargeapp.beans.commission.RequestCommission;
import base.rechargeapp.beans.commission.Response.ResponseCommission;
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

public class CommissionFragment extends Fragment implements View.OnClickListener {

    Calendar myCalendar = Calendar.getInstance();
    private View mView;
    private Activity mActivity;
    private TextView mNavigatePreviousDate, mNavigateNextDate, mCurrentDate, mNoTransaction, mTotalAmount, mAmountSymbol;
    private RecyclerView mSearchData;
    private CommissionReportAdapter mCommissionAdapter;
    private List<base.rechargeapp.beans.commission.Response.Response> mCommissionList;
    private String mCurrentSelectedDate;
    private RelativeLayout mRelativeMain;
    private base.rechargeapp.beans.Register.Response.Response mUserData;
    private ProgressBar mProgressBar;
    private LinearLayout mLinearBottom;
    DatePickerDialog.OnDateSetListener datePickerDialog = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            setValues(dayOfMonth + " " + getMonthForInt(monthOfYear) + " " + year);
            hitCommision(createRequest(year + "-" + getMonth(monthOfYear) + "-" + dayOfMonth));
            mProgressBar.setVisibility(View.VISIBLE);
        }

    };

    private int getMonth(int monthOfYear) {
        monthOfYear = monthOfYear + 1;
        if ((monthOfYear + "").toString().length() == 1) {
            monthOfYear = Integer.parseInt("0" + monthOfYear);
        }

        return monthOfYear;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.commission, null);
        mActivity = getActivity();

        initViews();
        initValues();
        setTypeface();

        setListner();
        setLayoutManager();
        setValues(Utils.getCurrentDateAndTime(AppConstant.DATE_FORMAT_DD_MMM_YYYY));

        if (Utils.isConnectedToInternet(mActivity)) {

            hitCommision(createRequest(Utils.getCurrentDateAndTime(AppConstant.DATE_FORMAT_YYYY_MM_DD)));
            mProgressBar.setVisibility(View.VISIBLE);

        } else {
            ApplicationSnackbar.showSnackBar(mRelativeMain, mActivity.getString(R.string.internet_connection));
        }


        return mView;
    }

    private void setListner() {
        mNavigatePreviousDate.setOnClickListener(this);
        mNavigateNextDate.setOnClickListener(this);
        mCurrentDate.setOnClickListener(this);
    }

    private void initValues() {

        mCommissionList = new ArrayList<>();
        mUserData = Utils.convertUserDataToObject(mActivity);
    }

    private void setTypeface() {
        Typeface font = Typeface.createFromAsset(mActivity.getAssets(), "fonts/" + mActivity.getString(R.string.icon_font));
        mNavigatePreviousDate.setTypeface(font);
        mNavigateNextDate.setTypeface(font);
        mAmountSymbol.setTypeface(font);
    }


    private void initAdapter() {

        if (mCommissionAdapter == null && mCommissionList != null) {
            mCommissionAdapter = new CommissionReportAdapter(mActivity, mCommissionList);
            mSearchData.setAdapter(mCommissionAdapter);

        } else {
            mCommissionAdapter.setUpDateList(mActivity, mCommissionList);
            mCommissionAdapter.notifyDataSetChanged();
        }

        if (mCommissionList != null && mCommissionList.size() > 0) {
            mNoTransaction.setVisibility(View.GONE);
            getAndSetTotalCommissionAmount();
            mLinearBottom.setVisibility(View.VISIBLE);
        } else {
            mNoTransaction.setText(getString(R.string.no_transaction_history));
            mNoTransaction.setVisibility(View.VISIBLE);
            mLinearBottom.setVisibility(View.GONE);
        }
    }

    private void getAndSetTotalCommissionAmount() {

        float totalAmount = 0;
        for (int i = 0; i < mCommissionList.size(); i++) {
            totalAmount += Float.parseFloat(mCommissionList.get(i).getCommissionRate());
        }
        mTotalAmount.setText(totalAmount + "");
    }

    private void initViews() {

        mNavigatePreviousDate = (TextView) mView.findViewById(R.id.txt_left_arrow);
        mNavigateNextDate = (TextView) mView.findViewById(R.id.txt_right_arrow);
        mSearchData = (RecyclerView) mView.findViewById(R.id.recyclerview_transaction_history);
        mCurrentDate = (TextView) mView.findViewById(R.id.txt_date);
        mNoTransaction = (TextView) mView.findViewById(R.id.txt_no_transaction);
        mRelativeMain = (RelativeLayout) mView.findViewById(R.id.rl_main);
        mProgressBar = (ProgressBar) mView.findViewById(R.id.progress_bar);
        mTotalAmount = (TextView) mView.findViewById(R.id.txt_total_profit_amount);
        mAmountSymbol = (TextView) mView.findViewById(R.id.txt_amount_cheat);
        mLinearBottom = (LinearLayout) mView.findViewById(R.id.ll_bottom_layout);
    }

    @Override
    public void onClick(View mView) {

        switch (mView.getId()) {
            case R.id.txt_left_arrow:
                manipulatePreviousDateData();
                break;
            case R.id.txt_right_arrow:
                manipulateNextDateData();
                break;

            case R.id.txt_date:

                DatePickerDialog dialog = new DatePickerDialog(getActivity(), datePickerDialog, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                Calendar cal = Calendar.getInstance();

                dialog.getDatePicker().setMinDate(new Date(System.currentTimeMillis() - ((long) 30 * 24 * 60 * 60 * 1000)).getTime());
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.show();

                break;
        }
    }

    private void manipulatePreviousDateData() {

        // Current Selected Date
        Date currentSelectedDate = Utils.convertStringToDate(mCurrentSelectedDate, AppConstant.DATE_FORMAT_DD_MMM_YYYY);

        // One day pevious date from current selected date
        Date previousDate = new Date(currentSelectedDate.getTime() - 24 * 60 * 60 * 1000);

        // Check Either
        if ((System.currentTimeMillis() - previousDate.getTime()) > (((long) 31 * 24 * 60 * 60 * 1000))) {
            ApplicationSnackbar.showSnackBar(mRelativeMain, mActivity.getString(R.string.cannot_navigate_before_30_days));
        } else {
            setValues(Utils.convertDateToString(previousDate, AppConstant.DATE_FORMAT_DD_MMM_YYYY));
            hitCommision(createRequest(Utils.convertDateToString(previousDate, AppConstant.DATE_FORMAT_YYYY_MM_DD)));
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    private void manipulateNextDateData() {

        // Current Selected Date
        Date currentSelectedDate = Utils.convertStringToDate(mCurrentSelectedDate, AppConstant.DATE_FORMAT_DD_MMM_YYYY);

        // One day pevious date from current selected date
        Date nextDate = new Date(currentSelectedDate.getTime() + ((long) 24 * 60 * 60 * 1000));

        // Check Either
        if ((System.currentTimeMillis() < nextDate.getTime())) {
            ApplicationSnackbar.showSnackBar(mRelativeMain, mActivity.getString(R.string.cannot_navigate_after_today));
        } else {
            setValues(Utils.convertDateToString(nextDate, AppConstant.DATE_FORMAT_DD_MMM_YYYY));
            hitCommision(createRequest(Utils.convertDateToString(nextDate, AppConstant.DATE_FORMAT_YYYY_MM_DD)));
            mProgressBar.setVisibility(View.VISIBLE);
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

    private void setValues(String date) {
        mCurrentDate.setText(date);
        mCurrentSelectedDate = date;
    }

    @Override
    public void onResume() {
        super.onResume();


        // For setting toolbar title
        ((MainActivity) mActivity).setTitle(mActivity.getString(R.string.commission_report));
    }

    /**
     * This Method is used to get Month Name from month number
     *
     * @param m - Month Number
     * @return -  Month Name
     */
    private String getMonthForInt(int m) {
        String month = "invalid";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (m >= 0 && m <= 11) {
            month = months[m];
        }
        return month.substring(0, 3);
    }

    /**
     * Method to hit API request with POST Method
     */
    private void hitCommision(RequestCommission mRequestCommission) {


        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // prepare call in Retrofit 2.0
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<ResponseCommission> call = retrofitInterface.hitGetCommission(mRequestCommission);

        //asynchronous call

        call.enqueue(new Callback<ResponseCommission>() {
            @Override
            public void onResponse(Call<ResponseCommission> call, Response<ResponseCommission> response) {
                if (response != null && response.body() != null) {
                    if (response.body().getErrorCode().equals(AppConstant.SUCCESS_ERROR_CODE)) {
                        mCommissionList = response.body().getResponse();
                    } else if (response.body().getErrorCode().equals(AppConstant.FAILURE_ERROR_CODE)) {
                        ApplicationSnackbar.showSnackBar(mRelativeMain, response.body().getResult());
                    }
                }
                ApplicationSnackbar.showSnackBar(mRelativeMain, response.body().getResult());
                mProgressBar.setVisibility(View.GONE);
                initAdapter();
            }

            @Override
            public void onFailure(Call<ResponseCommission> call, Throwable t) {
                ApplicationSnackbar.showSnackBar(mRelativeMain, getString(R.string.something_went_wrong));
//                getTransactioData(Utils.getCurrentDateAndTime(AppConstant.DATE_FORMAT_DD_MMM_YYYY));
                mProgressBar.setVisibility(View.GONE);

            }
        });
    }

    private RequestCommission createRequest(String date) {

        RequestCommission objRequestCommission = new RequestCommission();
        objRequestCommission.setToken_id(mUserData.getToken_id());
        objRequestCommission.setUser_id(mUserData.getId());
        objRequestCommission.setDate(date);

        return objRequestCommission;
    }
}
