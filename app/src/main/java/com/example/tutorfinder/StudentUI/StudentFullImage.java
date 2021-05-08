package com.example.tutorfinder.StudentUI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tutorfinder.R;
import com.squareup.picasso.Picasso;

public class StudentFullImage extends AppCompatActivity {

    ImageView image;
    String imgurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_full_image);

        //set action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Selected Image");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);

        //get image
        imgurl = getIntent().getStringExtra("image") ;

        image=findViewById(R.id.ivfullImage);

        //set image
        Picasso.get().load(imgurl).into(image);
    }
    @Override
    public boolean onSupportNavigateUp() {
        //navigate to previous activity
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}