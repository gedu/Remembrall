package com.gemapps.remembrall.ui.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by edu on 8/25/16.
 *
 * Wrapper class for cursor binding with ButterKnife binding
 */

public abstract class CursorViewHolder extends RecyclerView.ViewHolder {

    public CursorViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public abstract void bindCursor(Cursor cursor);

}
