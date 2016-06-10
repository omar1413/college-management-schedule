package info.androidhive.materialdesign.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import butterknife.ButterKnife;
import butterknife.InjectView;
import info.androidhive.materialdesign.R;

/**
 * Created by Omar on 5/5/2016.
 */
public class AssistantFragment extends Fragment {

    @InjectView(R.id.loginButton) Button AuthicatCode;
    @InjectView(R.id.verifyDoctorpassword)EditText password;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_professor, container, false);
        ButterKnife.inject(this, rootView);
        final Firebase firebase = new Firebase("https://fci-kit.firebaseio.com/");

        AuthicatCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebase.child("verify_assistant").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (password.getText().toString().equals(dataSnapshot.getValue(String.class))) {
                            Intent intent = new Intent(getActivity(), login_doctor.class);
                            startActivity(intent);
                        } else {
                            AlertDialog.Builder build = new AlertDialog.Builder(getActivity());
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
        return rootView;
    }
}
