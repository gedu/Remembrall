package com.gemapps.remembrall.ui.detail;


import android.content.Intent;
import android.net.Uri;
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
import com.gemapps.remembrall.ui.model.Job;
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

    private static final String TAG = "DetailFragment";
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
    private Job mJob;
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
        mJob = mRealm.where(Job.class)
                .equalTo(RemembrallContract.JobEntry.COLUMN_ID, mClientId)
                .findFirst();

        mJob.addChangeListener(new RealmChangeListener<RealmModel>() {
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
        for (Delivery delivery : mJob.getDeliveries()){
            total += delivery.getPrice();
        }

        mTotalPriceText.setText(String.valueOf(total));
    }

    private void setupClientUI(){
        Client client = mJob.getClient();

        mMobileText.setText(client.getMobilePhone());
        mAddressText.setText(client.getAddress());
    }

    private void setupDeliveryList(){

        mAdapter = new DeliveriesAdapter(getActivity(), mJob.getDeliveries());
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

    @OnClick(R.id.client_address_text)
    public void onAddressClicked(){

        Uri gmmIntentUri = Uri.parse("geo:0,0?q="+mJob.getClient().getAddress());
        Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        startActivity(intent);
    }

    @OnClick(R.id.client_mobile_text)
    public void onPhoneClicked(){
        Uri uri = Uri.parse("smsto:" + mJob.getClient().getMobilePhone());
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        startActivity(intent);
    }

    private FragmentManager getSupportFragmentManager(){
        return getActivity().getSupportFragmentManager();
    }

    @Override
    public void onDestroy() {
        mJob.removeChangeListeners();
        mRealm.close();
        super.onDestroy();
    }
}
