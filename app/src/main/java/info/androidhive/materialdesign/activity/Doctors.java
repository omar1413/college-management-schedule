package info.androidhive.materialdesign.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.DoctorAdapter;
import info.androidhive.materialdesign.model.NonScrollListView;

/**
 * Created by Abed Eid on 03/05/2016.
 */
public class Doctors extends Fragment {
    Firebase firebase;
    NonScrollListView list;
    List<String> dlist;
    String d;
    DoctorAdapter doctorAdapter;
    ProgressDialog myDialog;
    List<String> txt;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        myDialog = ProgressDialog.show(getActivity(), "Please wait ...", "Loading ...", true);
        super.onCreate(savedInstanceState);
        firebase.setAndroidContext(getActivity());
        firebase = new Firebase("https://fci-kit.firebaseio.com/users");
        dlist = new ArrayList<String>();
        txt = new ArrayList<>();


    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View b = inflater.inflate(R.layout.fragment_docotrs, container, false);

        list = (NonScrollListView) b.findViewById(R.id.list_doctors);
        Firebase doctor = firebase.child("");
        ChildEventListener childEventListener = doctor.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, String> m = dataSnapshot.getValue(Map.class);
                d = (m.get("username").toString()) + "username " + (m.get("email").toString()) + "email  aaa";
                txt.add(m.get("email"));
                dlist.add(d);
                doctorAdapter.notifyDataSetChanged();
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

        doctorAdapter = new DoctorAdapter(getActivity(), dlist);
        list.setAdapter(doctorAdapter);


        return b;
    }

    @Override
    public void onStart() {
        super.onStart();
        dlist.clear();


    }
    @Override

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
//                Toast.makeText(getActivity(), txt.get(position) + "", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{txt.get(position)});
                i.putExtra(Intent.EXTRA_SUBJECT, "");
                i.putExtra(Intent.EXTRA_TEXT   , "");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });}
}
