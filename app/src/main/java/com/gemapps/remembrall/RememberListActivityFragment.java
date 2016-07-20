package com.gemapps.remembrall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gemapps.remembrall.ui.adapter.RememberListAdapter;
import com.gemapps.remembrall.ui.model.Rememball;
import com.gemapps.remembrall.util.Util;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class RememberListActivityFragment extends Fragment {

    private static final String TAG = "RememberListActivityFra";
    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    private RememberListAdapter mAdapter;

    public RememberListActivityFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        Rememball[] dummyArray = new Rememball[]{
                new Rememball("Eduardo graciano", "Lafuente 391"),
                new Rememball("Eduardo graciano", "Lafuente 391"),
                new Rememball("Eduardo graciano", "Lafuente 391"),
                new Rememball("Eduardo graciano", "Lafuente 391"),
                new Rememball("Eduardo graciano", "Lafuente 391"),
                new Rememball("Eduardo graciano", "Lafuente 391"),
                new Rememball("Eduardo graciano", "Lafuente 391"),
                new Rememball("Eduardo graciano", "Lafuente 391"),
                new Rememball("Eduardo graciano", "Lafuente 391"),
                new Rememball("Eduardo graciano", "Lafuente 391"),
                new Rememball("Eduardo graciano", "Lafuente 391"),
                new Rememball("Eduardo graciano", "Lafuente 391"),
                new Rememball("Eduardo graciano", "Lafuente 391"),
                new Rememball("Eduardo graciano", "Lafuente 391")
        };

        Log.d(TAG, "is a large: "+ Util.isLargeTablet(getActivity()));

        mAdapter = new RememberListAdapter(Arrays.asList(dummyArray), getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_remember_list, container, false);
        ButterKnife.bind(this, rootView);

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
