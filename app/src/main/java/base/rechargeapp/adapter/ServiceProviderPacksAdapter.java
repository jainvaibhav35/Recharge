package base.rechargeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import base.rechargeapp.R;
import base.rechargeapp.beans.serviceProvider.ServiceProviderResponse.Pack;
import base.rechargeapp.beans.serviceProvider.ServiceProviderResponse.Response;

/**
 * Created by lin on 24/10/16.
 */

public class ServiceProviderPacksAdapter extends BaseAdapter {

        private Context mContext;
        private List<Pack> elements;
        private ViewHolderItem holder;

        public ServiceProviderPacksAdapter(Context mContext, List<Pack> elements) {
            this.mContext = mContext;
            this.elements = elements;
        }

        @Override
        public int getCount() {
            return elements.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.spinner_row, parent, false);
                holder = new ViewHolderItem(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolderItem) convertView.getTag();
            }

            holder.tvElement.setText(elements.get(position).getPackPrice() + "");

            return convertView;
        }

        public static class ViewHolderItem {
            TextView tvElement;

            ViewHolderItem(View base) {
                tvElement = (TextView) base.findViewById(R.id.tv_element);

            }
        }
    }

