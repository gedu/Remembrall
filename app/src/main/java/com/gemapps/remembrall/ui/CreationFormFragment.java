package com.gemapps.remembrall.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.gemapps.remembrall.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreationFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressWarnings("private-access")
public class CreationFormFragment extends ButterFragment {

    @BindView(R.id.form_first_name_edit) EditText mFirstNameEdit;
    @BindView(R.id.form_last_name_edit) EditText mLastNameEdit;
    @BindView(R.id.form_id_card_edit) EditText mIdCardEdit;
    @BindView(R.id.form_address_edit) EditText mAddressEdit;
    @BindView(R.id.form_email_edit) EditText mEmailEdit;
    @BindView(R.id.form_home_phone_edit) EditText mHomePhoneEdit;
    @BindView(R.id.form_mobile_phone_edit) EditText mMobilePhoneEdit;

    @BindView(R.id.form_start_day_text) TextView mStartDayText;
    @BindView(R.id.form_end_day_text) TextView mEndDayText;

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

    @OnClick(R.id.form_select_start_day_button)
    public void onPickStartDayClicked(){

        startActivityForResult(new Intent(getActivity(), DatePickerActivity.class),
                RemembrallCreationActivity.REQUEST_START_DATE_RESULT);
    }

    @OnClick(R.id.form_select_end_day_button)
    public void onPickEndtDayClicked(){
        Intent intent = new Intent(getActivity(), DatePickerActivity.class);
        intent.putExtra(DatePickerActivity.INTENT_EXTRA_DAYS_KEY, 30);
        startActivityForResult(intent, RemembrallCreationActivity.REQUEST_END_DATE_RESULT);
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
        String email = mEmailEdit.getText().toString();
        String homePhone = mHomePhoneEdit.getText().toString();
        String mobilePhone = mMobilePhoneEdit.getText().toString();
    }
}
