package com.gemapps.remembrall.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gemapps.remembrall.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends ButterFragment {


    public static DetailFragment getInstance(String id){
        Bundle bundle = new Bundle();
        bundle.putString(ID_ARGS, id);
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    public DetailFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return createView(inflater, container, R.layout.fragment_detail);
    }

}
