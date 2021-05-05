package com.example.tutorfinder.AdminUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tutorfinder.R;

public class Admin_MainActivity extends AppCompatActivity {
    Button BtnPaymetDetails,BtnViewTRequest,BtnUserDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        BtnPaymetDetails =  findViewById(R.id.btnPaymentMain);
        BtnViewTRequest = findViewById(R.id.btnViewrequest);
        BtnUserDetails =  findViewById(R.id.BtnStuTutors);

        BtnPaymetDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_MainActivity.this,PaymentDetails.class);
                startActivity(intent);

                Toast.makeText(Admin_MainActivity.this, "Welcome to paymentdetails page", Toast.LENGTH_SHORT).show();
            }
        });
        BtnViewTRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_MainActivity.this,admin_requestfromtutors.class);
                startActivity(intent);

                Toast.makeText(Admin_MainActivity.this, "Tutor's request", Toast.LENGTH_SHORT).show();
            }
        });
        BtnUserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_MainActivity.this,UserDetail.class);
                startActivity(intent);

                Toast.makeText(Admin_MainActivity.this, "User details page", Toast.LENGTH_SHORT).show();
            }
        });
    }
}