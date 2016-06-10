package info.androidhive.materialdesign.model;

import java.io.Serializable;

/**
 * Created by Mohamed Yossif on 28/04/2016.
 */
public class Schedule implements Serializable {

    public static final String assistant_key = "assist";
    private String day;
    private String name;
    private String doctor;
    private String subject;
    private String place;
    private int year;
    private String group;
    private double begin;
    private double end;
    private String section="";


    public Schedule() {


    }

    public String getName() {
        return name;
    }

    public String getDay() {
        return day;
    }

    public String getDoctor() {
        if (name == null || name.length() == 0)
            return doctor;
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public String getPlace() {
        return place;
    }

    public int getYear() {
        return year;
    }

    public String getGroup() {
        return group;
    }

    public double getBegin() {
        return begin;
    }

    public double getEnd() {
        return end;
    }

    public String getSection() {
        return section;
    }
}
