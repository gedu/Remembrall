package com.gemapps.remembrall.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by edu on 8/25/16.
 *
 * Class to take care of the cursor adapter. Why this way so I don't have to deal with
 * closing the cursor and stuff, {@link CursorAdapter} will take care (?)
 */

public abstract class RecyclerViewCursorAdapter<T extends CursorViewHolder>
        extends RecyclerView.Adapter<T> {

    private static final String TAG = "RecyclerViewCursorAdapt";

    protected Context mContext;
    protected CursorAdapter mCursorAdapter;

    private T mViewHolder;

    public RecyclerViewCursorAdapter(Context context) {
        mContext = context;
    }

    protected void setCursorAdapter(Cursor cursor, int flags,
                                    final int resource, final boolean attachToRoot){

        mCursorAdapter = new CursorAdapter(mContext, cursor, flags) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(resource, parent, attachToRoot);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {

                mViewHolder.bindCursor(cursor);
            }
        };
    }

    @Override
    public int getItemCount() {
        return mCursorAdapter.getCount();
    }

    protected void setViewHolder(T viewHolder){
        mViewHolder = viewHolder;
    }

    public void swapCursor(Cursor cursor){
        if(cursor != null){
            mCursorAdapter.swapCursor(cursor);
            notifyDataSetChanged();
        }
    }
}
