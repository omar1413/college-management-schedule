package info.androidhive.materialdesign.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Date;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.activity.DoctorOrAssistant;
import info.androidhive.materialdesign.model.Schedule;

/**
 * Created by Mohamed Yossif on 30/04/2016.
 */

public class TaskListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Schedule> schedual;
    private static LayoutInflater inflater=null;
    private long currenttime;
    private SharedPreferences sharedPreference;
    private TextView subject;
    private TextView year;
    private TextView group;
    private TextView begin;
    private TextView place;
    private TextView sec_num;
    private ImageView list_img;
    private TextView textViewTimer;
    private TextView textViewTupe;
    private Date date;
    private TextView textViewDoctor;
    private ImageView imageGo;



    public TaskListAdapter(Context context, ArrayList<Schedule> schedule) {
        this.context = context;
        this.schedual =schedule;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return schedual.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row,parent,false);



        init(vi);
        fillViews(position);

        return vi;
    }



  public long getTimeMin(double schedual_assistanttime)
  {



      int h = ((int)schedual_assistanttime);
      int m = (int)((schedual_assistanttime - h)*100);
      return (h*60+m);


  }

    public String timeFormate(long M){

        int tH =(int)M/60;
        long tM = M - (tH*60);
        String timeInView = null;
        if(tH < 10 && tM < 10) timeInView = "0"+tH+":0"+tM;
        else if(tH < 10 && tM > 10)timeInView ="0"+tH+":"+tM;
        else if(tH > 10 && tM < 10)timeInView = tH+":0"+tM;
        else  timeInView = tH+":"+tM;


        return  timeInView;


    }



    public void init(View vi){


        date = new Date();
        currenttime =(date.getHours()*60)+(date.getMinutes());


        sharedPreference = context.getSharedPreferences("date", Context.MODE_PRIVATE);



        subject        =   (TextView)vi.findViewById(R.id.subject);
        year           =   (TextView)vi.findViewById(R.id.year);
        group          =   (TextView)vi.findViewById(R.id.group);
        begin          =   (TextView)vi.findViewById(R.id.begin);
        place          =   (TextView)vi.findViewById(R.id.place);
        sec_num        =   (TextView)vi.findViewById(R.id.sec_num);
        list_img       =   (ImageView)vi.findViewById(R.id.list_image);
        textViewTimer  =   (TextView)vi.findViewById(R.id.textViewtimer);
        textViewTupe   =   (TextView)vi.findViewById(R.id.textViewType);
        textViewDoctor =   (TextView)vi.findViewById(R.id.textViewDoctor);
        imageGo        =   (ImageView)vi.findViewById(R.id.list_image_go);

    }



    public void fillViews(int position) {

        if(!schedual.get(position).getSection().equals(""))
        sec_num.setText("Sec"+schedual.get(position).getSection());
        subject.setText(schedual.get(position).getSubject());
        year.setText(Integer.toString(schedual.get(position).getYear()));
        group.setText( schedual.get(position).getGroup());
        place.setText(schedual.get(position).getPlace());


        isStudent(position);
        setBeginTimeViewInFormet(position);
        setList_imgView(position);

    }


    public  void  isStudent(int position) {

            if (!DoctorOrAssistant.isAssistant(context) && !DoctorOrAssistant.isDoctor(context)) {

                if (schedual.get(position).getSection().equals(""))
                    textViewTupe.setText("lecture");
                else textViewTupe.setText("Section");


                textViewDoctor.setText(schedual.get(position).getDoctor());

                Glide.with(context).load(R.drawable.ac).into(imageGo);

            }


        }



    public void setBeginTimeViewInFormet(int position){


        String timeForm;
        if(getTimeMin(schedual.get(position).getBegin())  > 12*60) {

            timeForm = timeFormate(getTimeMin(schedual.get(position).getBegin()))+"PM";
        }else
        {
            timeForm = timeFormate(getTimeMin(schedual.get(position).getBegin()))+"AM";
        }

        begin.setText(timeForm);

    }


    public void  setList_imgView(int position){


        //past Subjects


        if(getTimeMin(schedual.get(position).getEnd()) <= currenttime &&
                DateFormat.format("EEEE", date).toString().toLowerCase().equals(sharedPreference.getString("chosenDay", "").toLowerCase())) {
            list_img.setImageResource(R.drawable.radiobutto_next);

            textViewTimer.setText("");

        }

        //now Subject

        else if(getTimeMin(schedual.get(position).getBegin()) <= currenttime &&
                DateFormat.format("EEEE", date).toString().toLowerCase().equals(sharedPreference.getString("chosenDay", "").toLowerCase())) {

            list_img.setImageResource(R.drawable.radiobutton_now);
            textViewTimer.setText(timeFormate(getTimeMin(schedual.get(position).getEnd()) - currenttime));



        }

        //next  Subjects
        else {

            list_img.setImageResource(R.drawable.radiobutton_past);
            textViewTimer.setText("");

        }



    }



}
