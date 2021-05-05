package com.example.tutorfinder.TutorUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tutorfinder.R;

public class Selections extends AppCompatActivity {
    Button button2,button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selections);

        button2 =  findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Selections.this, CreateClassTutor.class);
                startActivity(intent);

                Toast.makeText(Selections.this, "Now Create a Class", Toast.LENGTH_SHORT).show();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Selections.this, StudentDetails.class);
                startActivity(intent);

                Toast.makeText(Selections.this, "My Student details ", Toast.LENGTH_SHORT).show();
            }
        });

    }
}