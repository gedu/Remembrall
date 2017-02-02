package com.gemapps.remembrall.ui.edition;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gemapps.remembrall.R;
import com.gemapps.remembrall.data.RemembrallContract;
import com.gemapps.remembrall.ui.ButterFragment;
import com.gemapps.remembrall.ui.model.Job;
import com.gemapps.remembrall.ui.widget.FormUIHandler;
import com.gemapps.remembrall.util.ImageUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;

public class DetailEditionFragment extends ButterFragment {

    private static final String TAG = "RemembrallDetailFragmen";

    public DetailEditionFragment() {}

    @BindView(R.id.client_sign_image)
    ImageView mImageView;
    @BindView(R.id.client_name_text)
    TextView mClientNameText;

    private Realm mRealm;
    private FormUIHandler mForm;
    private Job mJob;

    public static DetailEditionFragment getInstance(String id){
        Bundle bundle = new Bundle();
        bundle.putString(ID_ARGS, id);
        DetailEditionFragment detailFragment = new DetailEditionFragment();
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = createView(inflater, container, R.layout.fragment_remembrall_detail_edition);
        mForm = new FormUIHandler(rootView, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRealm = Realm.getDefaultInstance();
        setupUI();
    }

    private void setupUI(){
        setUpButton();
        findRemembrallItem();
        setupClientInfo();
        setupProductInfo();
    }

    private void findRemembrallItem(){
        mJob = mRealm.where(Job.class)
                .equalTo(RemembrallContract.JobEntry.COLUMN_ID,
                        getArguments().getString(ID_ARGS)).findFirst();

        mJob.addChangeListener(new RealmChangeListener<RealmModel>() {
            @Override
            public void onChange(RealmModel element) {
                setupClientInfo();
            }
        });
    }

    private void setupClientInfo() {

        mForm.fillClientUI(mJob.getClient());
        setupImageHeader();
        setupNameHeader();
    }

    private void setupImageHeader(){

        Bitmap bitmap = ImageUtil.convertByteToBitmap(mJob.getClient().getSignImage());
        ImageUtil.changeBlackLinesToWhite(bitmap);
        if(bitmap != null)mImageView.setImageBitmap(bitmap);
    }

    private void setupNameHeader(){
        mClientNameText.setText(mJob.getClient().getFirstName() +
                " "+ mJob.getClient().getLastName());
    }

    private void setupProductInfo(){
        mForm.fillProductUI(mJob.getProduct());
    }

    @OnClick(R.id.fab)
    public void onFabClicked(){
        update();
    }

    private void update(){
        mForm.updateClient(mJob.getClient());
    }

    @Override
    public void onDestroy() {
        mRealm.close();
        super.onDestroy();
    }
}
