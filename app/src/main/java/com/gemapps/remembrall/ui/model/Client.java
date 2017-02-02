package com.gemapps.remembrall.ui.model;

import com.gemapps.remembrall.ui.widget.searchtext.Searcheable;

import io.realm.RealmObject;

/**
 * Created by edu on 8/10/16.
 */

public class Client extends RealmObject implements Searcheable {

    private String mFirstName;
    private String mLastName;
    private String mIdCard;
    private String mAddress;
    private String mEmail;
    private String mHomePhone;
    private String mMobilePhone;
    private byte[] mSignImage;

    public Client() {}

    public Client(String firstName, String lastName) {
        mFirstName = firstName;
        mLastName = lastName;
    }

    public Client(String firstName, String lastName, String idCard, String address,
                  String email, String homePhone, String mobilePhone, byte[] signImage) {
        mFirstName = firstName;
        mLastName = lastName;
        mIdCard = idCard;
        mAddress = address;
        mEmail = email;
        mHomePhone = homePhone;
        mMobilePhone = mobilePhone;
        mSignImage = signImage;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getIdCard() {
        return mIdCard;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getHomePhone() {
        return mHomePhone;
    }

    public String getMobilePhone() {
        return mMobilePhone;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public void setIdCard(String idCard) {
        mIdCard = idCard;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public void setHomePhone(String homePhone) {
        mHomePhone = homePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        mMobilePhone = mobilePhone;
    }

    public byte[] getSignImage() {
        return mSignImage;
    }

    public void setSignImage(byte[] signImage) {
        mSignImage = signImage;
    }


    @Override
    public String getLabel() {
        return mFirstName + " " + mLastName;
    }
}
