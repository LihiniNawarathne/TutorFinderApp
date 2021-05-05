package com.example.tutorfinder.AdminUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tutorfinder.R;

public class PaymentDetails extends AppCompatActivity {

    Button science,arts,commerce,tec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_payment_details);

        science = findViewById(R.id.AdscBtn);
        arts = findViewById(R.id.AdartBtn);
        commerce = findViewById(R.id.AdcomBtn);
        tec = findViewById(R.id.AdtecBtn);

        science.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PaymentDetails.this,admin_add_students_to_chat_groups.class);
                startActivity(i);

                Toast.makeText(PaymentDetails.this, "welcome", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
