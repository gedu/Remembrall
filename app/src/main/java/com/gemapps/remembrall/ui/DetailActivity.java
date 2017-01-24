package com.gemapps.remembrall.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.gemapps.remembrall.R;

import butterknife.OnClick;

public class DetailActivity extends ButterActivity {

    private static final String FRAGMENT_TAG = "remembrall.DETAIL_TAG";

    private String mClientId = "";

    public static Intent getInstance(Context context, String id){
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(ID_ARGS, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setClientId();
        setupDetailFragment(savedInstanceState);
    }

    private void setClientId(){
        mClientId = getIntent().getExtras().getString(ID_ARGS);
    }

    private void setupDetailFragment(Bundle savedInstanceState){
        if(savedInstanceState == null){
            DetailFragment detailFragment = DetailFragment
                    .getInstance(mClientId);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, detailFragment, FRAGMENT_TAG)
                    .commit();
        }
    }

    @OnClick(R.id.fab)
    public void onFabClicked(){

        startActivity(DetailEditionActivity.getInstance(DetailActivity.this, mClientId));
    }
}
