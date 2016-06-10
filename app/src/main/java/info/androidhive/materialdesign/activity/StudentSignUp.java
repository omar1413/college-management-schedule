package info.androidhive.materialdesign.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.MySpinnerAdapter;

public class StudentSignUp extends AppCompatActivity {

    Spinner year, setion, group;
    EditText name, phone;
    String Uname, Uphone, Uyear, Usection, Ugroup;
    String Name, Group, Phone, Year, Section;
    public static final String DEFAULT = "default";

    private Button cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_up);

        cancel = (Button)findViewById(R.id.cancelStud);

        SharedPreferences sharedPreferences = getSharedPreferences("users", Context.MODE_PRIVATE);
        Name = sharedPreferences.getString("Name", DEFAULT);
        Phone = sharedPreferences.getString("Phone", DEFAULT);
        Group = sharedPreferences.getString("Group", DEFAULT);
        Section = sharedPreferences.getString("Section", DEFAULT);
        Year = sharedPreferences.getString("Year", DEFAULT);
        if (Name.equals(DEFAULT) || Phone.equals(DEFAULT) || Group.equals(DEFAULT) || Section.equals(DEFAULT) || Year.equals(DEFAULT)) {
            year = (Spinner) findViewById(R.id.spinner_year);
            String[] data = getResources().getStringArray(R.array.year);
            MySpinnerAdapter adapter = new MySpinnerAdapter(this, Arrays.asList(data), "year");
            year.setAdapter(adapter);


            group = (Spinner) findViewById(R.id.spinner_group);
            MySpinnerAdapter adapter2 = new MySpinnerAdapter(this, Arrays.asList(getResources().getStringArray(R.array.group)), "group");
            group.setAdapter(adapter2);

            setion = (Spinner) findViewById(R.id.spinner_Section);
            MySpinnerAdapter adapter3 = new MySpinnerAdapter(this, Arrays.asList(getResources().getStringArray(R.array.section)), "section");
            setion.setAdapter(adapter3);


            name = (EditText) findViewById(R.id.name);
            phone = (EditText) findViewById(R.id.phone);
        } else {
            Intent intent = new Intent(this,StudentTabs.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }




        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               finish();
            }
        });


    }

    public void go(View view) {

        if(group.getSelectedItem() == null || year.getSelectedItem() == null || setion.getSelectedItem() == null){
            Toast.makeText(this, "Enter full data", Toast.LENGTH_SHORT).show();
            return;
        }
        Uname = name.getText().toString();
        Uphone = phone.getText().toString();
        Ugroup = group.getSelectedItem().toString();
        Uyear = year.getSelectedItem().toString();
        Usection = setion.getSelectedItem().toString();

        if (Uname.equals("")) {
            Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
        } else if (Uphone.equals("")) {
            Toast.makeText(this, "Enter Phone ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, Ugroup +"/"+ Usection +"/"+ Uyear + "", Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences = getSharedPreferences("users", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Name", Uname);
            editor.putString("Phone", Uphone);
            editor.putString("Group", Ugroup);
            editor.putString("Section", Usection);
            editor.putString("Year", Uyear);
            editor.commit();

            Intent intent = new Intent(this, StudentTabs.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();



        }
    }
}