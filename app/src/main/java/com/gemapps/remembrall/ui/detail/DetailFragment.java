package com.gemapps.remembrall.ui.detail;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gemapps.remembrall.R;
import com.gemapps.remembrall.data.RemembrallContract;
import com.gemapps.remembrall.ui.ButterFragment;
import com.gemapps.remembrall.ui.adapter.DeliveriesAdapter;
import com.gemapps.remembrall.ui.model.Client;
import com.gemapps.remembrall.ui.model.Delivery;
import com.gemapps.remembrall.ui.model.Job;
import com.gemapps.remembrall.ui.widget.CreateDeliverySheet;
import com.gemapps.remembrall.ui.widget.DetailHeaderHelper;
import com.gemapps.remembrall.util.DateUtil;
import com.gemapps.remembrall.util.ImageUtil;

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

    @BindView(R.id.delivery_list)
    ListView mDeliveryList;

    private Realm mRealm;
    private String mClientId;
    private Job mJob;
    private DeliveriesAdapter mAdapter;
    private CreateDeliverySheet mCreateDeliverySheet;
    private DetailHeaderHelper mHeader;

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
        mHeader = new DetailHeaderHelper(view);
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

        mHeader.setTotalPrice(String.valueOf(total));
    }

    private void setupClientUI(){
        Client client = mJob.getClient();
        mHeader.setupViews(client);
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

    @OnClick(R.id.client_whatsapp_text)
    public void onWhatsappClicked(){
        Uri uri = Uri.parse("smsto:" + mJob.getClient().getMobilePhone());
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.setPackage("com.whatsapp");
        startActivity(i);
    }

    @OnClick(R.id.client_email_text)
    public void onEmailClicked(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mJob.getClient().getEmail()});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Contrato magneto");
        intent.putExtra(Intent.EXTRA_TEXT, formatEmail());
        Uri imageUri = saveImage();
        if(imageUri != null) intent.putExtra(Intent.EXTRA_STREAM, imageUri);

        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    private Spanned formatEmail(){
        //todo: send email from the selected contract
        Delivery delivery = mJob.getDeliveries().get(0);
        Client client = mJob.getClient();
        return Html.fromHtml("<h2><b>Contrato</b></h2>" +
                String.format("<p>Nombre y Apellido: %s %s</p>", client.getFirstName(), client.getLastName()) +
                String.format("<p>DNI: %s</p>", client.getIdCard()) +
                String.format("<p>Direccion: %s</p>", client.getAddress()) +
                String.format("<p>Equipo alquilado: %s</p>", mJob.getProduct().getEquipLabel()) +
                String.format("<p>Fecha de entrega: %s Fecha de busqueda: %s</p>", DateUtil.formatDateFromTs(delivery.getAlarm().getStartDate()),
                        DateUtil.formatDateFromTs(delivery.getAlarm().getEndDate())) +
                String.format("<p>Importe: %s</p>", delivery.getPrice()));
    }

    private Uri saveImage(){
        Bitmap bitmap = ImageUtil.convertByteToBitmap(mJob.getClient().getSignImage());
        ImageUtil.changeBlackLinesToWhite(bitmap);
        return ImageUtil.saveImage(mJob.getClient(), bitmap);
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
