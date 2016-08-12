package com.gemapps.remembrall.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gemapps.remembrall.R;
import com.gemapps.remembrall.ui.model.RememberAlarm;
import com.gemapps.remembrall.ui.model.Remembrall;
import com.gemapps.remembrall.util.DateUtil;

import org.json.JSONArray;

import butterknife.BindView;
import butterknife.OnClick;

import static com.gemapps.remembrall.ui.model.RememberAlarm.DEFAULT_ALARM_DESCRIPTION;
import static com.gemapps.remembrall.ui.model.RememberAlarm.DEFAULT_ALARM_LABEL;
import static com.gemapps.remembrall.ui.model.RememberAlarm.DEFAULT_ALARM_TYPE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreationFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressWarnings("private-access")
public class CreationFormFragment extends ButterFragment
        implements RemembrallCreationActivity.PickupDateListener {

    @BindView(R.id.form_first_name_edit) TextInputEditText mFirstNameEdit;
    @BindView(R.id.form_last_name_edit) TextInputEditText mLastNameEdit;
    @BindView(R.id.form_id_card_edit) TextInputEditText mIdCardEdit;
    @BindView(R.id.form_address_edit) TextInputEditText mAddressEdit;
    @BindView(R.id.form_email_edit) TextInputEditText mEmailEdit;
    @BindView(R.id.form_home_phone_edit) TextInputEditText mHomePhoneEdit;
    @BindView(R.id.form_mobile_phone_edit) TextInputEditText mMobilePhoneEdit;

    @BindView(R.id.form_start_day_text) TextView mStartDayText;
    @BindView(R.id.form_end_day_text) TextView mEndDayText;

    @BindView(R.id.form_equip_label_edit) TextInputEditText mEquipLabelEdit;
    @BindView(R.id.form_equip_num_edit) TextInputEditText mEquipNumEdit;
    @BindView(R.id.form_tester_num_edit) TextInputEditText mTesterNumEdit;
    @BindView(R.id.form_terminal_num_edit) TextInputEditText mTerminalNumEdit;
    @BindView(R.id.form_price_edit) TextInputEditText mPriceNumEdit;
    @BindView(R.id.form_description_edit) TextInputEditText mDescriptionEdit;

    private long mStartDate;
    private long mEndDate;
    //TODO: change the 30 to and get it from prefs
    private int mDaysToAdd = 30;
    private JSONArray mAlarms;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        ((RemembrallCreationActivity)getActivity()).addListener(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAlarms = new JSONArray();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return createView(inflater, container, R.layout.fragment_creation_form);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mStartDate = DateUtil.getDate();
        mEndDate = DateUtil.getDate(mDaysToAdd);

        setStartDayText();
        setEndDayText();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((RemembrallCreationActivity)getActivity()).addListener(null);
    }

    @OnClick(R.id.form_select_start_day_button)
    public void onPickStartDayClicked(){

        getActivity().startActivityForResult(new Intent(getActivity(), DatePickerActivity.class),
                RemembrallCreationActivity.REQUEST_START_DATE_RESULT);
    }

    @OnClick(R.id.form_select_end_day_button)
    public void onPickEndDayClicked(){
        Intent intent = new Intent(getActivity(), DatePickerActivity.class);

        intent.putExtra(DatePickerActivity.INTENT_EXTRA_DAYS_KEY, mDaysToAdd);
        getActivity().startActivityForResult(intent, RemembrallCreationActivity.REQUEST_END_DATE_RESULT);
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

        String equipLabel = mEquipLabelEdit.getText().toString();
        String equipNum = mEquipNumEdit.getText().toString();
        String testerNum = mTesterNumEdit.getText().toString();
        String terminalNum = mTerminalNumEdit.getText().toString();
        String price = mPriceNumEdit.getText().toString();
        String description = mDescriptionEdit.getText().toString();

        RememberAlarm alarm = new RememberAlarm(DEFAULT_ALARM_LABEL,
                DEFAULT_ALARM_DESCRIPTION, mStartDate, mEndDate, DEFAULT_ALARM_TYPE);
        addAlarm(alarm);

        Remembrall remembrall = new Remembrall(firstName, lastName, idCard, address, email,
                homePhone, mobilePhone, mAlarms, equipLabel, equipNum, testerNum,
                terminalNum, price, description);

//        remembrall.save(getActivity());
    }

    private void addAlarm(RememberAlarm alarm){

        mAlarms.put(alarm.convertTo());
    }

    private void setStartDayText(){
        mStartDayText.setText("Fecha entrega equipo: "+ DateUtil.formatDateFromTs(mStartDate));
    }

    private void setEndDayText(){
        mEndDayText.setText("Fecha busqueda equipo: "+DateUtil.formatDateFromTs(mEndDate));
    }

    @Override
    public void onStartDatePick(long ts) {

        mStartDate = ts;
        mEndDate = DateUtil.getDate(mDaysToAdd, ts);
        setStartDayText();
        setEndDayText();
    }

    @Override
    public void onEndDatePick(long ts) {
        mEndDate = ts;
        setEndDayText();
    }
}
