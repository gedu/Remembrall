package com.gemapps.remembrall.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gemapps.remembrall.R;
import com.gemapps.remembrall.ui.model.RememberAlarm;
import com.gemapps.remembrall.ui.model.Remembrall;
import com.gemapps.remembrall.ui.widget.FormUIHandler;
import com.gemapps.remembrall.util.DateUtil;

import butterknife.OnClick;
import io.realm.RealmList;

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
    private static final String TAG = "CreationFormFragment";

    private long mStartDate;
    private long mEndDate;
    private int mDaysToAdd = 30;//TODO: change the 30 to and get it from prefs
    private RealmList<RememberAlarm> mAlarms;
    private FormUIHandler mForm;

    public CreationFormFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CreationFormFragment.
     */
    public static CreationFormFragment newInstance() {
        return new CreationFormFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((RemembrallCreationActivity) getActivity()).addListener(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAlarms = new RealmList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = createView(inflater, container, R.layout.fragment_creation_form);
        mForm = new FormUIHandler(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mStartDate = DateUtil.getDate();
        mEndDate = DateUtil.getDate(mDaysToAdd);
        mForm.setLockScrollListener();
        setStartDayText();
        setEndDayText();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((RemembrallCreationActivity) getActivity()).addListener(null);
    }

    @OnClick(R.id.form_select_start_day_button)
    public void onPickStartDayClicked() {

        getActivity().startActivityForResult(new Intent(getActivity(), DatePickerActivity.class),
                RemembrallCreationActivity.REQUEST_START_DATE_RESULT);
    }

    @OnClick(R.id.form_select_end_day_button)
    public void onPickEndDayClicked() {
        Intent intent = new Intent(getActivity(), DatePickerActivity.class);

        intent.putExtra(DatePickerActivity.INTENT_EXTRA_DAYS_KEY, mDaysToAdd);
        getActivity().startActivityForResult(intent, RemembrallCreationActivity.REQUEST_END_DATE_RESULT);
    }

    @OnClick(R.id.redo_button)
    public void onRedoInkSign(){
        mForm.clearSignView();
    }

    @Override
    public void fabAction() {

        saveForm();
    }

    public void saveForm() {

        addDefaultAlarm();
        Remembrall remembrall = new Remembrall(mForm, mAlarms);
        remembrall.save();
    }

    private void addDefaultAlarm(){
        addAlarm(new RememberAlarm(DEFAULT_ALARM_LABEL,
                DEFAULT_ALARM_DESCRIPTION, mStartDate, mEndDate, DEFAULT_ALARM_TYPE));
    }

    private void addAlarm(RememberAlarm alarm) {

        mAlarms.add(alarm);
    }

    private void setStartDayText() {
        mForm.setStartDayText("Fecha entrega equipo: " + DateUtil.formatDateFromTs(mStartDate));
    }

    private void setEndDayText() {
        mForm.setEndDayText("Fecha busqueda equipo: " + DateUtil.formatDateFromTs(mEndDate));
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
