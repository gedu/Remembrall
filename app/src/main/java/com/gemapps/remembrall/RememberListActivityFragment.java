package com.gemapps.remembrall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gemapps.remembrall.ui.adapter.RememberListAdapter;
import com.gemapps.remembrall.ui.model.Rememball;

import java.util.Arrays;

import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class RememberListActivityFragment extends Fragment {

    private RememberListAdapter mAdapter;

    public RememberListActivityFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Rememball[] dummyArray = new Rememball[]{
                new Rememball("Eduardo graciano", "Lafuente 391"),
                new Rememball("Eduardo graciano", "Lafuente 391"),
                new Rememball("Eduardo graciano", "Lafuente 391"),
                new Rememball("Eduardo graciano", "Lafuente 391"),
                new Rememball("Eduardo graciano", "Lafuente 391"),
                new Rememball("Eduardo graciano", "Lafuente 391"),
                new Rememball("Eduardo graciano", "Lafuente 391"),
        };

        mAdapter = new RememberListAdapter(Arrays.asList(dummyArray), getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_remember_list, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

}
