package com.example.tutorfinder.StudentUI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tutorfinder.MainUI.LoginActivity;
import com.example.tutorfinder.R;

import java.util.ArrayList;
import java.util.List;

public class registerStudent1 extends AppCompatActivity {


     EditText Fname,phone,remail,schl,NIC,Alstrm;
     Button btn;
     Spinner Alspinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register1);

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
        list.add("Other");

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

                if(!sFname.isEmpty() && !sphone.isEmpty() && !sremail.isEmpty() && !sschl.isEmpty() && !sNIC.isEmpty()) {
                    if (!sFname.matches("^[a-zA-Z]+(([,. ][a-zA-Z ])?[a-zA-Z]*)*$") || sFname.length()<4) {//^[A-Za-z]+$
                        Fname.setError("Invalid Name");
                        Fname.setFocusable(true);
                    }else if (sphone.length() < 10) {
                        phone.setError("Phone number should be 10 digits");
                        phone.setFocusable(true);
                    }else if (!sphone.matches("^0[7-9][0-9]{8}$")) {//!Patterns.PHONE.matcher(sphone).matches()
                        phone.setError("Invalid Phone");
                        phone.setFocusable(true);
                    }
                    else if (!Patterns.EMAIL_ADDRESS.matcher(sremail).matches()) {
                        remail.setError("Invalid Email");
                        remail.setFocusable(true);
                    }   else if (!sschl.matches("^[a-zA-Z,'.\\s]+$")) {//^[A-Za-z]+$
                        schl.setError("Invalid School Name");
                        schl.setFocusable(true);
                    } else if (!sNIC.matches("^(?:19|20)?\\d{2}[0-9]{10}|[0-9][9|11][X|V]$")) {//^[A-Za-z]+$
                        NIC.setError("Invalid NIC");
                        NIC.setFocusable(true);
                    } else {
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
                else{
                    Toast.makeText(registerStudent1.this, "Fields cannot be empty..", Toast.LENGTH_SHORT).show();
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