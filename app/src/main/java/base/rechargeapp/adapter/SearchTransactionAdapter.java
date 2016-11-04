package base.rechargeapp.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import base.rechargeapp.R;
import base.rechargeapp.beans.TransactionBean;
import base.rechargeapp.utils.NetworkConstant;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lin on 17/10/16.
 */

public class SearchTransactionAdapter extends RecyclerView.Adapter<SearchTransactionAdapter.CustomViewHolder> {


    private ArrayList<TransactionBean> mTransactionList ;
    private Context mContext ;


    public SearchTransactionAdapter(Context mContext,ArrayList<TransactionBean> mTransactionList){

        this.mTransactionList = mTransactionList ;
        this.mContext = mContext ;

    }

    public void setUpDateList(Context mContext ,ArrayList<TransactionBean> mTransactionList){

        this.mTransactionList = mTransactionList ;
        this.mContext = mContext ;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_transaction_adapter, parent, false);

        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        TransactionBean selectedPosition = mTransactionList.get(position);
        holder.mMobileNumber.setText(selectedPosition.getmMobileNumber());
        holder.mOperatorName.setText(selectedPosition.getmOperatorName());
        holder.mAmount.setText(selectedPosition.getmAmount());
        holder.mTransactionId.setText(mContext.getString(R.string.transaction_code) + " " + selectedPosition.getmTransactionId());
        holder.mStatus.setText(selectedPosition.getmStatus());
        holder.mDateTime.setText(selectedPosition.getmDate() + " | " + selectedPosition.getmTime());

        holder.mRupee.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/"+ mContext.getString(R.string.icon_font)));

        Glide.with(mContext)
                .load(NetworkConstant.IMAGE_BASE_URL + "Vodafone_Logo.jpg")
                .dontAnimate()
                .into(holder.operaatorimage);


    }


    @Override
    public int getItemCount() {
        return mTransactionList.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder{

        private TextView mMobileNumber,mRupee,mOperatorName,mAmount,mStatus,mTransactionId,mDateTime ;
        private CircleImageView operaatorimage ;

        public CustomViewHolder(View itemView) {
            super(itemView);

            mMobileNumber = (TextView) itemView.findViewById(R.id.txt_phone_number);
            mOperatorName = (TextView) itemView.findViewById(R.id.txt_operator_name);
            mAmount = (TextView) itemView.findViewById(R.id.txt_amount);
            mStatus = (TextView) itemView.findViewById(R.id.txt_status);
            mTransactionId = (TextView) itemView.findViewById(R.id.txt_transaction_code);
            mDateTime = (TextView) itemView.findViewById(R.id.txt_transaction_date);
            mRupee = (TextView) itemView.findViewById(R.id.rupee_icon);
            operaatorimage = (CircleImageView) itemView.findViewById(R.id.imgv_operator);
        }
    }
}
