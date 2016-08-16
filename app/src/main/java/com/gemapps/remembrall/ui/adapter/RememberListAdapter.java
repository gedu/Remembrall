package com.gemapps.remembrall.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gemapps.remembrall.R;
import com.gemapps.remembrall.ui.model.Remembrall;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by edu on 7/19/16.
 */
public class RememberListAdapter extends RecyclerView.Adapter<RememberListAdapter.RememberViewItem> {

    private static final String TAG = "RememberListAdapter";

    private final Context context;
    private List<Remembrall> items;

    public RememberListAdapter(List<Remembrall> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public RememberViewItem onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rememball_item_list, parent, false);
        return new RememberViewItem(v);
    }

    @Override
    public void onBindViewHolder(RememberViewItem holder, int position) {
        Remembrall item = items.get(position);

        holder.mContactNameView.setText(item.getClient().getFirstName());
        holder.mContactAddressView.setText(item.getClient().getLastName());
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public class RememberViewItem extends RecyclerView.ViewHolder {

        @BindView(R.id.contact_name_text) TextView mContactNameView;
        @BindView(R.id.contact_address_text) TextView mContactAddressView;

        public RememberViewItem(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}