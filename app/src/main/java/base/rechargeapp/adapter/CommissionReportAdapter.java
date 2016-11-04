package base.rechargeapp.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import base.rechargeapp.R;
import base.rechargeapp.beans.commission.Response.Response;
import base.rechargeapp.utils.CustomTextView;

/**
 * Created by lin on 2/11/16.
 */

public class CommissionReportAdapter extends RecyclerView.Adapter<CommissionReportAdapter.viewHolder> {

    Context mContext;
    List<Response> mCommissionList;

    public CommissionReportAdapter(Context mContext, List<Response> mCommissionList) {
        this.mContext = mContext;
        this.mCommissionList = mCommissionList;
    }

    public void setUpDateList(Context mContext, List<Response> mCommissionList) {
        this.mContext = mContext;
        this.mCommissionList = mCommissionList;
    }

    @Override
    public CommissionReportAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.commision_adapter, parent, false);

        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommissionReportAdapter.viewHolder holder, int position) {

        holder.mOperatorName.setText(mCommissionList.get(position).getName());
        holder.mAmount.setText(mCommissionList.get(position).getTotalRecharge());
        holder.mCommissionPercent.setText(mContext.getString(R.string.commission) + " " + mCommissionList.get(position).getCommission() + "%");
        holder.mAmountSymbol.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/" + mContext.getString(R.string.icon_font)));
        holder.mCircle.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/" + mContext.getString(R.string.icon_font)));
        holder.mCommisionAmount.setText(mContext.getString(R.string.dollar) +mCommissionList.get(position).getCommissionRate());
    }

    @Override
    public int getItemCount() {
        return mCommissionList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {

        private TextView mOperatorName, mAmount, mCommissionPercent, mAmountSymbol, mCircle , mCommisionAmount;

        public viewHolder(View itemView) {
            super(itemView);

            mOperatorName = (TextView) itemView.findViewById(R.id.txt_operator_name);
            mAmount = (TextView) itemView.findViewById(R.id.txt_amount);
            mCommissionPercent = (TextView) itemView.findViewById(R.id.commision_per);
            mAmountSymbol = (CustomTextView) itemView.findViewById(R.id.txt_cheat);
            mCircle = (CustomTextView) itemView.findViewById(R.id.txt_circle);
            mCommisionAmount = (TextView) itemView.findViewById(R.id.commision_amount);

        }
    }

}
