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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import base.rechargeapp.AppPrefernces.AppPreference;
import base.rechargeapp.R;
import base.rechargeapp.activity.MainActivity;
import base.rechargeapp.adapter.ServiceProviderAdapter;
import base.rechargeapp.adapter.ServiceProviderPacksAdapter;
import base.rechargeapp.appInterface.RetrofitInterface;
import base.rechargeapp.beans.Recharge.RechargeRequest;
import base.rechargeapp.beans.Recharge.RechargeResponse.RechargeResponse;
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
public class RechargeStatusFragment extends Fragment implements View.OnClickListener{

    private View mView;
    private RelativeLayout mRelativeMain;
    private Activity mActivity ;
    private TextView mRupeeSymbol,mBalance,mDone,mStatus,mTransactionId  ;
    private RelativeLayout mRelativeHeader ;
    private ImageView mRechargeImageStatus ;

    private String mCurrentBalance , mRechargeStatus , mRechargeTransactionId ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mView = inflater.inflate(R.layout.recharge_status_fragment, null);
        mActivity = getActivity() ;

        initViews();
        setListners();
        setTypeface();
        setFragmentData(mCurrentBalance,mRechargeStatus,mRechargeTransactionId);
        return mView;
    }

    public RechargeStatusFragment(String mBalance, String mStatus , String mTransactionId) {
        mCurrentBalance = mBalance ;
        mRechargeStatus = mStatus;
        mRechargeTransactionId = mTransactionId ;

    }

//    public void setData(String mBalance, String mStatus , String mTransactionId){
//        setFragmentData(mBalance,mStatus,mTransactionId);
//    }


    private void setFragmentData(String mCurrentBalance,String mRechargeStatus,String mRechargeTransactionId) {
        if(!mCurrentBalance.equals("")){
            if(Double.parseDouble(mCurrentBalance) <= AppConstant.LOW_BALANCE) {
                mRupeeSymbol.setTextColor(mActivity.getResources().getColor(R.color.low_balance));
                mBalance.setTextColor(mActivity.getResources().getColor(R.color.low_balance));
                mRelativeHeader.setBackground(mActivity.getResources().getDrawable(R.mipmap.low_balance));
            }else{
                mRupeeSymbol.setTextColor(mActivity.getResources().getColor(R.color.high_balance));
                mBalance.setTextColor(mActivity.getResources().getColor(R.color.high_balance));
                mRelativeHeader.setBackground(mActivity.getResources().getDrawable(R.mipmap.high_balance));
            }
            mBalance.setText(mCurrentBalance);
        }

        if(mRechargeStatus.equalsIgnoreCase(AppConstant.SUCCESS)){
            mRechargeImageStatus.setImageResource(R.mipmap.successful);
            mStatus.setText(mActivity.getString(R.string.recharge_success));
        }else{
            mRechargeImageStatus.setImageResource(R.mipmap.unsuccessfu);
            mStatus.setText(mActivity.getString(R.string.recharge_fail));
        }

        mTransactionId.setText(mActivity.getString(R.string.transaction_code) + " "+mRechargeTransactionId);
    }

    private void setTypeface() {
        Typeface font = Typeface.createFromAsset(mActivity.getAssets(), "fonts/"+ mActivity.getString(R.string.icon_font));
        mRupeeSymbol.setTypeface(font);
    }

    private void setListners() {
        mDone.setOnClickListener(this);
    }

    private void initViews() {

        mRelativeMain = (RelativeLayout) mView.findViewById(R.id.rl_main);
        mRupeeSymbol = (TextView) mView.findViewById(R.id.cheat_icon);
        mBalance = (TextView) mView.findViewById(R.id.txt_balance);
        mDone = (TextView) mView.findViewById(R.id.txt_ok);
        mRelativeHeader = (RelativeLayout) mView.findViewById(R.id.rl_header);
        mStatus = (TextView) mView.findViewById(R.id.txt_status);
        mTransactionId = (TextView) mView.findViewById(R.id.txt_transaction_code);
        mRechargeImageStatus = (ImageView) mView.findViewById(R.id.imgv_recharge_status);
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
            case R.id.txt_ok:
                ((MainActivity)mActivity).onBackPressed();
                break;

        }
    }
}
