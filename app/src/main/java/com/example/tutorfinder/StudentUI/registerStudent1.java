package com.example.tutorfinder.StudentUI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tutorfinder.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class registerStudent1 extends AppCompatActivity {


     EditText Fname,phone,remail,schl,NIC,Alstrm;
     Button btn;
     Spinner Alspinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student1);

        //set action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Sign Up");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //init view
        Alspinner =findViewById(R.id.AlstrmSpinner);
        Fname = findViewById(R.id.etv_Fname);
        phone = findViewById(R.id.etv_phone);
        remail = findViewById(R.id.etv_remail);
        schl = findViewById(R.id.etv_schl);
        NIC = findViewById(R.id.etv_sNIC);
        btn = findViewById(R.id.btnReg);

        //set spinner
        List<String> list = new ArrayList<>();

        list.add("Science(Maths)");
        list.add("Science(Bio)");
        list.add("Commerce");
        list.add("Art");

        ArrayAdapter<String> dataAdapter =new ArrayAdapter<String>(registerStudent1.this, android.R.layout.simple_spinner_dropdown_item,list);
        Alspinner.setAdapter(dataAdapter);


        //navigate to Student SIgnUp2 page
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get spinner value
                String sAlstrm  = Alspinner.getSelectedItem().toString();

                String sFname= Fname.getText().toString().trim();
                String sphone= phone.getText().toString().trim();
                String sremail= remail.getText().toString().trim();
                String sschl= schl.getText().toString().trim();
                String sNIC= NIC.getText().toString().trim();


                //validate
                if(!Patterns.EMAIL_ADDRESS.matcher(sremail).matches()){
                    remail.setError("Invalid Email");
                    remail.setFocusable(true);
                }
                else if(!Patterns.PHONE.matcher(sphone).matches()){
                    phone.setError("Invalid Phone");
                    phone.setFocusable(true);
                }
                else {
                    Intent intent = new Intent(registerStudent1.this, registerStudent2.class);
                    intent.putExtra("Fname", sFname);
                    intent.putExtra("phone", sphone);
                    intent.putExtra("remail", sremail);
                    intent.putExtra("schl", sschl);
                    intent.putExtra("NIC", sNIC);
                    intent.putExtra("Alstrm", sAlstrm);

                    startActivity(intent);
                }
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