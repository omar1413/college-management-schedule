package info.androidhive.materialdesign.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.firebase.client.Firebase;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import info.androidhive.materialdesign.model.Schedule;

/**
 * Created by Omar on 5/7/2016.
 */
public class Utils {

    public static void sendNotification(String title, String desc, String link, Schedule assistant){
        Map<String, String> m = new HashMap<>();
        m.put(NotificationInFirebase.TITLE,title);
        m.put(NotificationInFirebase.DESCRIPTION, desc);
        m.put(NotificationInFirebase.GROUP,assistant.getGroup() );
        m.put(NotificationInFirebase.SECTION, assistant.getSection());
        m.put(NotificationInFirebase.YEAR, assistant.getYear() + "");
        m.put(NotificationInFirebase.DOCTOR, assistant.getDoctor());
        m.put(NotificationInFirebase.SUBJECT, assistant.getSubject());

        if (link != null && link.length() != 0) {
            m.put("link", link);
        }
        else
        {
            m.put("link", "");
        }
        // m.put("link", "null");

        Firebase firebase = new Firebase("https://fci-kit.firebaseio.com/notification");
        Firebase newNotification = firebase.push();
        newNotification.setValue(m);
    }



    public static class CheckForInternet extends AsyncTask<Void,Void,Boolean>{
        Context context;
        public CheckForInternet(Context context){
            this.context = context;
        }
        @Override
        protected Boolean doInBackground(Void[] objects) {

            return hasActiveInternetConnection();
        }




            private boolean isNetworkAvailable() {
                ConnectivityManager connectivityManager
                        = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                return activeNetworkInfo != null;
            }

            public boolean hasActiveInternetConnection() {
                if (isNetworkAvailable()) {
                    try {
                        HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                        urlc.setRequestProperty("User-Agent", "Test");
                        urlc.setRequestProperty("Connection", "close");
                        urlc.setConnectTimeout(1500);
                        urlc.connect();
                        return (urlc.getResponseCode() == 200);
                    } catch (IOException e) {
                        return false;       ///Error checking internet connection
                    }
                }
                return false;  //////// No network available!

            }



    }
}
