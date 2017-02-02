package com.gemapps.remembrall.ui.model;

import com.gemapps.remembrall.ui.widget.searchtext.Searcheable;

import io.realm.RealmObject;

/**
 * Created by edu on 8/11/16.
 */

public class Product extends RealmObject implements Searcheable {

    private String mEquipLabel;
    private String mEquipNum;
    private String mTesterNum;
    private String mTerminalNum;
    private String mDescription;

    public Product() {}

    public Product(String equipLabel, String equipNum, String testerNum, String terminalNum,
                   String description) {
        mEquipLabel = equipLabel;
        mEquipNum = equipNum;
        mTesterNum = testerNum;
        mTerminalNum = terminalNum;
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

    public String getDescription() {
        return mDescription;
    }

    @Override
    public String getLabel() {
        return null;
    }
}
