package info.androidhive.materialdesign.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import info.androidhive.materialdesign.R;

public class MySpinnerAdapter implements SpinnerAdapter {

    int pos;
    Context context;
    List<String> spinnerString;
    String hint;
    Boolean dropedMenueHasOpend;           // to check that he has not choose any item and then no green color :)

    public MySpinnerAdapter(Context context, List<String> spinnerString, String hint) {
        this.context = context;
        this.spinnerString = spinnerString;
        pos = -1;
        this.hint = hint;
        dropedMenueHasOpend = false;
    }


    @Override
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        Holder h = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.spinner_item, viewGroup, false);
            h = new Holder(view);
            view.setTag(h);
        } else {
            h = (Holder) view.getTag();
        }
        h.textView.setText(spinnerString.get(i));
        Glide.with(context).load(R.drawable.radiobutton_past).into(h.imageView);
        if (pos != -1 && i == pos) {
            Glide.with(context).load(R.drawable.radiobutton_now).into(h.imageView);
        }
        dropedMenueHasOpend = true;
        return view;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return spinnerString.size();
    }

    @Override
    public Object getItem(int i) {
        if (pos == -1)
            return null;
        return spinnerString.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder h = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.spinner_item, viewGroup, false);
            h = new Holder(view);
            view.setTag(h);
        } else {
            h = (Holder) view.getTag();
        }
        h.textView.setText(hint);
        h.imageView.setVisibility(View.GONE);
        if (dropedMenueHasOpend) {
            pos = i;
        }
        return view;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }


    class Holder {
        ImageView imageView;
        TextView textView;

        public Holder(View v) {
            textView = (TextView) v.findViewById(R.id.spinner_textView_item);
            imageView = (ImageView) v.findViewById(R.id.spinner_imageView_item);
        }
    }
}