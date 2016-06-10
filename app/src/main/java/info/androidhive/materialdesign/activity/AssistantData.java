package info.androidhive.materialdesign.activity;

/**
 * Created by Omar on 5/5/2016.
 */
public class AssistantData {

    private String begin, day, end, group,place, name, section, subject, year;

    //String type;
    public AssistantData() {

    }

    public AssistantData(String day, String subject, String name, String begin, String end, String hall, String group, String year, String section) {
        this.begin = begin;
        this.day = day;
        this.name = name;
        this.end = end;
        this.group = group;
        this.place = hall;
        this.subject = subject;
        this.year = year;
        this.section = section;
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

    public String getName() {
        return name;
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

    public String getSection() {
        return section;
    }

//    public void setType(String type){
//        this.type = type;
//    }


}
