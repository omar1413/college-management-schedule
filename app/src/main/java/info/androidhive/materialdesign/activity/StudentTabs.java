package info.androidhive.materialdesign.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.R;

public class StudentTabs extends AppCompatActivity {

    String Name, Group, Phone, Year, Section;
    public static final String DEFAULT = "default";
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_tabs);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        tabLayout.getTabAt(0).setIcon(R.drawable.ic_action_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_not);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_action_personal);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ScheduleActivityFragment(),null);
        adapter.addFragment(new NotificationStud(),null);
        adapter.addFragment(new Doctors(),null);

        viewPager.setAdapter(adapter);
    }




    public void created(View view) {

        SharedPreferences sharedPreferences = getSharedPreferences("users", Context.MODE_PRIVATE);
        Name = sharedPreferences.getString("Name", DEFAULT);
        Phone = sharedPreferences.getString("Phone", DEFAULT);
        Group = sharedPreferences.getString("Group", DEFAULT);
        Section = sharedPreferences.getString("Section", DEFAULT);
        Year = sharedPreferences.getString("Year", DEFAULT);
        if (Name.equals(DEFAULT) || Phone.equals(DEFAULT) || Group.equals(DEFAULT) || Section.equals(DEFAULT) || Year.equals(DEFAULT)) {

            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, Name + " " + Phone + " " + Group + " " + Section + " " + Year, Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_student, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.Account) {
            //check name phone

            SharedPreferences sharedPreferences = getSharedPreferences("users", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Name", DEFAULT);
            editor.putString("Phone", DEFAULT);
            editor.putString("Group", DEFAULT);
            editor.putString("Section", DEFAULT);
            editor.putString("Year", DEFAULT);
            editor.commit();
            Intent intent = new Intent(this, StudentSignUp.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }












    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }


        @Override
        public CharSequence getPageTitle(int position) {

            return mFragmentTitleList.get(position);
        }


    }




}
