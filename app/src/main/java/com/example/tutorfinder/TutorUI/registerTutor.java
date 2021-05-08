package com.example.tutorfinder.TutorUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        tr = new Tutor_register();
        reff= FirebaseDatabase.getInstance().getReference("TutorRequest");
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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