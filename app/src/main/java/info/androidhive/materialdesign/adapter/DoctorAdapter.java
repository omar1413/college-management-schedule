package info.androidhive.materialdesign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import info.androidhive.materialdesign.R;

/**
 * Created by Abed Eid on 03/05/2016.
 */
public class DoctorAdapter extends BaseAdapter  {
    private Context context;
    private LayoutInflater inflater;
    private List<String> Items;
    String m;


    public DoctorAdapter(Context context, List<String> Items) {
        this.context = context;
        this.Items = Items;

    }

    @Override
    public int getCount() {
        return Items.size();
    }

    @Override
    public Object getItem(int location) {
        return Items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.doctor_item, parent, false);
        TextView username = (TextView) convertView.findViewById(R.id.Doctor_name);
        TextView email = (TextView) convertView.findViewById(R.id.doctor_email);
        m = Items.get(position);
        String[] parts = (m.split("username"));
        username.setText(parts[0]);
        String[] part = parts[1].split("email ");
        email.setText("Email :"+part[0].toString());


        return convertView;
    }



}
