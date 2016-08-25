package com.gemapps.remembrall.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gemapps.remembrall.R;
import com.gemapps.remembrall.data.RemembrallContract;

import butterknife.BindView;

/**
 * Created by edu on 8/25/16.
 */
public class RecyclerViewRemembrallAdapter extends RecyclerViewCursorAdapter<RecyclerViewRemembrallAdapter.RemembrallViewHolder> {

    public RecyclerViewRemembrallAdapter(Context context) {
        super(context);

        setCursorAdapter(null, 0, R.layout.remembrall_item_list, false);
    }

    @Override
    public RemembrallViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
//        View v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.remembrall_item_list, parent, false);
        return new RemembrallViewHolder(mCursorAdapter.newView(mContext, mCursorAdapter.getCursor(), parent));
    }

    @Override
    public void onBindViewHolder(RemembrallViewHolder holder, int position) {

        mCursorAdapter.getCursor().moveToPosition(position);
        setViewHolder(holder);
        mCursorAdapter.bindView(null, mContext, mCursorAdapter.getCursor());
    }

    public class RemembrallViewHolder extends CursorViewHolder {

        @BindView(R.id.contact_name_text) TextView mContactNameView;
        @BindView(R.id.contact_address_text) TextView mContactAddressView;

        public RemembrallViewHolder(View view) {
            super(view);
        }

        @Override
        public void bindCursor(Cursor cursor) {

            mContactNameView.setText(cursor.getString(cursor.getColumnIndex(RemembrallContract.ClientEntry.COLUMN_FIRST_NAME)));
            mContactAddressView.setText(cursor.getString(cursor.getColumnIndex(RemembrallContract.ClientEntry.COLUMN_LAST_NAME)));
        }
    }
}