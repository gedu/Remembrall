package com.gemapps.remembrall.ui;

import android.os.Bundle;

import com.gemapps.remembrall.R;
import com.simplify.ink.InkView;

import butterknife.BindView;
import butterknife.OnClick;

public class InkWritingActivity extends BaseCardActivity {

    @BindView(R.id.ink_view) InkView mInkView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ink_writing);

        doEntryAnimation();
        overrideTrans();
    }

    @OnClick(R.id.redo_button)
    public void redoSignClicked(){
        mInkView.clear();
    }
}
