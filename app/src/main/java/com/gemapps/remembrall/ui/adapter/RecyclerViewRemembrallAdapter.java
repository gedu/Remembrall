package com.gemapps.remembrall.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gemapps.remembrall.R;
import com.gemapps.remembrall.ui.RemembrallDetailEditionActivity;
import com.gemapps.remembrall.ui.model.Remembrall;

import butterknife.BindView;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by edu on 8/25/16.
 */
public class RecyclerViewRemembrallAdapter
        extends RealmRecyclerViewAdapter<Remembrall, RecyclerViewRemembrallAdapter.RemembrallViewHolder> {

    private static final String TAG = "RecyclerViewRemembrallA";
    public RecyclerViewRemembrallAdapter(Context context, OrderedRealmCollection<Remembrall> data) {
        super(context, data, true);
        Log.d(TAG, "RecyclerViewRemembrallAdapter: "+data.size());
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

        holder.mContactNameView.setText(remembrall.getClient().getFirstName());
        holder.mContactAddressView.setText(remembrall.getClient().getAddress());

        holder.mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.getContext().startActivity(RemembrallDetailEditionActivity
                        .getInstance(v.getContext(), remembrall.getId()));
            }
        });
    }

    public class RemembrallViewHolder extends CursorViewHolder {

        @BindView(R.id.item_container) View mContainer;
        @BindView(R.id.contact_name_text) TextView mContactNameView;
        @BindView(R.id.contact_address_text) TextView mContactAddressView;

        public RemembrallViewHolder(View view) {
            super(view);
        }
    }
}