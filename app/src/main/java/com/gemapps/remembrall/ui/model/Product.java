package com.gemapps.remembrall.ui.model;

import io.realm.RealmObject;

/**
 * Created by edu on 8/11/16.
 */

public class Product extends RealmObject {

    private String mEquipLabel;
    private String mEquipNum;
    private String mTesterNum;
    private String mTerminalNum;
    private String mPrice;
    private String mDescription;

    public Product() {}

    public Product(String equipLabel, String equipNum, String testerNum, String terminalNum,
                   String price, String description) {
        mEquipLabel = equipLabel;
        mEquipNum = equipNum;
        mTesterNum = testerNum;
        mTerminalNum = terminalNum;
        mPrice = price;
        mDescription = description;
    }

    public String getEquipLabel() {
        return mEquipLabel;
    }

    public String getEquipNum() {
        return mEquipNum;
    }

    public String getTesterNum() {
        return mTesterNum;
    }

    public String getTerminalNum() {
        return mTerminalNum;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getDescription() {
        return mDescription;
    }
}
