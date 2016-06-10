package info.androidhive.materialdesign.activity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import info.androidhive.materialdesign.R;


public class ProfessorFragment extends AppCompatActivity {

    Button AuthicatCode;
    //SharedPreferences sharedPreferences;

    public ProfessorFragment() {
        // Required empty public constructor
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //View rootView = inflater.inflate(R.layout.fragment_professor, container, false);
        setContentView(R.layout.fragment_professor);
        Firebase.setAndroidContext(this);
        final Firebase firebase = new Firebase("https://fci-kit.firebaseio.com/");
        final EditText password = (EditText) findViewById(R.id.verifyDoctorpassword);
        AuthicatCode = (Button) findViewById(R.id.loginButton);



        AuthicatCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String child = null;
                if (DoctorOrAssistant.isDoctor(ProfessorFragment.this)) {
                    child = "verify_doctor";

                    //Toast.makeText(this, "if", Toast.LENGTH_SHORT).show();
                } else if (DoctorOrAssistant.isAssistant(ProfessorFragment.this)) {
                    child = "verify_assistant";
                   // Toast.makeText(getActivity(), "else if", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Toast.makeText(getActivity(), "else", Toast.LENGTH_SHORT).show();
                    ///////////////////// student intent  to statrt student activity
                    return;
                }
                firebase.child(child).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Toast.makeText(ProfessorFragment.this, "data >> " + dataSnapshot.getValue(String.class), Toast.LENGTH_SHORT).show();
                        if (password.getText().toString().equals(dataSnapshot.getValue(String.class))) {
                            Intent intent = new Intent(ProfessorFragment.this, create_account_doctor.class);
                            startActivity(intent);

                        } else {
                            AlertDialog.Builder build = new AlertDialog.Builder(ProfessorFragment.this);
                            build.setTitle("Sucssesful");
                            build.setMessage("Verification Fail");
                            build.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            AlertDialog dialog = build.create();
                            dialog.show();
                        }

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });

    }




}

