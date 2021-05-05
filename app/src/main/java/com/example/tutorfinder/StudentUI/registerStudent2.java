package com.example.tutorfinder.StudentUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorfinder.Database.StudentHelperClass;
import com.example.tutorfinder.MainUI.LoginActivity;
import com.example.tutorfinder.MainUI.registerUserSelect;
import com.example.tutorfinder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Pattern;


public class registerStudent2 extends AppCompatActivity {

    FirebaseDatabase rootNode;
    FirebaseAuth mAuth;
    DatabaseReference reference;

    DatePickerDialog datePickerDialog;
    Button dateButton;

    TextView Fname,phone,remail,schl,sgrade,Alstrm,dateB;
    EditText etvPasswordRegister1,etvPasswordRegisterCon1,EmailS;
    Button btnSignUp;



    String Name,Phone,Email,School,NIC,ALStream,DOB,Password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgister_student2);

        //set action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Sign Up");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);

        //getDateOfBirthFromADatePicker
        initDatePicker();
        dateB = findViewById(R.id.dateB);
        dateB.setText(getTodaysDate());

        etvPasswordRegister1 = findViewById(R.id.etvPasswordRegister);
        etvPasswordRegisterCon1 = findViewById(R.id.etvPasswordRegisterCon);
        btnSignUp = findViewById(R.id.btnSignUp);

        //get data from registerStudent1
        Name = getIntent().getStringExtra("Fname") ;
        Phone= getIntent().getStringExtra("phone") ;
        Email= getIntent().getStringExtra("remail") ;
        School= getIntent().getStringExtra("schl") ;
        NIC= getIntent().getStringExtra("NIC") ;
        ALStream= getIntent().getStringExtra("Alstrm") ;
       // DOB= getIntent().getStringExtra("dateB") ;
        //DOB= dateB.getText().toString().trim();
        DOB="2000-02-01";
        //Password= etvPasswordRegister1.getText().toString().trim();

        //navigate to Student SIgnUp page
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txt_email = Email;
                String txt_password = etvPasswordRegister1.getText().toString().trim();

                if(TextUtils.isEmpty(txt_email)||TextUtils.isEmpty(txt_password)){
                    Toast.makeText(registerStudent2.this, "Empty Credential", Toast.LENGTH_SHORT).show();

                }

                else if(txt_password.length() < 6){
                    //set error
                    etvPasswordRegister1.setError("Password length should be at least 6 characters");
                    etvPasswordRegister1.setFocusable(true);
                    //Toast.makeText(registerStudent2.this, "Password too short", Toast.LENGTH_SHORT).show();
                }
                else{
                    registerUser(txt_email,txt_password);
                }

            }
        });
    }

    private void registerUser(String email, String password) {

        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(registerStudent2.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(registerStudent2.this, "Registration is successful", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(registerStudent2.this, "Please Login with your credentials", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(registerStudent2.this,LoginActivity.class));

                    //store student deatils in student table
                    addUser();

                }
                else{

                    //if an unsuccessful registration direct back to registration form with a toast
                    Toast.makeText(registerStudent2.this, "Registration is Unsuccessful ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(registerStudent2.this, registerStudent1.class ));
                    //Toast.makeText(registerStudent2.this, "Registration is Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void addUser(){


        //connecting to firebase database
        rootNode = FirebaseDatabase.getInstance();
        //pointing to the table/reference
        reference = rootNode.getReference("Student");

        String proimg ="";

        StudentHelperClass addNewStudent = new StudentHelperClass(Name,Phone,Email,School,NIC,ALStream,DOB,proimg,mAuth.getUid());
        reference.child(mAuth.getUid()).setValue(addNewStudent).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {

                    Toast.makeText(registerStudent2.this, "Registration is successful", Toast.LENGTH_SHORT).show();
                    Toast.makeText(registerStudent2.this, "Please Login with your credentials", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(registerStudent2.this,LoginActivity.class));

                }
                else {
                    //if an unsuccessful registration direct back to registration form with a toast
                    Toast.makeText(registerStudent2.this, "Registration is Unsuccessful ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(registerStudent2.this, registerStudent1.class ));
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

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
}