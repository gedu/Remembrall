package com.gemapps.remembrall.ui.model;

/**
 * Created by edu on 8/10/16.
 */

public class Client {

    private String mFirstName;
    private String mLastName;
    private String mIdCard;
    private String mAddress;
    private String mEmail;
    private String mHomePhone;
    private String mMobilePhone;

    public Client(String firstName, String lastName) {
        mFirstName = firstName;
        mLastName = lastName;
    }

    public Client(String firstName, String lastName, String idCard, String address,
                  String email, String homePhone, String mobilePhone) {
        mFirstName = firstName;
        mLastName = lastName;
        mIdCard = idCard;
        mAddress = address;
        mEmail = email;
        mHomePhone = homePhone;
        mMobilePhone = mobilePhone;
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
}
