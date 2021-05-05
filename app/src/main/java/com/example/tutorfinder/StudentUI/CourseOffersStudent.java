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

        //set action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Search Results");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);

        offerbtnMaths =findViewById(R.id.btnMaths);
        offerbtnBio =findViewById(R.id.btnBio);
        offerbtnCommerce =findViewById(R.id.btnCommerce);
        offerbtnArt =findViewById(R.id.btnArt);

        //Science(Maths) btn clicked
        offerbtnMaths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stream = "Science(Maths)";

                intent = new Intent(CourseOffersStudent.this, selectedOfferStudent.class);
                intent.putExtra("Stream", Stream );
                startActivity(intent);
            }
        });

        //Science(Bio) btn clicked
        offerbtnBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stream = "Science(Bio)";

                intent = new Intent(CourseOffersStudent.this, selectedOfferStudent.class);
                intent.putExtra("Stream", Stream );
                startActivity(intent);
            }
        });

        //Commerce btn clicked
        offerbtnCommerce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stream = "Commerce";

                intent = new Intent(CourseOffersStudent.this, selectedOfferStudent.class);
                intent.putExtra("Stream", Stream );
                startActivity(intent);
            }
        });

        //Commerce btn clicked
        offerbtnArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stream = "Art";

                intent = new Intent(CourseOffersStudent.this, selectedOfferStudent.class);
                intent.putExtra("Stream", Stream );
                startActivity(intent);
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