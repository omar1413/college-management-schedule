package info.androidhive.materialdesign.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import butterknife.InjectView;
import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.model.Schedule;

/**
 * Created by Omar on 5/2/2016.
 */
public class FragmentDetails extends Fragment {

    @InjectView(R.id.textViewSection)
    TextView textViewSection;
    @InjectView(R.id.textViewGroup)
    TextView textViewGroup;
    @InjectView(R.id.textViewYear)
    TextView textViewYear;
    @InjectView(R.id.textViewSubject)
    TextView textViewSubject;
    @InjectView(R.id.textViewDay)
    TextView textViewDay;
    @InjectView(R.id.textViewBegin)
    TextView textViewBegin;
    @InjectView(R.id.textViewEnd)
    TextView textViewEnd;
    @InjectView(R.id.textViewHall)
    TextView textViewHall;

    @InjectView(R.id.section) TextView section;



    Communicator communicator;
    private Schedule assistant;

    String lectureOrSection = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.details_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.notifyAction) {
            communicator.sendData(ActivityNotification.class);
        } else if (item.getItemId() == R.id.cancelAction) {

            if(DoctorOrAssistant.isAssistant(getActivity())){
                lectureOrSection = "section";
            }
            else {
                lectureOrSection = "lecture";
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Attention !!");
            builder.setMessage("are you want to cancel this " + lectureOrSection);
            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.sendNotification("Attention !!", lectureOrSection + " has been cancelled", null, assistant);
                    Toast.makeText(getActivity(), lectureOrSection + "has been cancelled", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.create().show();

        }
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.inject(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void setLayout(Schedule assistant) {
        this.assistant = assistant;
        textViewGroup.setText(assistant.getGroup());
        textViewYear.setText(assistant.getYear() + "");
        textViewSubject.setText(assistant.getSubject());
        textViewDay.setText(assistant.getDay());
        textViewBegin.setText(assistant.getBegin() + "");
        textViewEnd.setText(assistant.getEnd() + "");
        textViewHall.setText(assistant.getPlace());
        if (DoctorOrAssistant.isAssistant(getActivity())) {
            section.setVisibility(View.VISIBLE);
            textViewSection.setVisibility(View.VISIBLE);
            textViewSection.setText(assistant.getSection());
        } else
        {
            section.setVisibility(View.GONE);
            textViewSection.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicator = (Communicator) context;
    }

    public interface Communicator {
        void sendData(Class activity);
    }
}
