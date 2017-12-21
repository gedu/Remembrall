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

import com.gemapps.remembrall.ui.ButterFragment;
import com.gemapps.remembrall.ui.adapter.RecyclerViewRemembrallAdapter;
import com.gemapps.remembrall.ui.detail.DetailActivity;
import com.gemapps.remembrall.ui.model.Job;
import com.gemapps.remembrall.util.RealmUtil;
import com.gemapps.remembrall.util.Util;

import butterknife.BindView;
import io.realm.Realm;
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
    private RealmResults<Job> mJobs;

    public RememberListActivityFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRealm = Realm.getDefaultInstance();

        Log.d(TAG, "is a large: "+ Util.isLargeTablet(getActivity()));

        mJobs = mRealm.where(Job.class).findAllAsync();
        // TODO: 1/16/17 : should be sorted by date, endDate at the top
        mAdapter = new RecyclerViewRemembrallAdapter(getActivity(), this, mJobs);
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

        if(Util.isLargeTablet(getActivity())) {
            layoutManager = new GridLayoutManager(getActivity(), 2, LIST_ORIENTATION, true);
            ((GridLayoutManager)layoutManager).setStackFromEnd(true);
        }else {
            layoutManager = new LinearLayoutManager(getActivity(), LIST_ORIENTATION, true);
            ((LinearLayoutManager)layoutManager).setStackFromEnd(true);
        }


        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LIST_ORIENTATION));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClicked(int position) {
        Job job = mJobs.get(position);
        startActivity(DetailActivity.getInstance(getContext(), job.getId()));
    }

    @Override
    public void onDeleteClicked(final int position) {

        final String clientId = getClientIdFrom(position);
        RealmUtil.deleteJobs(mRealm, clientId);
    }

    private String getClientIdFrom(int position){
        Job currentRem = mJobs.get(position);
        return currentRem.getId();
    }

    @Override
    public void onDestroy() {
        mRealm.close();
        super.onDestroy();

    }
}
