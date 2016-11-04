package base.rechargeapp.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import base.rechargeapp.R;
import base.rechargeapp.utils.CustomTextView;

/**
 * Created by lin on 28/6/16.
 */

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.drawerHolder> {

    private List<String> list = new ArrayList<>();
    private List<String> iconList = new ArrayList<>();
    private Context mContext ;
    private int selectedPosition ;

    public DrawerAdapter(List<String> list,List<String> iconList,Context  mContext,int selectedPosition) {
        this.list = list;
        this.iconList = iconList;
        this.mContext = mContext;
        this.selectedPosition = selectedPosition ;
    }

    public void setUpDateList(List<String> list,List<String> iconList,Context  mContext,int selectedPosition) {
        this.list = list;
        this.iconList = iconList;
        this.mContext = mContext;
        this.selectedPosition = selectedPosition ;
    }


    /**
     * First of all this method is called which inflate
     * the view and create viewHolder with it.
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public drawerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_adapter, parent, false);
        return new drawerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(drawerHolder holder, int position) {

        holder.icon.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/"+ mContext.getString(R.string.icon_font)));
        holder.name.setText(list.get(position));
        holder.icon.setText(iconList.get(position));

        if(position == selectedPosition){
            holder.name.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.icon.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        }   else{
            holder.name.setTextColor(mContext.getResources().getColor(R.color.form_text_color));
            holder.icon.setTextColor(mContext.getResources().getColor(R.color.form_text_color));
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class drawerHolder extends RecyclerView.ViewHolder {

        TextView name, icon;


        public drawerHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.txt_name);
            icon = (CustomTextView) itemView.findViewById(R.id.left_menu_icon);



        }
    }
}
