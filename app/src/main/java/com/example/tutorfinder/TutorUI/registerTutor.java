package com.example.tutorfinder.TutorUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.tutorfinder.Database.Tutor_register;
import com.example.tutorfinder.MainUI.LoginActivity;
import com.example.tutorfinder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registerTutor extends AppCompatActivity {
    EditText name,email,phoneno,password,subject,qualifications,address,nic;
    Button btn2;
    Tutor_register tr;
    DatabaseReference reff;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_tutor);

        name=findViewById(R.id.Tname);
        email=findViewById(R.id.Temail);
        phoneno=findViewById(R.id.phone);
        password=findViewById(R.id.Tpass);
        nic=findViewById(R.id.nic);
        subject=findViewById(R.id.subject);
        qualifications=findViewById(R.id.qualification);
        address=findViewById(R.id.address);
        btn2=findViewById(R.id.button);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

            awesomeValidation.addValidation(this,R.id.Tname,
        RegexTemplate.NOT_EMPTY,R.string.Invalid_Name);
            awesomeValidation.addValidation(this,R.id.Temail,
         Patterns.EMAIL_ADDRESS,R.string.Invalid_Email);
           awesomeValidation.addValidation(this,R.id.phone,
       "[5-9]{1}[0-9]{9}$",R.string.Invalid_Mobile);
            awesomeValidation.addValidation(this,R.id.nic,
        RegexTemplate.NOT_EMPTY,R.string.Invalid_NIC);
            awesomeValidation.addValidation(this,R.id.subject,
        RegexTemplate.NOT_EMPTY,R.string.Invalid_Subject);
        awesomeValidation.addValidation(this,R.id.Tpass,
                ".{6,}",R.string.Invalid_Password);
        awesomeValidation.addValidation(this,R.id.qualification,
                RegexTemplate.NOT_EMPTY,R.string.Invalid_Q);
        awesomeValidation.addValidation(this,R.id.address,
                RegexTemplate.NOT_EMPTY,R.string.Invalid_Addrees);


        tr = new Tutor_register();
        reff= FirebaseDatabase.getInstance().getReference("TutorRequest");
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // validation
                 if(awesomeValidation.validate() ){
                Toast.makeText(getApplicationContext(), "Form validation success", Toast.LENGTH_LONG).show();



            }else{
                Toast.makeText(getApplicationContext(), "Form validation Unsuccessful", Toast.LENGTH_LONG).show();
            }

                tr=new Tutor_register(name.getText().toString(),email.getText().toString(),phoneno.getText().toString(),password.getText().toString(),nic.getText().toString(),subject.getText().toString(),qualifications.getText().toString(),address.getText().toString());
                reff.child(nic.getText().toString()).setValue(tr).addOnCompleteListener(registerTutor.this, new OnCompleteListener<Void>()
                { @Override public void onComplete(@NonNull Task<Void> task)
                { if(task.isSuccessful())
                { Toast.makeText(registerTutor.this, "Joining Request has send", Toast.LENGTH_SHORT).show();
                    Toast.makeText(registerTutor.this, "Cofirmation Email will Recieve", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(registerTutor.this, LoginActivity.class);
                    startActivity(intent);
                }
                else { Toast.makeText(registerTutor.this, "Unsuccessful ", Toast.LENGTH_SHORT).show();
                } } });
            }
        });
    }
}