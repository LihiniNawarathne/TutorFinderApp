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

                Intent i = new Intent(PaymentDetails.this, admin_add_chatgroup_science.class);
                startActivity(i);

                Toast.makeText(PaymentDetails.this, "science stream payments", Toast.LENGTH_SHORT).show();
            }
        });
        arts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PaymentDetails.this, admin_add_chatgroup_arts.class);
                startActivity(i);

                Toast.makeText(PaymentDetails.this, "Art stream payments", Toast.LENGTH_SHORT).show();

            }
        });
        commerce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PaymentDetails.this, admin_add_chatgroup_commerce.class);
                startActivity(i);

                Toast.makeText(PaymentDetails.this, "Commerce stream payments", Toast.LENGTH_SHORT).show();

            }
        });
        tec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PaymentDetails.this, admin_add_chatgroup_tec.class);
                startActivity(i);

                Toast.makeText(PaymentDetails.this, "Technology stream payments", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
