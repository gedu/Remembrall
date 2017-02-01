package com.gemapps.remembrall.ui.detail;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gemapps.remembrall.R;
import com.gemapps.remembrall.data.RemembrallContract;
import com.gemapps.remembrall.ui.ButterActivity;
import com.gemapps.remembrall.ui.edition.DetailEditionActivity;
import com.gemapps.remembrall.ui.model.Job;
import com.gemapps.remembrall.util.ActivityTransitionUtil;
import com.gemapps.remembrall.util.ImageUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;

public class DetailActivity extends ButterActivity {

    private static final String FRAGMENT_TAG = "remembrall.DETAIL_TAG";

    // TODO: 1/26/17 Create a HeaderUIHelper to move this views to there
    @BindView(R.id.client_sign_image)
    ImageView mImageView;
    @BindView(R.id.client_name_text)
    TextView mClientNameText;
    private String mClientId = "";
    private Job mJob;

    public static Intent getInstance(Context context, String id){
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(ID_ARGS, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityTransitionUtil.setFadeTransition(getWindow());
        }
        setContentView(R.layout.activity_detail);
        setUpButton();
        setClientId();
        setRemembrall();
        setupHeader();
        setupDetailFragment(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO: 1/26/17 save the client id to recover it later
        super.onSaveInstanceState(outState);
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

    private void setRemembrall(){
        Realm realm = Realm.getDefaultInstance();
        mJob = realm.where(Job.class)
                .equalTo(RemembrallContract.JobEntry.COLUMN_ID, mClientId)
                .findFirst();
    }

    private void setupHeader(){
        setupImageHeader();
        setupNameHeader();
    }

    private void setupImageHeader(){

        Bitmap bitmap = ImageUtil.convertByteToBitmap(mJob.getClient().getSignImage());
        ImageUtil.changeBlackLinesToWhite(bitmap);
        if(bitmap != null) mImageView.setImageBitmap(bitmap);
    }

    private void setupNameHeader(){
        mClientNameText.setText(mJob.getClient().getFirstName() +
                " "+ mJob.getClient().getLastName());
    }

    @OnClick(R.id.fab)
    public void onFabClicked(final View fab){

        Intent editionIntent = DetailEditionActivity.getInstance(DetailActivity.this, mClientId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Pair<View, String> appbarPair = new Pair<>(findViewById(R.id.appbar), "appbar");
            Pair<View, String> fabPair = new Pair<>(fab, "fab");
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(this, appbarPair, fabPair);

            startActivity(editionIntent, options.toBundle());
        }else{
            startActivity(editionIntent);
        }
    }



}
