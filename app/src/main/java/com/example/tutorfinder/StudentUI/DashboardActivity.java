package com.example.tutorfinder.StudentUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.tutorfinder.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //actionbar and it's title
        actionBar = getSupportActionBar();
        actionBar.setTitle("Notifications");

        //bottom navigation:show menu
        BottomNavigationView navigationViewStudent = findViewById(R.id.bottom_navigation);
        navigationViewStudent.setOnNavigationItemSelectedListener(selectedListener);

        //notification fragment transaction(default on start)
        actionBar.setTitle("Notification");//change action title
        NotificationFragmentStudent nfragment = new NotificationFragmentStudent();//NotificationStudent nfragment = new NotificationStudent();
        FragmentTransaction fr1 = getSupportFragmentManager().beginTransaction();
        fr1.replace(R.id.navContent,nfragment,"");
        fr1.commit() ;

    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    //handle item clicks

                    switch (menuItem.getItemId()){

                        case R.id.nav_notification:
                            //notification fragment transaction
                            actionBar.setTitle("Notification");//change action title
                            NotificationFragmentStudent nfragment = new NotificationFragmentStudent();//NotificationStudent nfragment = new NotificationStudent();
                            FragmentTransaction fr1 = getSupportFragmentManager().beginTransaction();
                            fr1.replace(R.id.navContent,nfragment,"");
                            fr1.commit() ;

                            return true;

                        case R.id.nav_chat:
                            //chat fragment transaction

                            actionBar.setTitle("Chat");//change action title
                            ChatFragmentStudent cfragment = new ChatFragmentStudent();
                            FragmentTransaction fr2 = getSupportFragmentManager().beginTransaction();
                            fr2.replace(R.id.navContent,cfragment,"");
                            fr2.commit();
                            return true;

                        case R.id.nav_profile:
                            //profile fragment transaction
                            actionBar.setTitle("Profile");//change action title
                            ChatFragmentStudent pfragment = new ChatFragmentStudent();
                            FragmentTransaction fr3 = getSupportFragmentManager().beginTransaction();
                            fr3.replace(R.id.navContent,pfragment,"");
                            fr3.commit();
                            return true;

                        case R.id.nav_search:
                            //search fragment transaction
                            actionBar.setTitle("Search");//change action title
                            ChatFragmentStudent sfragment = new ChatFragmentStudent();
                            FragmentTransaction fr4 = getSupportFragmentManager().beginTransaction();
                            fr4.replace(R.id.navContent,sfragment,"");
                            fr4.commit();
                            return true;

                    }


                    return false;
                }
            };

}