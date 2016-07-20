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

    @Nullable @BindView(R.id.toolbar) protected Toolbar mToolbar;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);

        if(mToolbar != null) setSupportActionBar(mToolbar);
    }

    /**
     * Set the Home/Up button in the Toolbar
     */
    protected void setUpButton(){
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
