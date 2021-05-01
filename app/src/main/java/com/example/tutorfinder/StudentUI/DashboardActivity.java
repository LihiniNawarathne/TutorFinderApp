package com.example.tutorfinder.StudentUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.tutorfinder.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {

    ActionBar actionBar;

    //firebase auth
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //actionbar and it's title
        actionBar = getSupportActionBar();
       // actionBar.setTitle("Notifications");

        mAuth = FirebaseAuth.getInstance();

        //bottom navigation:show menu
        BottomNavigationView navigationViewStudent = findViewById(R.id.bottom_navigation);
        navigationViewStudent.setOnNavigationItemSelectedListener(selectedListener);


        actionBar.setTitle("My Profile");//change action title
        ProfileFragmentStudent fragment3 = new ProfileFragmentStudent();
        FragmentTransaction fr3 = getSupportFragmentManager().beginTransaction();
        fr3.replace(R.id.navContent,fragment3,"");
        fr3.commit();

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
                            NotificationFragmentStudent fragment1 = new NotificationFragmentStudent();
                            FragmentTransaction fr1 = getSupportFragmentManager().beginTransaction();
                            fr1.replace(R.id.navContent,fragment1 ,"");
                            fr1.commit() ;

                            return true;

                        case R.id.nav_chat:
                            //chat fragment transaction

                            actionBar.setTitle("Chat");//change action title
                            ChatFragmentStudent fragment2 = new ChatFragmentStudent();
                            FragmentTransaction fr2 = getSupportFragmentManager().beginTransaction();
                            fr2.replace(R.id.navContent,fragment2,"");
                            fr2.commit();
                            return true;

                        case R.id.nav_profile:
                            //profile fragment transaction
                            actionBar.setTitle("My Profile");//change action title
                            ProfileFragmentStudent fragment3 = new ProfileFragmentStudent();
                            FragmentTransaction fr3 = getSupportFragmentManager().beginTransaction();
                            fr3.replace(R.id.navContent,fragment3,"");
                            fr3.commit();
                            return true;

                        case R.id.nav_search:
                            //search fragment transaction
                            actionBar.setTitle("Search");//change action title
                            SearchFragmentStudent fragment4 = new SearchFragmentStudent();
                            FragmentTransaction fr4 = getSupportFragmentManager().beginTransaction();
                            fr4.replace(R.id.navContent,fragment4,"");
                            fr4.commit();
                            return true;

                    }


                    return false;
                }
            };

}