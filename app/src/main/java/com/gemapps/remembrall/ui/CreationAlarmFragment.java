package com.gemapps.remembrall.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gemapps.remembrall.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreationAlarmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreationAlarmFragment extends ButterFragment {

    public CreationAlarmFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment CreationAlarmFragment.
     */
    public static CreationAlarmFragment newInstance() {

        return new CreationAlarmFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return createView(inflater, container, R.layout.fragment_creation_alarm);
    }

}
