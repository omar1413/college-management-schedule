package info.androidhive.materialdesign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.model.Schedule;

/**
 * Created by Omar on 5/1/2016.
 */
public class ActivityNotification extends AppCompatActivity{
    

    Schedule assistant;
    FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Schedule.assistant_key);
        assistant = (Schedule) bundle.getSerializable(Schedule.assistant_key);
        manager = getSupportFragmentManager();
        FragmentNotification fragmentNotification = (FragmentNotification) manager.findFragmentById(R.id.fragmentNotification);
        fragmentNotification.setAssistant(assistant);

    }


}
