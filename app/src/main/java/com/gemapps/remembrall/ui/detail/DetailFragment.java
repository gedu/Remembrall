package com.gemapps.remembrall.ui.detail;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.gemapps.remembrall.R;
import com.gemapps.remembrall.data.RemembrallContract;
import com.gemapps.remembrall.ui.ButterFragment;
import com.gemapps.remembrall.ui.adapter.DeliveriesAdapter;
import com.gemapps.remembrall.ui.model.Client;
import com.gemapps.remembrall.ui.model.Delivery;
import com.gemapps.remembrall.ui.model.Remembrall;
import com.gemapps.remembrall.ui.widget.CreateDeliverySheet;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends ButterFragment {

    private static final String BOTTOM_SHEET_FRAGMENT_TAG = "remembrall.BOTTOM_SHEET_FRAGMENT_TAG";

    @BindView(R.id.total_price_text)
    TextView mTotalPriceText;
    @BindView(R.id.client_mobile_text)
    TextView mMobileText;
    @BindView(R.id.client_address_text)
    TextView mAddressText;
    @BindView(R.id.delivery_list)
    ListView mDeliveryList;

    private Realm mRealm;
    private String mClientId;
    private Remembrall mRemembrall;
    private DeliveriesAdapter mAdapter;
    private CreateDeliverySheet mCreateDeliverySheet;

    public static DetailFragment getInstance(String id){
        Bundle bundle = new Bundle();
        bundle.putString(ID_ARGS, id);
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    public DetailFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm = Realm.getDefaultInstance();
        mClientId = getArguments().getString(ID_ARGS);
        mCreateDeliverySheet = CreateDeliverySheet.getInstance(mClientId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return createView(inflater, container, R.layout.fragment_detail);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findRemembrallItem();
        setupUI();
    }

    private void findRemembrallItem(){
        mRemembrall = mRealm.where(Remembrall.class)
                .equalTo(RemembrallContract.ClientProdEntry.COLUMN_ID, mClientId).findFirst();

        mRemembrall.addChangeListener(new RealmChangeListener<RealmModel>() {
            @Override
            public void onChange(RealmModel element) {
                updateListAndTotal();
            }
        });
    }

    private void setupUI(){

        setupTotalPriceUI();
        setupClientUI();
        setupDeliveryList();
    }

    private void setupTotalPriceUI(){
        float total = 0;
        for (Delivery delivery : mRemembrall.getDeliveries()){
            total += delivery.getPrice();
        }

        mTotalPriceText.setText(String.valueOf(total));
    }

    private void setupClientUI(){
        Client client = mRemembrall.getClient();

        mMobileText.setText(client.getMobilePhone());
        mAddressText.setText(client.getAddress());
    }

    private void setupDeliveryList(){

        mAdapter = new DeliveriesAdapter(getActivity(), mRemembrall.getDeliveries());
        mDeliveryList.setAdapter(mAdapter);
    }

    private void updateListAndTotal(){
        mAdapter.notifyDataSetChanged();
        setupTotalPriceUI();
    }

    @OnClick(R.id.add_delivery_button)
    public void onAddDeliveryClicked(){
        mCreateDeliverySheet.show(getSupportFragmentManager(), BOTTOM_SHEET_FRAGMENT_TAG);
    }

    private FragmentManager getSupportFragmentManager(){
        return getActivity().getSupportFragmentManager();
    }
}
