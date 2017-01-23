package com.gemapps.remembrall.ui;

import android.os.Bundle;

import com.gemapps.remembrall.R;

public class DetailActivity extends ButterActivity {

    private static final String FRAGMENT_TAG = "remembrall.DETAIL_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setupDetailFragment(savedInstanceState);
    }

    private void setupDetailFragment(Bundle savedInstanceState){
        if(savedInstanceState == null){
            DetailFragment detailFragment = DetailFragment
                    .getInstance(getIntent().getExtras().getString(ID_ARGS));
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_remembrall_detail, detailFragment, FRAGMENT_TAG)
                    .commit();
        }
    }
}
