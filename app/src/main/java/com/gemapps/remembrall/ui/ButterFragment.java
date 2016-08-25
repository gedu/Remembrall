package com.gemapps.remembrall.ui;

import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by edu on 7/21/16.
 */

public class ButterFragment extends Fragment {

    /**
     * Inflate a new view with the given id and link it with ButterKnife
     * @param inflater
     * @param container
     * @param layoutId
     * @return
     */
    protected View createView(LayoutInflater inflater, ViewGroup container,
                              @LayoutRes int layoutId){
        View rootView = inflater.inflate(layoutId, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    public void fabAction(){
        //inherited classes can override to perform an fab action
    }

    public void fabStyle(FloatingActionButton fab){
        //inherited classes can override to style the fab button
    }
}
