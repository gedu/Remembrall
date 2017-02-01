package com.gemapps.remembrall.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gemapps.remembrall.R;
import com.gemapps.remembrall.ui.model.Remembrall;
import com.gemapps.remembrall.util.DateUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by edu on 8/25/16.
 */
public class RecyclerViewRemembrallAdapter
        extends RealmRecyclerViewAdapter<Remembrall, RecyclerViewRemembrallAdapter.RemembrallViewHolder> {

    public interface RemembrallItemsListener {
        void onItemClicked(int position);
        void onDeleteClicked(int position);
    }

    private static final String TAG = "RecyclerViewRemembrallA";

    private RemembrallItemsListener mListener;

    public RecyclerViewRemembrallAdapter(Context context, RemembrallItemsListener listener,
                                         OrderedRealmCollection<Remembrall> data) {
        super(context, data, true);
        Log.d(TAG, "RecyclerViewRemembrallAdapter: "+data.size());
        mListener = listener;
    }

    @Override
    public RemembrallViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.remembrall_item_list, parent, false);
        return new RemembrallViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RemembrallViewHolder holder, int position) {

        try {
            setupViewUsing(holder, getContentAt(position));
        }catch (NullPointerException e){
            Log.w(TAG, "Failed to get OBJ at position: "+position, e);
        }
    }

    private Remembrall getContentAt(int position) throws NullPointerException {
        return getData().get(position);
    }

    private void setupViewUsing(RemembrallViewHolder holder, final Remembrall remembrall){

        holder.mDateView.setText(DateUtil
                .formatDayMonthFrom(remembrall.getDeliveries().get(0).getAlarm().getEndDate()));
        holder.mContactNameView.setText(remembrall.getClient().getFirstName());
        holder.mContactAddressView.setText(remembrall.getClient().getAddress());
    }

    public class RemembrallViewHolder extends CursorViewHolder
            implements View.OnClickListener {

        @BindView(R.id.item_container) View mContainer;
        @BindView(R.id.date_text) TextView mDateView;
        @BindView(R.id.contact_name_text) TextView mContactNameView;
        @BindView(R.id.contact_address_text) TextView mContactAddressView;

        public RemembrallViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            mListener.onItemClicked(getAdapterPosition());
        }

        @OnClick(R.id.delete_button)
        public void onDelete(){
            mListener.onDeleteClicked(getAdapterPosition());
        }
    }
}