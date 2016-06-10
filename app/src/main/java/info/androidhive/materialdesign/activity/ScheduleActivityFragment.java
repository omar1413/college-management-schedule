package info.androidhive.materialdesign.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.TaskListAdapter;
import info.androidhive.materialdesign.model.NonScrollListView;
import info.androidhive.materialdesign.model.Schedule;

/**
 * A placeholder fragment containing a simple view.
 */
public class ScheduleActivityFragment extends Fragment {


    private Firebase ref;
    private TaskListAdapter taskListAdapter;
    private NonScrollListView listView;
    private ArrayList<Schedule> allschedule;
    private ArrayList<Schedule> todayschedule;


    private Handler handler;

    private SharedPreferences sharedPreference;
    private SharedPreferences sharedPreferenceUsers;
   // private SharedPreferences sharedPreferencesStud;

    // ImageView listBackground;

    public ScheduleActivityFragment() {
    }


    //initialization

    public void init(View view) {


        ref = new Firebase("https://fci-kit.firebaseio.com");

        allschedule = new ArrayList<>();
        todayschedule = new ArrayList<>();
        listView = (NonScrollListView) view.findViewById(R.id.listView);
        sharedPreference = getActivity().getSharedPreferences("date", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString("chosenDay", (String) DateFormat.format("EEEE", new Date()));
        editor.commit();
        sharedPreferenceUsers = getActivity().getSharedPreferences("users", Context.MODE_PRIVATE);



    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(getActivity());
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_scedule, container, false);

        init(view);
        choosenType();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (DoctorOrAssistant.isAssistant(getActivity()) || DoctorOrAssistant.isDoctor(getActivity())) {
                    Intent intent = new Intent(getActivity(), ActivityDetails.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Schedule.assistant_key, todayschedule.get(position));
                    intent.putExtra(Schedule.assistant_key, bundle);
                    startActivity(intent);
                }
                // intent

            }
        });






        return view;
    }


    @Override
    public void onResume() {
        super.onResume();


        // refreh list

        updateList();

        // Refresh title
        if (!DateFormat.format("EEEE", new Date()).toString().toLowerCase().equals(sharedPreference.getString("chosenDay", "").toLowerCase()))
            getActivity().setTitle(sharedPreference.getString("chosenDay", ""));
        else{
            getActivity().setTitle("Today");
            if (taskListAdapter != null) {
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        taskListAdapter.notifyDataSetChanged();
                        handler.postDelayed(this, 60 * 1000);
                    }
                }, 60 * 1000);


            }

        }

    }


    public void getData(String child, String key, String value) {


        ref.child(child).orderByChild(key).equalTo(value)

                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        for (DataSnapshot d : dataSnapshot.getChildren()) {

                            Schedule schedule = d.getValue(Schedule.class);

                            if (DoctorOrAssistant.isAssistant(getActivity()) || DoctorOrAssistant.isDoctor(getActivity())) {
                                allschedule.add(schedule);

                            } else {
                                if ((schedule.getSection().equals(sharedPreferenceUsers.getString("Section", "default")) ||
                                        schedule.getSection().equals(""))
                                        && schedule.getGroup().equals(sharedPreferenceUsers.getString("Group", "default")))
                                    allschedule.add(schedule);


                            }

                        }

                        updateList();


                    }


                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

    }


    public void updateList() {

        if (!todayschedule.isEmpty()) {
            todayschedule.clear();
        }


        if (!allschedule.isEmpty()) {

            for (Schedule s : allschedule) {
                if (s.getDay().toLowerCase().equals(sharedPreference.getString("chosenDay", "").toLowerCase())) {

                    todayschedule.add(s);
                }

                Collections.sort(todayschedule, new Comparator<Schedule>() {

                    @Override
                    public int compare(Schedule s1, Schedule s2) {
                        return (int) (s1.getBegin() - s2.getBegin());
                    }
                });

                taskListAdapter = new TaskListAdapter(getActivity(), todayschedule);
                listView.setAdapter(null);
                listView.setAdapter(taskListAdapter);
            }
        }


    }


    public void choosenType() {



        if (DoctorOrAssistant.isAssistant(getActivity())) {


            getData("assistant", "name", sharedPreferenceUsers.getString("name", "default"));

        } else if (DoctorOrAssistant.isDoctor(getActivity())) {



            getData("schedule_doctor", "doctor", sharedPreferenceUsers.getString("name", "default"));


        } else {



            //   sharedPreferencesStud = getActivity().getSharedPreferences("student", Context.MODE_PRIVATE);

            getData("assistant", "year", sharedPreferenceUsers.getString("Year", "default"));
            getData("schedule_doctor", "year", sharedPreferenceUsers.getString("Year", "default"));

        }


        //getData("schedule_assistant","doctor","israa");


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);


        inflater.inflate(R.menu.menu, menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.calender) {

            startActivity(new Intent(getActivity(), Activity_Date.class));


        } else if (id == R.id.log_out) {

                SharedPreferences sharePrefernces = getActivity().getSharedPreferences("users", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharePrefernces.edit();
                editor.clear();
                editor.commit();
                getActivity().finish();
                startActivity(new Intent(getActivity(), LogoActivity.class));
            }



        return true;
    }


}
