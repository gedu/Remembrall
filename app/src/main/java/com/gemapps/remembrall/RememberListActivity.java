package com.gemapps.remembrall;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;

import com.gemapps.remembrall.ui.ButterActivity;
import com.gemapps.remembrall.ui.creation.CreationActivity;
import com.gemapps.remembrall.ui.model.Job;
import com.gemapps.remembrall.ui.model.RememberAlarm;
import com.gemapps.remembrall.ui.model.bus.DbTransaction;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class RememberListActivity extends ButterActivity {

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remember_list);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataBaseMessageEvent(DbTransaction dbTransaction){

        if(dbTransaction.getType() == DbTransaction.SAVE){
            openCalendar(dbTransaction.getJob());
            Snackbar.make(mCoordinatorLayout,
                    R.string.save_success_msg,
                    BaseTransientBottomBar.LENGTH_LONG)
                    .show();
        }
    }

    private void openCalendar(Job job) {
      RememberAlarm alarm = job.getDeliveries().get(0).getAlarm();
      Intent intent = new Intent(Intent.ACTION_INSERT);
      intent.setData(CalendarContract.Events.CONTENT_URI)
          .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, alarm.getEndDate())
          .putExtra(CalendarContract.Events.TITLE,
              getString(R.string.deliver_mg_to, job.getClient().getFormattedName()))
          .putExtra(CalendarContract.Events.EVENT_LOCATION, job.getClient().getAddress())
          .putExtra(Intent.EXTRA_EMAIL, job.getClient().getEmail());

      startActivity(intent);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_remember_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    public void onFabClicked(){
        startActivity(new Intent(this, CreationActivity.class));
    }
}
