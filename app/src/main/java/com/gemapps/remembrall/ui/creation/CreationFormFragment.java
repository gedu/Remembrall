package com.gemapps.remembrall.ui.creation;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gemapps.remembrall.R;
import com.gemapps.remembrall.alarm.AlarmUpdateHandler;
import com.gemapps.remembrall.ui.ButterFragment;
import com.gemapps.remembrall.ui.DatePickerActivity;
import com.gemapps.remembrall.ui.model.Delivery;
import com.gemapps.remembrall.ui.model.Job;
import com.gemapps.remembrall.ui.model.RememberAlarm;
import com.gemapps.remembrall.ui.widget.FormUIHandler;
import com.gemapps.remembrall.util.DateUtil;

import butterknife.OnClick;
import io.realm.RealmList;

import static com.gemapps.remembrall.ui.model.RememberAlarm.DEFAULT_ALARM_DESCRIPTION;
import static com.gemapps.remembrall.ui.model.RememberAlarm.DEFAULT_ALARM_LABEL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreationFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressWarnings("private-access")
public class CreationFormFragment extends ButterFragment
        implements CreationActivity.PickupDateListener {
    private static final String TAG = "CreationFormFragment";

    private long mStartDate;
    private long mEndDate;
    private int mDaysToAdd = 30;//TODO: change the 30 to and get it from prefs
    private RealmList<Delivery> mDeliveries;
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
        ((CreationActivity) getActivity()).addListener(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDeliveries = new RealmList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = createView(inflater, container, R.layout.fragment_creation_form);
        mForm = new FormUIHandler(rootView, true);
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
        ((CreationActivity) getActivity()).addListener(null);
    }

    @OnClick(R.id.form_select_start_day_button)
    public void onPickStartDayClicked() {

        getActivity().startActivityForResult(new Intent(getActivity(), DatePickerActivity.class),
                CreationActivity.REQUEST_START_DATE_RESULT);
    }

    @OnClick(R.id.form_select_end_day_button)
    public void onPickEndDayClicked() {
        Intent intent = new Intent(getActivity(), DatePickerActivity.class);

        intent.putExtra(DatePickerActivity.INTENT_EXTRA_DAYS_KEY, mDaysToAdd);
        getActivity().startActivityForResult(intent, CreationActivity.REQUEST_END_DATE_RESULT);
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
        Job job = new Job(mForm, mDeliveries);
        AlarmUpdateHandler alarmUpdateHandler = new AlarmUpdateHandler(getActivity());
        alarmUpdateHandler.addAlarmAsync(job);
    }

    private void addDefaultAlarm(){

        final float price = Float.valueOf(mForm.getPriceFromView());
        RememberAlarm alarm = new RememberAlarm(DEFAULT_ALARM_LABEL,
                DEFAULT_ALARM_DESCRIPTION, mStartDate, mEndDate);
        addAlarm(new Delivery(alarm, price));
    }

    private void addAlarm(Delivery delivery) {

        mDeliveries.add(delivery);
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

    @Override
    public void onDestroy() {
        mForm.onDestroy();
        super.onDestroy();
    }
}
