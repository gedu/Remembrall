package com.gemapps.remembrall.ui.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gemapps.remembrall.R;
import com.gemapps.remembrall.ui.model.Delivery;
import com.gemapps.remembrall.ui.model.RememberAlarm;
import com.gemapps.remembrall.ui.model.Remembrall;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by edu on 1/24/17.
 */

public class CreateDeliverySheet extends BottomSheetDialogFragment {

    private static final String CLIENT_ID_ARGS = "remembrall.CLIENT_ID_ARGS";

    @BindView(R.id.delivery_price)
    EditText mPriceText;

    private String mClientId;
    private DatePickerHandler mDatePickerHandler;

    public static CreateDeliverySheet getInstance(String clientId){
        CreateDeliverySheet createDeliverySheet = new CreateDeliverySheet();
        Bundle bundle = new Bundle();
        bundle.putString(CLIENT_ID_ARGS, clientId);
        createDeliverySheet.setArguments(bundle);
        return  createDeliverySheet;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClientId = getArguments().getString(CLIENT_ID_ARGS);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bottom_sheet_create_delivery, container, false);
        mDatePickerHandler = new DatePickerHandler(rootView, 30, -1);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.cancel_button)
    public void onCancelButton(){
        dismiss();
    }

    @OnClick(R.id.accept_button)
    public void onAcceptButton(){
        saveDelivery();
        dismiss();
    }

    private void saveDelivery(){
        Realm realm = Realm.getDefaultInstance();
        final Remembrall remembrall = realm.where(Remembrall.class).findFirst();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RememberAlarm oldAlarm = remembrall.getDeliveries().get(0).getAlarm();
                RememberAlarm alarm = new RememberAlarm(oldAlarm.getLabel(),
                        oldAlarm.getDescription(),
                        Calendar.getInstance().getTimeInMillis(),
                        mDatePickerHandler.getPickedDate());
                Delivery delivery = new Delivery(alarm,
                        Float.valueOf(mPriceText.getText().toString()));
                remembrall.addDelivery(delivery);
            }
        });

    }
}
