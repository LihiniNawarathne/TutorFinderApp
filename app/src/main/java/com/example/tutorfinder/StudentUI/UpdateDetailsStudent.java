package com.example.tutorfinder.StudentUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorfinder.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateDetailsStudent extends AppCompatActivity {

    //firebase
    FirebaseDatabase rootNode;
    FirebaseUser user;
    FirebaseAuth mAuth;
    DatabaseReference reference;

    //views
    CircleImageView propic,epropic;
    TextView StudentName,SBirthDay,SPhonepro,Streampro,Semail,eStudentName,eSPhonepro,eStreampro,epass,erepass;
    Button editProfile;

    String name,dob,phone,alstream,email,nic,sname,sdob,sphone,sstream,sepass,serepass,proimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details_student);

        //set action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Update Profile");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);

        //init firebase
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        rootNode = FirebaseDatabase.getInstance();
        //pointing to the table/reference
        reference = rootNode.getReference("Student");

        //init
        propic = findViewById(R.id.imgPropic);
        StudentName = findViewById(R.id.tvStudentName);
        SBirthDay= findViewById(R.id.tvSBirthDay);
        SPhonepro = findViewById(R.id.tvSPhonepr);
        Streampro=findViewById(R.id.tvStreampro);
        Semail = findViewById(R.id.tvSemail);
        editProfile =findViewById(R.id.buttoneditPro);

        Query checkUser = reference.orderByChild("email").equalTo(user.getEmail());
        checkUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds:snapshot.getChildren()){

                    //get data
                    name =""+ds.child("name").getValue();
                    dob =""+ds.child("dob").getValue();
                    phone =""+ds.child("phone").getValue();
                    alstream =""+ds.child("alstream").getValue();
                    email=""+ds.child("email").getValue();
                    proimg=""+ds.child("proimg").getValue();
                    nic=""+ds.child("nic").getValue();

                    //set data
                    StudentName.setText(name);
                    SBirthDay.setText(dob);
                    SPhonepro.setText(phone);
                    Streampro.setText(alstream );
                    Semail.setText(email);

                    try {
                        //if image received set it to the image view
                        Picasso.get().load(proimg).into(propic);
                    }
                    catch (Exception e){
                        //if there is an exception load default image
                        Picasso.get().load(R.drawable.studentpro).into(propic);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //init
                epropic = findViewById(R.id.imgPropic);

                eStudentName = findViewById(R.id.tvStudentName);
                eSPhonepro = findViewById(R.id.tvSPhonepr);
                eStreampro=findViewById(R.id.tvStreampro);
                epass = findViewById(R.id.tvpasswordedit);
                sepass=epass.getText().toString().trim();
                erepass = findViewById(R.id.tvpasswordeditRe);
                serepass=erepass.getText().toString().trim();

                //convert to string
                sname=eStudentName.getText().toString().trim();
                sphone=eSPhonepro.getText().toString().trim();
                sstream=eStreampro.getText().toString().trim();
                sepass=epass.getText().toString().trim();
                serepass=erepass.getText().toString().trim();

                if(!sname.isEmpty() && !sphone.isEmpty() && !sstream.isEmpty()){
               //if(nameChanged()||phoneChanged()||streamChanged()||passwordChanged()){
                   nameChanged();
                   phoneChanged();
                   streamChanged();

                    if(!sepass.isEmpty()  && !serepass.isEmpty()) {
                        passwordChanged();

                    }
                    else{
                        Toast.makeText(UpdateDetailsStudent.this, "Details are updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UpdateDetailsStudent.this, DashboardActivity.class));
                    }
               }
                else {
                    Toast.makeText(UpdateDetailsStudent.this, "Please fill the required places", Toast.LENGTH_SHORT).show();

                }

            }


        //update data
            private void nameChanged()  {
                if(!name.equals(sname)){
                    reference.child(nic).child("name").setValue(sname);
                    name = sname;
                }
            }

            private void phoneChanged() {
                if(!phone.equals(sphone)){
                    reference.child(nic).child("phone").setValue(sphone);
                    phone = sphone;
                }
            }

            private void streamChanged() {
                if(!alstream.equals(sstream)){
                    reference.child(nic).child("alstream").setValue(sstream);
                    alstream = sstream;
                }
            }

            private void passwordChanged() {

                    if(!sepass.isEmpty()  && !serepass.isEmpty()) {//check whether the provided passwords fields are empty or not
                            if (sepass.equals(serepass)) {

                                        user.updatePassword(sepass).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //password updated
                                                Toast.makeText(UpdateDetailsStudent.this, "Passwords successfully changed", Toast.LENGTH_SHORT).show();
                                                Toast.makeText(UpdateDetailsStudent.this, "Details are updated", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(UpdateDetailsStudent.this, DashboardActivity.class));
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(UpdateDetailsStudent.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                                            }
                                        });

                            } else {
                                Toast.makeText(UpdateDetailsStudent.this, "Passwords should be same", Toast.LENGTH_SHORT).show();
                            }
                    }
                    else {
                        Toast.makeText(UpdateDetailsStudent.this, "Passwords should be same", Toast.LENGTH_SHORT).show();
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