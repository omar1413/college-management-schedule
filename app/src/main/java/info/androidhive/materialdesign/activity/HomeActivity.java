package info.androidhive.materialdesign.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import info.androidhive.materialdesign.R;

public class HomeActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = LogoActivity.class.getSimpleName();
    SharedPreferences sharedPreferences;

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPreferences = getSharedPreferences("checkType",MODE_PRIVATE);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle(getString(R.string.app_name));

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch

        //displayView(0);



    }


    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Fragment fragment = null;
        String title =getString(R.string.app_name);
        int icon=R.drawable.ic_number1;
        switch (position) {
            case 0:
                editor.putString("type","student");
                editor.commit();
                startActivity(new Intent(this, StudentSignUp.class));
                break;
            case 1:
                editor.putString("type","doctor");
                editor.commit();
                startActivity(new Intent(this, login_doctor.class));
                break;
            case 2:
                editor.putString("type","assistant");
                editor.commit();
                startActivity(new Intent(this, login_doctor.class));
                break;
            default:
                break;
        }

//        if (fragment != null) {
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.addToBackStack("");
//            fragmentTransaction.replace(R.id.container_body, fragment);
//            fragmentTransaction.commit();
//
//            // set the toolbar title
//            getSupportActionBar().setIcon(icon);
//            getSupportActionBar().setTitle(title);
//        }
    }
}