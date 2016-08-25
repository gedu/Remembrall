package com.gemapps.remembrall;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gemapps.remembrall.data.RemembrallContract;
import com.gemapps.remembrall.ui.ButterFragment;
import com.gemapps.remembrall.ui.adapter.RecyclerViewRemembrallAdapter;
import com.gemapps.remembrall.ui.model.Remembrall;
import com.gemapps.remembrall.util.Util;

import butterknife.BindView;

/**
 * A placeholder fragment containing a simple view.
 */
public class RememberListActivityFragment extends ButterFragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "RememberListActivityFra";
    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    private RecyclerViewRemembrallAdapter mAdapter;

    public RememberListActivityFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Remembrall[] dummyArray = new Remembrall[]{
                new Remembrall("Eduardo graciano", "Lafuente 391"),
                new Remembrall("Eduardo graciano", "Lafuente 391"),
                new Remembrall("Eduardo graciano", "Lafuente 391"),
                new Remembrall("Eduardo graciano", "Lafuente 391"),
                new Remembrall("Eduardo graciano", "Lafuente 391"),
                new Remembrall("Eduardo graciano", "Lafuente 391"),
                new Remembrall("Eduardo graciano", "Lafuente 391"),
                new Remembrall("Eduardo graciano", "Lafuente 391"),
                new Remembrall("Eduardo graciano", "Lafuente 391"),
                new Remembrall("Eduardo graciano", "Lafuente 391"),
                new Remembrall("Eduardo graciano", "Lafuente 391"),
                new Remembrall("Eduardo graciano", "Lafuente 391"),
                new Remembrall("Eduardo graciano", "Lafuente 391"),
                new Remembrall("Eduardo graciano", "Lafuente 391")
        };

        Log.d(TAG, "is a large: "+ Util.isLargeTablet(getActivity()));

//        mAdapter = new RememberListAdapter(Arrays.asList(dummyArray), getActivity());
        mAdapter = new RecyclerViewRemembrallAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = createView(inflater, container, R.layout.fragment_remember_list);

        RecyclerView.LayoutManager layoutManager;

        if(Util.isLargeTablet(getActivity()))
            layoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        else
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                RemembrallContract.AlarmEntry.CONTENT_URI,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
