package com.gemapps.remembrall.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.gemapps.remembrall.R;

public class RemembrallDetailEditionActivity extends ButterActivity {

    private static final String FRAGMENT_TAG = "remembrall.DETAIL_TAG";
    private static final String ID_ARGS = "remembrall.ID_ARGS";

    public static Intent getInstance(Context context, String id){
        Intent intent = new Intent(context, RemembrallDetailEditionActivity.class);
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
            RemembrallDetailEditionFragment detailFragment = RemembrallDetailEditionFragment
                    .getInstance(getIntent().getExtras().getString(ID_ARGS));
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_remembrall_detail, detailFragment, FRAGMENT_TAG)
                    .commit();
        }
    }
}
