package com.gemapps.remembrall.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gemapps.remembrall.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreationFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreationFormFragment extends ButterFragment {

    @BindView(R.id.form_first_name_edit) private EditText mFirstNameEdit;
    @BindView(R.id.form_last_name_edit) private EditText mLastNameEdit;
    @BindView(R.id.form_id_card_edit) private EditText mIdCardEdit;
    @BindView(R.id.form_address_edit) private EditText mAddressEdit;

    public CreationFormFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment CreationFormFragment.
     */
    public static CreationFormFragment newInstance() {

        return new CreationFormFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return createView(inflater, container, R.layout.fragment_creation_form);
    }

    @OnClick(R.id.form_sign)
    public void onSignClicked(View view){
        startActivity(BaseCardActivity.getInstance(view, InkWritingActivity.class));
    }

    public void saveForm(){

        String firstName = mFirstNameEdit.getText().toString();
        String lastName = mLastNameEdit.getText().toString();
        String idCard = mIdCardEdit.getText().toString();
        String address = mAddressEdit.getText().toString();

    }
}
