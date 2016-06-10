package info.androidhive.materialdesign.activity;

/**
 * Created by SaMiR on 4/28/2016.
 */
public class DoctorData {

    private String begin,day,doctor,end,group,place,subject,year;
    //String type;
    public DoctorData() {

    }

    public DoctorData(String day,String subject, String doctor, String begin, String end, String place, String group, String year) {
        this.begin = begin;
        this.day = day;
        this.doctor = doctor;
        this.end = end;
        this.group = group;
        this.place = place;
        this.subject = subject;
        this.year = year;
    }

    public String getYear() {
        return year;
    }


    public String getSubject() {
        return subject;
    }

    public String getBegin() {
        return begin;
    }

    public String getDay() {
        return day;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getEnd() {
        return end;
    }

    public String getGroup() {
        return group;
    }

    public String getPlace() {
        return place;
    }

//    public void setType(String type){
//        this.type = type;
//    }
}
