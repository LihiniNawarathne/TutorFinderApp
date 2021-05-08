package com.example.tutorfinder.MainUI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.tutorfinder.R;
import com.example.tutorfinder.StudentUI.registerStudent1;
import com.example.tutorfinder.TutorUI.registerTutor;

public class registerUserSelect extends AppCompatActivity {
    //view
    ImageButton tutor_btn,student_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user_select);

        //set action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Select The Role");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        tutor_btn = findViewById(R.id.imgbtnTutor);
        student_btn = findViewById(R.id.imgbtnStudent);

        //navigate to Student SIgnUp page
        student_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(registerUserSelect.this, registerStudent1.class ));
            }
        });

        tutor_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(registerUserSelect.this, registerTutor.class ));
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        //navigate to previous activity
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}