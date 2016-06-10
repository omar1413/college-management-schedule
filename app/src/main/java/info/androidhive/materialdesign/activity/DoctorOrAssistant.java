package info.androidhive.materialdesign.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by Omar on 5/5/2016.
 */
public class DoctorOrAssistant {

    private DoctorOrAssistant() {

    }

    static public boolean isDoctor(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("checkType", Context.MODE_PRIVATE);

        String type = sharedPreferences.getString("type", "");
        if (type.equals("doctor"))
            return true;
        return false;
    }

    static public boolean isAssistant(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("checkType", Context.MODE_PRIVATE);
        String type = sharedPreferences.getString("type", "");
        if (type.equals("assistant"))
            return true;
        return false;
    }
}
