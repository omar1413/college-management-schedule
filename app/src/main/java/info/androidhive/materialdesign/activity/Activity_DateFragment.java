package info.androidhive.materialdesign.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import info.androidhive.materialdesign.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class Activity_DateFragment extends Fragment implements View.OnClickListener {

    @InjectView(R.id.datePicker)
    DatePicker datePicker;
    @InjectView(R.id.button)
    Button b;

    String now;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_date, container, false);
        ButterKnife.inject(this, v);
        now = getDateFromDatePicker(datePicker) + "";
        b.setOnClickListener(this);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        Date d = getDateFromDatePicker(datePicker);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("date", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("chosenDay",(String) DateFormat.format("EEEE", d));
        editor.commit();
        getActivity().finish();
    }

    public static Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }





}
