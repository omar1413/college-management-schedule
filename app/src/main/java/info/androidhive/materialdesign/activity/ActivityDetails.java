package info.androidhive.materialdesign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.model.Schedule;

/**
 * Created by Omar on 5/2/2016.
 */
public class ActivityDetails extends AppCompatActivity implements FragmentDetails.Communicator{

    Schedule assistant;
    FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

       Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_details);
       setSupportActionBar(myToolbar);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Schedule.assistant_key);
        assistant = (Schedule) bundle.getSerializable(Schedule.assistant_key);
        manager = getSupportFragmentManager();
        FragmentDetails fragmentDetails = (FragmentDetails) manager.findFragmentById(R.id.fragmentDetails);
        fragmentDetails.setLayout(assistant);
    }

    @Override
    public void sendData( Class activity) {
        Intent intent = new Intent(this, activity);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Schedule.assistant_key,  assistant);
        intent.putExtra(Schedule.assistant_key, bundle);
        startActivity(intent);
    }
}
