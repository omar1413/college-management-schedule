package info.androidhive.materialdesign.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.model.Schedule;

/**
 * Created by Omar on 5/1/2016.
 */
public class FragmentNotification extends Fragment  implements View.OnClickListener {

    @InjectView(R.id.titleEditText) EditText titleEditText;
    @InjectView(R.id.messageEditText) EditText messageEditText;
    @InjectView(R.id.linkEditText) EditText linkEditText;
    @InjectView(R.id.sendButton) Button sendButton;
    @InjectView(R.id.linear) LinearLayout l;
    @InjectView(R.id.backgroundImageView)ImageView backgroundImageView;

    Schedule assistant;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.inject(this, v);
        sendButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Glide.with(this).load(R.drawable.fci).into(backgroundImageView);
    }

    public boolean isValidUrl() {
        return Patterns.WEB_URL.matcher(linkEditText.getText().toString()).matches();
    }

    public boolean isDataFilled(){
        if (titleEditText.getText().toString().trim().length() == 0){
            return false;
        }
        if (messageEditText.getText().toString().trim().length() == 0){
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {

        if(!isDataFilled()){
            Toast.makeText(getActivity(), "please fill required fields", Toast.LENGTH_SHORT).show();
        }
        else if(linkEditText.getText().toString().trim().length() != 0 && !isValidUrl()){
            Toast.makeText(getActivity(), "invalid url", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getActivity(), "notification sent", Toast.LENGTH_SHORT).show();
            String title = titleEditText.getText().toString();
            String message = messageEditText.getText().toString();
            String link = linkEditText.getText().toString();
            Utils.sendNotification(title, message, link, assistant);

            getActivity().finish();
        }
    }

    public void setAssistant(Schedule assistant){
        this.assistant = assistant;
    }


}
