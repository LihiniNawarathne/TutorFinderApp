package com.example.tutorfinder.StudentUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tutorfinder.MainUI.LoginActivity;
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
        setContentView(R.layout.activity_student_dashboard);

        //actionbar and it's title
        actionBar = getSupportActionBar();
       // actionBar.setTitle("Notifications");

        mAuth = FirebaseAuth.getInstance();

        //bottom navigation:show menu
        BottomNavigationView navigationViewStudent = findViewById(R.id.bottom_navigation);
        navigationViewStudent.setOnNavigationItemSelectedListener(selectedListener);

        //default action
        actionBar.setTitle("Notifications");//change action title
        NotificationFragmentStudent fragment1 = new NotificationFragmentStudent();
        FragmentTransaction fr1 = getSupportFragmentManager().beginTransaction();
        fr1.replace(R.id.navContent,fragment1 ,"");
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
                            actionBar.setTitle("Notifications");//change action title
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

    //inflate option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflating meny
        getMenuInflater().inflate(R.menu.menu_main_opt,menu);
        return super.onCreateOptionsMenu(menu);
    }


    //handle menu item click logout option
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //get item id
        int id = item.getItemId();


        if(id==R.id.logoutoption){

            FirebaseAuth.getInstance().signOut();

            Toast.makeText(this, "Successfully logged out", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}