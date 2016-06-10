package info.androidhive.materialdesign.adapter;

/**
 * Created by Ravi on 29/07/15.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.model.NavDrawerItem;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavDrawerItem current = data.get(position);
        holder.title.setText(current.getTitle());
        switch (position){
            case 0:
                holder.imageView.setImageResource(R.drawable.ic_number1);
                break;
            case 1:
                holder.imageView.setImageResource(R.drawable.ic_number2);
                data.get(1).setShowNotify(false);
                break;
            case 2:
                holder.imageView.setImageResource(R.drawable.ic_number3);
                data.get(2).setShowNotify(false);

                break;
            case 3:
                holder.imageView.setImageResource(R.drawable.ic_number4);


                break;



        }
        if(data.get(0).isShowNotify()){

            holder.textcounter.setText(data.get(0).getCounter());
        }
        else{
            //hide the counter view
           holder. textcounter.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView imageView;
        TextView textcounter;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            textcounter= (TextView) itemView.findViewById(R.id.counter);


        }
    }
}
