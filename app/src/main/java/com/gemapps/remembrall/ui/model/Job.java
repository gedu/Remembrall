package com.gemapps.remembrall.ui.model;

import com.gemapps.remembrall.ui.widget.FormUIHandler;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by edu on 7/19/16.
 */

public class Job extends RealmObject {

    private static final String TAG = "Job";

    @PrimaryKey
    private String mId;
    private Client mClient;
    private Product mProduct;
    private RealmList<Delivery> mDeliveries;

    public Job() {}

    public Job(FormUIHandler form, RealmList<Delivery> deliveries){

        this.mClient = form.buildClient();
        this.mDeliveries = deliveries;
        this.mProduct = form.buildProduct();
        setPrimaryKey();
    }

    public Job(String firstName, String lastName, String idCard, String address,
               String email, String homePhone, String mobilePhone, RealmList<Delivery> deliveries,
               String equipLabel, String equipNum, String testerNum, String terminalNum,
               String description) {

        this.mClient = new Client(firstName, lastName, idCard, address, email, homePhone, mobilePhone);
        this.mDeliveries = deliveries;
        this.mProduct = new Product(equipLabel, equipNum, testerNum, terminalNum, description);
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
    public RealmList<Delivery> getDeliveries() {
        return mDeliveries;
    }
}
