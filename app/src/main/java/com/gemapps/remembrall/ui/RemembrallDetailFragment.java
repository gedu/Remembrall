package com.gemapps.remembrall.ui;


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
import com.gemapps.remembrall.ui.model.Client;
import com.gemapps.remembrall.util.ImageUtil;

import butterknife.BindView;
import io.realm.Realm;

public class RemembrallDetailFragment extends ButterFragment {

    private static final String TAG = "RemembrallDetailFragmen";
    private static final String ID_ARGS = "remembrall.ID_ARGS";

    public RemembrallDetailFragment() {}

    @BindView(R.id.client_sign_image)
    ImageView mImageView;
    @BindView(R.id.client_name_text)
    TextView mClientNameText;

    private Realm mRealm;

    public static RemembrallDetailFragment getInstance(String id){
        Bundle bundle = new Bundle();
        bundle.putString(ID_ARGS, id);
        RemembrallDetailFragment detailFragment = new RemembrallDetailFragment();
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return createView(inflater, container, R.layout.fragment_remembrall_detail);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpButton();
        mRealm = Realm.getDefaultInstance();

        setupClientInfo();
    }

    private void setupClientInfo() {

        Client client = mRealm.where(Client.class)
                .equalTo(RemembrallContract.ClientEntry.COLUMN_ID_CARD,
                        getArguments().getString(ID_ARGS)).findFirst();

        if(client != null){
            Bitmap bitmap = ImageUtil.convertByteToBitmap(client.getSignImage());
            ImageUtil.changeBlackLinesToWhite(bitmap);
            if(bitmap != null)mImageView.setImageBitmap(bitmap);
            mClientNameText.setText(client.getFirstName() + " "+ client.getLastName());
        }
    }
}
