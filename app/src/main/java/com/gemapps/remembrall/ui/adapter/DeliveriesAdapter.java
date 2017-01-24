package com.gemapps.remembrall.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gemapps.remembrall.R;
import com.gemapps.remembrall.ui.model.Delivery;
import com.gemapps.remembrall.util.DateUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by edu on 1/23/17.
 */

public class DeliveriesAdapter extends BaseAdapter {

    private List<Delivery> mDeliveries;
    private LayoutInflater mInflater;

    public DeliveriesAdapter(Context context, List<Delivery> deliveries) {
        mInflater = LayoutInflater.from(context);
        mDeliveries = deliveries;
    }

    @Override
    public int getCount() {
        return mDeliveries == null ? 0 : mDeliveries.size();
    }

    @Override
    public Delivery getItem(int position) {
        return mDeliveries == null ? null : mDeliveries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DeliveryHolder holder = null;
        if(convertView == null) {
            convertView = mInflater
                    .inflate(R.layout.delivery_item_list, parent, false);
            holder = new DeliveryHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (DeliveryHolder) convertView.getTag();
        }

        Delivery delivery = getItem(position);

        holder.mStartDay.setText(DateUtil.formatDateFromTs(delivery.getAlarm().getStartDate()));
        holder.mEndDay.setText(DateUtil.formatDateFromTs(delivery.getAlarm().getEndDate()));
        holder.mPrice.setText(String.valueOf(delivery.getPrice()));

        return convertView;
    }

    public static class DeliveryHolder {

        @BindView(R.id.start_day_text)
        TextView mStartDay;
        @BindView(R.id.end_day_text)
        TextView mEndDay;
        @BindView(R.id.price_text)
        TextView mPrice;

        public DeliveryHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
