package com.gemapps.remembrall.ui.model;

import com.gemapps.remembrall.ui.widget.FormUIHandler;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by edu on 7/19/16.
 */

public class Remembrall extends RealmObject {

    private static final String TAG = "Remembrall";

    @PrimaryKey
    private String mId;
    private Client mClient;
    private Product mProduct;
    private RealmList<Delivery> mDeliveries;

    public Remembrall() {}

    public Remembrall(FormUIHandler form, RealmList<Delivery> deliveries){

        this.mClient = form.buildClient();
        this.mDeliveries = deliveries;
        this.mProduct = form.buildProduct();
        setPrimaryKey();
    }

    public Remembrall(String firstName, String lastName, String idCard, String address,
                      String email, String homePhone, String mobilePhone, RealmList<Delivery> deliveries,
                      String equipLabel, String equipNum, String testerNum, String terminalNum,
                      String price, String description, byte[] signImage) {

        this.mClient = new Client(firstName, lastName, idCard, address, email, homePhone, mobilePhone, signImage);
        this.mDeliveries = deliveries;
        this.mProduct = new Product(equipLabel, equipNum, testerNum, terminalNum, price, description);
        setPrimaryKey();
    }

    private void setPrimaryKey(){
        if(mClient != null) mId = mClient.getIdCard();
    }

    public Product getProduct() {
        return mProduct;
    }

    public Client getClient() {
        return mClient;
    }

    public String getId(){
        return mId;
    }

    public void addDelivery(Delivery delivery){
        mDeliveries.add(delivery);
    }
    public List<Delivery> getDeliveries() {
        return mDeliveries;
    }
}
