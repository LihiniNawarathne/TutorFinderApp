package com.example.tutorfinder.StudentUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorfinder.StudentModels.NotificationModel;
import com.example.tutorfinder.StudentModels.StudentHelperClass;
import com.example.tutorfinder.MainUI.LoginActivity;
import com.example.tutorfinder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class registerStudent2 extends AppCompatActivity {

    FirebaseDatabase rootNode;
    FirebaseAuth mAuth;
    DatabaseReference reference;

    DatePickerDialog datePickerDialog;

    TextView Fname,phone,remail,schl,sgrade,Alstrm,dateB;
    EditText etvPasswordRegister1,etvPasswordRegisterCon1,EmailS;
    Button btnSignUp;



    String Name,Phone,Email,School,NIC,ALStream,Password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_resgister2);

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

        //navigate to Student SignUp page
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String passwordCon = etvPasswordRegisterCon1.getText().toString().trim();
                String password = etvPasswordRegister1.getText().toString().trim();

                //validation
                if(!TextUtils.isEmpty(passwordCon) && !TextUtils.isEmpty(password)){


                    if(passwordCon.length() < 6 || password.length() < 6){
                        //set error
                        etvPasswordRegister1.setError("Password length should be at least 6 characters");
                        etvPasswordRegister1.setFocusable(true);
                        //Toast.makeText(registerStudent2.this, "Password too short", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        registerUser(Email,password);
                    }
                }
                else {
                    Toast.makeText(registerStudent2.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
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

                    //store student deatils in student table
                    addUser();
                    setnotifications();

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

        StudentHelperClass addNewStudent = new StudentHelperClass(Name,Phone,Email,School,NIC,ALStream,dateB.getText().toString(),proimg,mAuth.getUid());
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

    //add notification
    private void setnotifications(){

        //store data in firebase
        String time = ""+System.currentTimeMillis();
        String message = "Welcome to TutorFinder \n"+Name+"!";

        NotificationModel mm= new NotificationModel(message,time);

        reference.child(mAuth.getUid()).child("notifications").child(time).setValue(mm).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Added notification

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //sending message failed
                Toast.makeText(registerStudent2.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                dateB.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        //return month + "-" + day + "-" + year;
        return year + "-" + month + "-" + day;
    }


    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
}