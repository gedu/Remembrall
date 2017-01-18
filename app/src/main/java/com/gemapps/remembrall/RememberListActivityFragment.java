package com.gemapps.remembrall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gemapps.remembrall.ui.ButterFragment;
import com.gemapps.remembrall.ui.adapter.RecyclerViewRemembrallAdapter;
import com.gemapps.remembrall.ui.model.Client;
import com.gemapps.remembrall.ui.model.Remembrall;
import com.gemapps.remembrall.util.Util;

import butterknife.BindView;
import io.realm.Realm;

/**
 * A placeholder fragment containing a simple view.
 */
public class RememberListActivityFragment extends ButterFragment {

    private static final String TAG = "RememberListActivityFra";
    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    private RecyclerViewRemembrallAdapter mAdapter;

    public RememberListActivityFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Realm realm = Realm.getDefaultInstance();

        Log.d(TAG, "is a large: "+ Util.isLargeTablet(getActivity()));

        Client client = realm.where(Client.class).findFirst();
        Log.d(TAG, "onCreate: "+client);
        // TODO: 1/16/17 : should be sorted by date, endDate at the top
        mAdapter = new RecyclerViewRemembrallAdapter(getActivity(),
                realm.where(Remembrall.class).findAllAsync());
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
}
