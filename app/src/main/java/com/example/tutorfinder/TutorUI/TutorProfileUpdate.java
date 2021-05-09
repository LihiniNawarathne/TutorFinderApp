package com.example.tutorfinder.TutorUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorfinder.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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


public class TutorProfileUpdate extends AppCompatActivity {

        FirebaseDatabase rootNode;
        FirebaseUser user;
        FirebaseAuth mAuth;
        DatabaseReference reference;

        //views
        CircleImageView propic,epropic;
        TextView TutorName,Address,email,phone,nic,subject,qualifications,sepass,serepass;
        Button editProfile;

        String name,Tphone,address,Temail,Nic,Subject,Tqualifications,Tpassword,proimg,Tepass,Terepass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_profile_update);

        //set action bar
        // ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle("Update Profile");

        //enable back button
        //actionBar.setDisplayHomeAsUpEnabled(true);

        //init firebase
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        rootNode = FirebaseDatabase.getInstance();
        //pointing to the table/reference
        reference = rootNode.getReference("Tutors");

        //init
        propic = findViewById(R.id.imgPropic);
        TutorName = findViewById(R.id.tvTutorName);
        Address= findViewById(R.id.tvTAddress);
        phone = findViewById(R.id.tvPhone);
        qualifications=findViewById(R.id.tvQualifications);
        email = findViewById(R.id.tvTemail);
        editProfile =findViewById(R.id.buttoncreateclass);

        Query checkUser = reference.orderByChild("email").equalTo(user.getEmail());
        checkUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds:snapshot.getChildren()){

                    //get data
                    name =""+ds.child("name").getValue();
                    address =""+ds.child("address").getValue();
                    Tphone =""+ds.child("phone").getValue();
                    Tqualifications =""+ds.child("qualifications").getValue();
                    Temail=""+ds.child("email").getValue();
                    proimg=""+ds.child("proimg").getValue();
                    Nic=""+ds.child("nic").getValue();

                    //set data
                    TutorName.setText(name);
                    Address.setText(address);
                    phone.setText(Tphone);
                    qualifications.setText(Tqualifications );
                    email.setText(Temail);

                    try {
                        //if image received set it to the image view
                        Picasso.get().load(proimg).into(propic);
                    }
                    catch (Exception e){
                        //if there is an exception load default image
                        Picasso.get().load(R.drawable.tutors).into(propic);
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

                TutorName = findViewById(R.id.tvTutorName);
                phone = findViewById(R.id.tvPhone);
                qualifications=findViewById(R.id.tvQualifications);
                sepass = findViewById(R.id.tvpasswordedit);
                //sepass=Tepass.getText().toString().trim();
                serepass = findViewById(R.id.tvpasswordeditRe);
                //serepass=Terepass.getText().toString().trim();

                //convert to string
                name=TutorName.getText().toString().trim();
                Tphone=phone.getText().toString().trim();
                Tqualifications=qualifications.getText().toString().trim();
               // sepass=Tepass.getText().toString().trim();
                //serepass=Terepass.getText().toString().trim();

                if(!name.isEmpty() && !Tphone.isEmpty() && !Tqualifications.isEmpty()){
                    //if(nameChanged()||phoneChanged()||streamChanged()||passwordChanged()){
                    nameChanged();
                    phoneChanged();
                    qualificationsChanged();

                    if(!Tepass.isEmpty()  && !Terepass.isEmpty()) {
                        passwordChanged();

                    }
                    else{
                        Toast.makeText(TutorProfileUpdate.this, "Details are updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(TutorProfileUpdate.this, TutorViewDashboard.class));
                    }
                }
                else {
                    Toast.makeText(TutorProfileUpdate.this, "Please fill the required places", Toast.LENGTH_SHORT).show();

                }

            }


            //update data
            private void nameChanged()  {
                if(!name.equals(name)){
                    reference.child(Nic).child("name").setValue(name);
                    name = name;
                }
            }

            private void phoneChanged() {
                if(!phone.equals(Tphone)){
                    reference.child(Nic).child("phone").setValue(Tphone);
                    phone = phone;
                }
            }

            private void qualificationsChanged() {
                if(!qualifications.equals(Tqualifications)){
                    reference.child(Nic).child("qualifications").setValue(Tqualifications);
                    qualifications = qualifications;
                }
            }

            private void passwordChanged() {

                if(!Tepass.isEmpty()  && !Terepass.isEmpty()) {//check whether the provided passwords fields are empty or not
                    if (sepass.equals(serepass)) {

                        user.updatePassword(Tepass).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //password updated
                                Toast.makeText(TutorProfileUpdate.this, "Passwords successfully changed", Toast.LENGTH_SHORT).show();
                                Toast.makeText(TutorProfileUpdate.this, "Details are updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(TutorProfileUpdate.this, TutorViewDashboard.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(TutorProfileUpdate.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

                    } else {
                        Toast.makeText(TutorProfileUpdate.this, "Passwords should be same", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(TutorProfileUpdate.this, "Passwords should be same", Toast.LENGTH_SHORT).show();
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
