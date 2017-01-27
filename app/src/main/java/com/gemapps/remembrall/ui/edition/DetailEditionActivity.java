package com.gemapps.remembrall.ui.edition;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.gemapps.remembrall.R;
import com.gemapps.remembrall.ui.ButterActivity;

public class DetailEditionActivity extends ButterActivity {

    private static final String FRAGMENT_TAG = "remembrall.DETAIL_TAG";

    public static Intent getInstance(Context context, String id){
        Intent intent = new Intent(context, DetailEditionActivity.class);
        intent.putExtra(ID_ARGS, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remembrall_detail_edition);

        setupDetailFragment(savedInstanceState);
    }

    private void setupDetailFragment(Bundle savedInstanceState){
        if(savedInstanceState == null){
            DetailEditionFragment detailFragment = DetailEditionFragment
                    .getInstance(getIntent().getExtras().getString(ID_ARGS));
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_remembrall_detail, detailFragment, FRAGMENT_TAG)
                    .commit();
        }
    }
}
