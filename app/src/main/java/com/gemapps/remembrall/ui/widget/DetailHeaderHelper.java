package com.gemapps.remembrall.ui.widget;

import android.view.View;
import android.widget.TextView;

import com.gemapps.remembrall.R;
import com.gemapps.remembrall.ui.model.Client;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by edu on 2/7/17.
 */

public class DetailHeaderHelper {

    @BindView(R.id.total_price_text)
    TextView mTotalPriceText;
    @BindView(R.id.client_mobile_text)
    TextView mMobileText;
    @BindView(R.id.client_address_text)
    TextView mAddressText;
    @BindView(R.id.client_whatsapp_text)
    TextView mWhatsappText;
    @BindView(R.id.client_email_text)
    TextView mEmailText;

    public DetailHeaderHelper(View view) {
        ButterKnife.bind(this, view);
    }

    public void setupViews(Client client){
        mMobileText.setText(client.getMobilePhone());
        mAddressText.setText(client.getAddress());
        mWhatsappText.setText(client.getMobilePhone());
        mEmailText.setText(client.getEmail());
    }

    public void setTotalPrice(String price){
        mTotalPriceText.setText(price);
    }
}
