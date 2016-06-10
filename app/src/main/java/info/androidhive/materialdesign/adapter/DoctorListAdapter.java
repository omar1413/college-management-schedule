package info.androidhive.materialdesign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.activity.DoctorData;

/**
 * Created by SaMiR on 4/30/2016.
 */
public class DoctorListAdapter extends ArrayAdapter<DoctorData> {
    Context context;
    int resourse;
    List<DoctorData> list=new ArrayList<>();
    TextView title,day,time;

    public DoctorListAdapter(Context context, int resource, List<DoctorData> list) {
        super(context, resource, list);
        this.context=context;
        this.resourse=resource;
        this.list=list;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v= LayoutInflater.from(context).inflate(resourse,parent,false);
        title= (TextView) v.findViewById(R.id.subjectTitle);
        day= (TextView) v.findViewById(R.id.day);
        time= (TextView) v.findViewById(R.id.time);
        title.setText(list.get(position).getSubject());
        day.setText(list.get(position).getDay());
        time.setText(list.get(position).getBegin()+" : "+list.get(position).getEnd());
        return v;
    }
}
