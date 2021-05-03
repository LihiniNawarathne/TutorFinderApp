package com.example.tutorfinder.StudentUI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tutorfinder.R;

public class CourseOffersStudent extends AppCompatActivity {

    Button offerbtnMaths,offerbtnBio,offerbtnCommerce,offerbtnArt;
    String Stream;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_offers_student);

    }
}