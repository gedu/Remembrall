package com.gemapps.remembrall.ui.widget.searchtext;

import android.support.design.widget.TextInputEditText;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;

import java.lang.ref.WeakReference;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by edu on 2/2/17.
 */

public class SearchPopupHelper implements SearchTextWatcher.SearchWatcherListener,
        PopupMenu.OnMenuItemClickListener{

    public interface SearchPopupListener {
        void onItemClick(RealmObject selected);
    }

    private WeakReference<TextInputEditText> mTextWeakReference;
    private SearchPopupListener mListener;
    private RealmResults mResults;
    private SearchTextWatcher mSearchTextWatcher;

    public SearchPopupHelper(TextInputEditText textInputView, Class toSearch, String fieldToSearch,
                             SearchPopupListener listener) {
        mTextWeakReference = new WeakReference<>(textInputView);
        mListener = listener;
        mSearchTextWatcher = new SearchTextWatcher(toSearch, fieldToSearch, this);
        mTextWeakReference.get().addTextChangedListener(mSearchTextWatcher);
    }

    @Override
    public void onSearchUpdate(RealmResults searchResults) {
        mResults = searchResults;
        displayProductPopupMenu();
    }

    private void displayProductPopupMenu(){

        if(mTextWeakReference.get() == null) return;

        PopupMenu popupMenu = new PopupMenu(mTextWeakReference.get().getContext(),
                mTextWeakReference.get());
        Menu menu = popupMenu.getMenu();
        for (int i = 0; i < mResults.size(); i++){
            menu.add(0, i, i, ((Searcheable)mResults.get(i)).getLabel());
        }
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        RealmModel model = mResults.get(item.getItemId());
        if(mListener != null) mListener.onItemClick((RealmObject) model);
        return false;
    }

    public void onDestroy(){
        mSearchTextWatcher.onDestroy();
    }
}
