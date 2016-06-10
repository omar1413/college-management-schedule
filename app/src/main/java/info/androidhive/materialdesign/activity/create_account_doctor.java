package info.androidhive.materialdesign.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.MySpinnerAdapter;

public class create_account_doctor extends AppCompatActivity {
    Button camera;
    Button singup, cancel;
    ImageView imageView;
    Uri mMediauri;
    Bitmap bitmap = null;
    EditText username, password, email;
    Spinner spinner;
    List<String> spinnerData, userDoctorDataNames;
    MySpinnerAdapter adapter;
    String doctorName;
    Firebase firebase;
    private static final int CAMERA_REQUEST = 1;
    private static final int GALLERY_REQUEST = 2;
    String child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_doctor);
        spinner = (Spinner) findViewById(R.id.spinner);
        singup = (Button) findViewById(R.id.signupBtn);
        cancel = (Button) findViewById(R.id.cancel);
        username = (EditText) findViewById(R.id.usernameFieldsingup);
        password = (EditText) findViewById(R.id.passwordFieldsingup);
        email = (EditText) findViewById(R.id.emailFieldsingup);
        spinnerData = new ArrayList<>();
        userDoctorDataNames = new ArrayList<>();
        Firebase.setAndroidContext(this);
        firebase = new Firebase("https://fci-kit.firebaseio.com");
        userDoctorName();
        child = null;
        if (DoctorOrAssistant.isDoctor(this)) {
            child = "schedule_doctor";
        } else if (DoctorOrAssistant.isAssistant(this)) {
            child = "assistant";
        }
        firebase.child(child).addValueEventListener(new ValueEventListener() {
            Set<String> s = new HashSet<String>();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (child.equals("schedule_doctor")) {
                        DoctorData user = data.getValue(DoctorData.class);
                        s.add(user.getDoctor());
//                        spinnerData.add(user.getDoctor());
                    } else if (child.equals("assistant")) {
                        AssistantData user = data.getValue(AssistantData.class);
                        s.add(user.getName());

//                        spinnerData.add(user.getName());
                    }


                    //Log.v("settttt", spinnerData + "");
                }
                spinnerData.addAll(s);

                userDoctorName();
                removeDuplicated();

                adapter = new MySpinnerAdapter(getBaseContext(), spinnerData, "choose your name");
                spinner.setAdapter(adapter);

//                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        doctorName =((TextView) view.findViewById(R.id.spinner_textView_item)).getText().toString();
//
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//                    }
//                });

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        camera = (Button) findViewById(R.id.Camerabutton);
        imageView = (ImageView) findViewById(R.id.profilePicture);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder build = new AlertDialog.Builder(create_account_doctor.this);
                build.setItems(R.array.camera_choices, mDialogListener);
                AlertDialog dialog = build.create();
                dialog.show();
            }
        });


    }


    protected DialogInterface.OnClickListener mDialogListener =
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i) {
                        case 0: // Take picture
                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, CAMERA_REQUEST);

                            break;

                        case 1: // Choose picture
                            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                            galleryIntent.setType("image/*");

                            startActivityForResult(galleryIntent, GALLERY_REQUEST);
                            break;

                    }
                }

            };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
        if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.profilePicture);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

    public void createUser() {

        if (username.getText().toString().equals("") || password.getText().toString().equals("") || spinner.getSelectedItem() == null) {
            AlertDialog.Builder build = new AlertDialog.Builder(create_account_doctor.this);
            build.setTitle("Fail");
            build.setMessage("UserName and Password can't be Empty");
            build.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = build.create();
            dialog.show();

        } else {
            doctorName = spinner.getSelectedItem().toString();
            String type = null;
            if(DoctorOrAssistant.isAssistant(this)){
                type = "assistant";
            }
            else if(DoctorOrAssistant.isDoctor(this)){
                type = "doctor";
            }
            DataDoctorAccount createUser = new DataDoctorAccount(doctorName, username.getText().toString(), password.getText().toString(), email.getText().toString(), type);
            firebase.child("users").push().setValue(createUser);
            SharedPreferences sharePrefernces = getSharedPreferences("users", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharePrefernces.edit();
            editor.putString("name", doctorName);
            editor.putString("username", username.getText().toString());
            editor.putString("password", password.getText().toString());
            editor.putString("typeDoctor", type);
            editor.commit();
            AlertDialog.Builder build = new AlertDialog.Builder(create_account_doctor.this);
            build.setTitle("Sucssesful");
            build.setMessage("your account sing up sucssesful");
            build.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(create_account_doctor.this,ScheduleActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            });
            AlertDialog dialog = build.create();
            dialog.show();
        }

    }

    public void userDoctorName() {
        firebase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    DataDoctorAccount user = data.getValue(DataDoctorAccount.class);
                    userDoctorDataNames.add(user.getName());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void removeDuplicated() {
        if (userDoctorDataNames != null && spinnerData != null) {
            for (int i = 0; i < userDoctorDataNames.size(); i++) {
                for (int j = 0; j < spinnerData.size(); j++) {
                    if (userDoctorDataNames.get(i).toString().equals(spinnerData.get(j).toString())) {
                        spinnerData.remove(j);
                    }
                }
            }
        }
    }

}
