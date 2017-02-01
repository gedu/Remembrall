package com.gemapps.remembrall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gemapps.remembrall.ui.ButterFragment;
import com.gemapps.remembrall.ui.adapter.RecyclerViewRemembrallAdapter;
import com.gemapps.remembrall.ui.detail.DetailActivity;
import com.gemapps.remembrall.ui.model.Remembrall;
import com.gemapps.remembrall.util.RealmUtil;
import com.gemapps.remembrall.util.Util;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * A placeholder fragment containing a simple view.
 */
public class RememberListActivityFragment extends ButterFragment
        implements RecyclerViewRemembrallAdapter.RemembrallItemsListener {

    private static final String TAG = "RememberListActivityFra";
    private static final int LIST_ORIENTATION = LinearLayoutManager.VERTICAL;
    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    private Realm mRealm;
    private RecyclerViewRemembrallAdapter mAdapter;
    private RealmResults<Remembrall> mRemembralls;

    public RememberListActivityFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRealm = Realm.getDefaultInstance();

        Log.d(TAG, "is a large: "+ Util.isLargeTablet(getActivity()));

        mRemembralls = mRealm.where(Remembrall.class).findAllAsync();
        // TODO: 1/16/17 : should be sorted by date, endDate at the top
        mAdapter = new RecyclerViewRemembrallAdapter(getActivity(), this, mRemembralls);
        mRealm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm element) {
                Toast.makeText(getContext(), "DELETED?", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return createView(inflater, container, R.layout.fragment_remember_list);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUI();
    }

    private void setupUI(){
        RecyclerView.LayoutManager layoutManager;

        if(Util.isLargeTablet(getActivity()))
            layoutManager = new GridLayoutManager(getActivity(), 2, LIST_ORIENTATION, false);
        else
            layoutManager = new LinearLayoutManager(getActivity(), LIST_ORIENTATION, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LIST_ORIENTATION));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClicked(int position) {
        Remembrall remembrall = mRemembralls.get(position);
        startActivity(DetailActivity.getInstance(getContext(), remembrall.getId()));
    }

    @Override
    public void onDeleteClicked(final int position) {

        final String clientId = getClientIdFrom(position);
        RealmUtil.deleteRemembralls(mRealm, clientId);
    }

    private String getClientIdFrom(int position){
        Remembrall currentRem = mRemembralls.get(position);
        return currentRem.getId();
    }

    @Override
    public void onDestroy() {
        mRealm.close();
        super.onDestroy();

    }
}
