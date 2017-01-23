package com.gemapps.remembrall.ui;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.gemapps.remembrall.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by edu on 7/19/16.
 */

public class ButterActivity extends AppCompatActivity {

    private static final String TAG = "ButterActivity";
    protected static final String ID_ARGS = "remembrall.ID_ARGS";
    @Nullable @BindView(R.id.toolbar) protected Toolbar mToolbar;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);

        setToolbar(mToolbar);
    }

    /**
     * Set the Home/Up button in the Toolbar
     */
    public void setUpButton(){
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setToolbar(@Nullable Toolbar toolbar){
        mToolbar = toolbar;
        if(mToolbar != null) setSupportActionBar(mToolbar);
    }
}
