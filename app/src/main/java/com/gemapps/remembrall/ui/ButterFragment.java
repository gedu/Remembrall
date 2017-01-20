package com.gemapps.remembrall.ui;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gemapps.remembrall.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by edu on 7/21/16.
 */

public class ButterFragment extends Fragment {

    private static final String TAG = "ButterFragment";
    @Nullable @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

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

        if(mToolbar != null && activityIsButterActivity())
            getButterActivity().setToolbar(mToolbar);
        return rootView;
    }

    private boolean activityIsButterActivity(){
        return getActivity() instanceof ButterActivity;
    }

    protected void setUpButton(){
        if(activityIsButterActivity()) getButterActivity().setUpButton();
    }

    protected ButterActivity getButterActivity(){
        return  (ButterActivity)getActivity();
    }

    public void fabAction(){
        //inherited classes can override to perform an fab action
    }

    public void fabStyle(FloatingActionButton fab){
        //inherited classes can override to style the fab button
    }
}
