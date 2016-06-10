package info.androidhive.materialdesign.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.concurrent.ExecutionException;

import info.androidhive.materialdesign.R;

public class login_doctor extends AppCompatActivity {
    TextView signuptextview;
    Button login;
    EditText username, password;
    Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_doctor);
        if (checkUser()) {

            Intent intent = new Intent(login_doctor.this,ScheduleActivity.class);
            startActivity(intent);
        } else {
            Firebase.setAndroidContext(this);
            firebase = new Firebase("https://fci-kit.firebaseio.com/");
            signuptextview = (TextView) findViewById(R.id.signUpText);
            login = (Button) findViewById(R.id.loginActivityButton);
            username = (EditText) findViewById(R.id.usernameLogin);
            password = (EditText) findViewById(R.id.passwordLogin);
            signuptextview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                       Intent i = new Intent(login_doctor.this, ProfessorFragment.class);
                    //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            });
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loginUser();
                }
            });
        }
    }

    private boolean checkUser() {
        SharedPreferences sharePrefernces = getSharedPreferences("users", Context.MODE_PRIVATE);
        String savedName = sharePrefernces.getString("name", "defaultname");
        String savedUsername = sharePrefernces.getString("username", "defaultusername");
        String savedPassword = sharePrefernces.getString("password", "defaultpassword");
        String savedType = sharePrefernces.getString("typeDoctor", "defaulttype");
        if ((!savedName.equals("defaultname")) && (!savedPassword.equals("defaultusername"))) {
            return true;
        }
        return false;
    }

    private void loginUser() {
        firebase.child("users").addValueEventListener(new ValueEventListener() {
            boolean flag = true;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    DataDoctorAccount doctorData = data.getValue(DataDoctorAccount.class);
                    if (username.getText().toString().equals(doctorData.getUsername()) && password.getText().toString().equals(doctorData.getPassword())) {
                        flag = false;
                        SharedPreferences sharePrefernces = getSharedPreferences("users", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharePrefernces.edit();
                        editor.putString("name", doctorData.getName());
                        editor.putString("username", username.getText().toString());
                        editor.putString("password", password.getText().toString());
                        editor.putString("typeDoctor", doctorData.getType());
                        editor.commit();


                        try {
                            if(!(new Utils.CheckForInternet(login_doctor.this).execute().get())){
                                Toast.makeText(login_doctor.this, "there is no connection", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(login_doctor.this,ScheduleActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
                if (flag) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(login_doctor.this);
                    builder.setTitle("Failed");
                    builder.setMessage("user name and password are incorrect");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }




            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

}
