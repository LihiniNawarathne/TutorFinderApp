package com.example.tutorfinder.TutorUI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tutorfinder.Database.CreateClass;
import com.example.tutorfinder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

public class CreateClassTutor extends AppCompatActivity {

    EditText tutorname,classname,subname,grade,time,amount;
    Button btn2;
    DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);

        tutorname=findViewById(R.id.tname);
        classname=findViewById(R.id.className);
        subname=findViewById(R.id.sub);
        grade=findViewById(R.id.grade);
        time=findViewById(R.id.time);
        amount=findViewById(R.id.amount);
        btn2=findViewById(R.id.button4);
        //Cc = new CreateClass();
        reff= FirebaseDatabase.getInstance().getReference("ClassGroups");


        btn2.setOnClickListener(new View.OnClickListener() {
            String name=tutorname.getText().toString();
            @Override
            public void onClick(View v) {
                CreateClass Cc=new CreateClass(tutorname.getText().toString(),classname.getText().toString(),subname.getText().toString(),grade.getText().toString(),time.getText().toString(),amount.getText().toString());
                reff.child(classname.getText().toString()).setValue(Cc).addOnCompleteListener(CreateClassTutor.this, new OnCompleteListener<Void>()
                {
                    @Override
                public void onComplete(@NotNull Task<Void> task){


                    if(task.isSuccessful())
                {
                    Toast.makeText(CreateClassTutor.this, "successful", Toast.LENGTH_SHORT).show();
                }


                else { Toast.makeText(CreateClassTutor.this, "Unsuccessful ", Toast.LENGTH_SHORT).show();
                }

                } });
            }
        });

    }
}
