package com.example.tutorfinder.AdminUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tutorfinder.R;

public class UserDetail extends AppCompatActivity {
    Button tutorBtn,stuBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_detail);
        tutorBtn = findViewById(R.id.AdtutorBtn);
        stuBtn = findViewById(R.id.AdstudentBtn);
        tutorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDetail.this, admin_view_tutors.class);
                startActivity(intent);
            }
        });
        stuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDetail.this,admin_view_students.class);
                startActivity(intent);
            }
        });
    }
}