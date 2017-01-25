package com.gemapps.remembrall.ui;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.gemapps.remembrall.R;
import com.gemapps.remembrall.data.RemembrallContract;
import com.gemapps.remembrall.ui.model.Remembrall;
import com.gemapps.remembrall.util.ActivityTransitionUtil;
import com.gemapps.remembrall.util.ImageUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;

public class DetailActivity extends ButterActivity {

    private static final String FRAGMENT_TAG = "remembrall.DETAIL_TAG";

    @BindView(R.id.client_sign_image)
    ImageView mImageView;
    @BindView(R.id.client_name_text)
    TextView mClientNameText;
    private String mClientId = "";
    private Remembrall mRemembrall;

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
        mRemembrall = realm.where(Remembrall.class)
                .equalTo(RemembrallContract.ClientProdEntry.COLUMN_ID, mClientId)
                .findFirst();
    }

    private void setupHeader(){
        setupImageHeader();
        setupNameHeader();
    }

    private void setupImageHeader(){

        Bitmap bitmap = ImageUtil.convertByteToBitmap(mRemembrall.getClient().getSignImage());
        ImageUtil.changeBlackLinesToWhite(bitmap);
        if(bitmap != null) mImageView.setImageBitmap(bitmap);
    }

    private void setupNameHeader(){
        mClientNameText.setText(mRemembrall.getClient().getFirstName() +
                " "+ mRemembrall.getClient().getLastName());
    }

    @OnClick(R.id.fab)
    public void onFabClicked(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(this, findViewById(R.id.appbar), "appbar");

            startActivity(DetailEditionActivity.getInstance(DetailActivity.this, mClientId),
                    options.toBundle());
        }
    }
}
