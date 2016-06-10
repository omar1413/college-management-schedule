package info.androidhive.materialdesign.adapter;

/**
 * Created by Abed Eid on 01/05/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import info.androidhive.materialdesign.R;

public class NotificationAdapter extends BaseAdapter  {
    private Context context;
    private LayoutInflater inflater;
    private List<String> Items;
    String m;
    String url;

    public NotificationAdapter(Context context, List<String> Items) {
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
            convertView = inflater.inflate(R.layout.notification_item, parent, false);
        TextView title = (TextView) convertView.findViewById(R.id.titleNotificationStud);
        TextView Sub = (TextView) convertView.findViewById(R.id.subjectStud);
        TextView des = (TextView) convertView.findViewById(R.id.DescriptionStud);
        TextView doctor = (TextView) convertView.findViewById(R.id.Doctor_name);
        TextView link = (TextView) convertView.findViewById(R.id.link);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.messageIcon);
        m = Items.get(position);
        String[] parts = (m.split("title"));
        title.setText(parts[0]);
        String[] part = parts[1].split("subject");
        Sub.setText(part[0].toString());
        String[] par = part[1].split("doctor");
        doctor.setText(par[0]);
        String[] pa = par[1].split("description");
        des.setText(pa[0]);
        String[] p = pa[1].split("link");
        link.setText(p[0]);
        url = p[0].trim();
        if (!p[0].trim().equals("")) {
            imageView.setImageResource(R.drawable.link);

        }


        return convertView;
    }



}
