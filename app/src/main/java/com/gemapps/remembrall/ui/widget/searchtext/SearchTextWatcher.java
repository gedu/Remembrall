package com.gemapps.remembrall.ui.widget.searchtext;

import android.text.Editable;
import android.text.TextWatcher;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by edu on 2/1/17.
 */

public class SearchTextWatcher<T extends RealmModel> implements TextWatcher {

    private static final String TAG = "SearchTextWatcher";
    public interface SearchWatcherListener {
        void onSearchUpdate(RealmResults searchResults);
    }

    private Class mClassToSearch;
    private String mFieldToCompare;
    private SearchWatcherListener mListener;
    private Realm mRealm;

    public <E extends RealmObject> SearchTextWatcher(Class<E> classToSearch, String fieldToCompare,
                                                     SearchWatcherListener listener) {
        mClassToSearch = classToSearch;
        mFieldToCompare = fieldToCompare;
        mListener = listener;
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if(count == 0) return;

        RealmResults results = mRealm
                .where(mClassToSearch)
                .beginsWith(mFieldToCompare, s.toString(), Case.INSENSITIVE)
                .findAll();

        if (mListener != null && results.size() > 0) mListener.onSearchUpdate(results);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void onDestroy(){
        mRealm.close();
        mListener = null;
    }
}
