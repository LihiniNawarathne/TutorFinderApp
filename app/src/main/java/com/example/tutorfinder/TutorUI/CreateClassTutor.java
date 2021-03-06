package com.example.tutorfinder.TutorUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tutorfinder.Database.ChatGroupsModel;
import com.example.tutorfinder.Database.ChatParticipent;
import com.example.tutorfinder.Database.CreateClass;
import com.example.tutorfinder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

public class CreateClassTutor extends AppCompatActivity {

    EditText tutor,className,subject,grade,time,amount;
    Button btn2;
    DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);

        tutor=findViewById(R.id.tname);
        className=findViewById(R.id.className);
        subject=findViewById(R.id.sub);
        grade=findViewById(R.id.grade);
        time=findViewById(R.id.time);
        amount=findViewById(R.id.amount);
        btn2=findViewById(R.id.button4);
        //Cc = new CreateClass();
        reff= FirebaseDatabase.getInstance().getReference("ClassGroups");


        btn2.setOnClickListener(new View.OnClickListener() {
            String name=tutor.getText().toString();
            @Override
            public void onClick(View v) {
                CreateClass Cc=new CreateClass(tutor.getText().toString(),className.getText().toString(),subject.getText().toString(),grade.getText().toString(),time.getText().toString(),amount.getText().toString());
                reff.child(className.getText().toString()).setValue(Cc).addOnCompleteListener(CreateClassTutor.this, new OnCompleteListener<Void>()
                {
                    @Override
                public void onComplete(@NotNull Task<Void> task){


                    if(task.isSuccessful())
                {
                    addChatGroup();
                    Toast.makeText(CreateClassTutor.this, "successful", Toast.LENGTH_SHORT).show();
                    //addChatGroup();
                    Intent intent = new Intent(CreateClassTutor.this, Selections.class);
                    startActivity(intent);
                }


                else { Toast.makeText(CreateClassTutor.this, "Unsuccessful ", Toast.LENGTH_SHORT).show();
                }

                } });
            }
        });

    }

    public void addChatGroup(){


        //connecting to firebase database
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        //pointing to the table/reference
        DatabaseReference reference = rootNode.getReference("ChatGroups");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();


        ChatGroupsModel addNewgroup = new ChatGroupsModel(className.getText().toString());
        reference.child(className.getText().toString()).setValue(addNewgroup).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {

                    String role ="Tutor";

                    ChatParticipent participent= new ChatParticipent(role,mAuth.getUid());

                    reference.child(className.getText().toString()).child("participents").child(mAuth.getUid()).setValue(participent);

                    Toast.makeText(CreateClassTutor.this, "Creating chat group is successful", Toast.LENGTH_SHORT).show();
                        System.out.println("XXXXX");

                }
                else {
                    //if an unsuccessful registration direct back to registration form with a toast
                    Toast.makeText(CreateClassTutor.this, "Creating chat group is Unsuccessful ", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
