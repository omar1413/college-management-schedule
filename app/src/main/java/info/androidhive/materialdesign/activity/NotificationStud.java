package info.androidhive.materialdesign.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.NotificationAdapter;
import info.androidhive.materialdesign.model.NonScrollListView;

/**
 * Created by Abed Eid on 01/05/2016.
 */
public class NotificationStud extends Fragment implements AdapterView.OnItemSelectedListener {
    String Group, Year, Section;
    Firebase firebase;
    //    Notify data;
    //    List<Notify> notifies;
   NonScrollListView list;
    NotificationAdapter notificationAdapter;
    public static final String DEFAULT = "default";
    List<String> txt;
    String d;
    List<String> dlist;
    ProgressDialog myDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        myDialog = ProgressDialog.show(getActivity(), "Please wait ...", "Loading ...", true);
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("users", Context.MODE_PRIVATE);
        Group = sharedPreferences.getString("Group", DEFAULT);
        Section = sharedPreferences.getString("Section", DEFAULT);
        Year = sharedPreferences.getString("Year", DEFAULT);
        firebase.setAndroidContext(getActivity());
        firebase = new Firebase("https://fci-kit.firebaseio.com/notification");
//        data = new Notify();             ////////////////////////////////////////
        dlist = new ArrayList<String>();
        txt = new ArrayList<>();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View b = inflater.inflate(R.layout.fragment_notificationstud, container, false);

        // list = (ListView) getActivity().findViewById(R.id.list1);
        list = (NonScrollListView) b.findViewById(R.id.listNostud);
        Firebase notification = firebase.child("");
        ChildEventListener childEventListener = notification.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, String> m = dataSnapshot.getValue(Map.class);
                if (m.get("group").equals(Group) && m.get("year").equals(Year) && m.get("section").equals(Section)) {
                    d = (m.get("title").toString()) + "title " + (m.get("subject").toString()) + "subject " + (m.get("doctor").toString()) + "doctor " + (m.get("description").toString()) + "description" + (m.get("link").toString()) + " link";//
                    txt.add(m.get("link"));
                    dlist.add(d);
                    notificationAdapter.notifyDataSetChanged();
                }

                myDialog.dismiss();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        notificationAdapter = new NotificationAdapter(getActivity(), dlist);
        list.setAdapter(notificationAdapter);


        return b;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                if (!txt.get(position).equals("")) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(""));
                    i.setData(Uri.parse(txt.get(position) + ""));
                    startActivity(i);
                }
            }
        });
    }
}
